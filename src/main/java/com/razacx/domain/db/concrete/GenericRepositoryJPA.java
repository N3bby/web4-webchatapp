package com.razacx.domain.db.concrete;

import com.razacx.domain.db.action.IAction;
import com.razacx.domain.db.action.IJPAAction;
import com.razacx.domain.db.specification.*;
import com.razacx.domain.db.specification.IJPACriteriaQuerySpecification;
import com.razacx.domain.db.exception.DBException;
import com.razacx.domain.db.IGenericRepository;
import com.razacx.domain.db.exception.DBEntityAlreadyExistsException;
import com.razacx.domain.db.exception.DBEntityDoesNotExistException;
import com.razacx.domain.db.exception.DBNotImplementedSpecificationException;
import com.razacx.domain.db.specification.ISpecification;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class GenericRepositoryJPA<T> implements IGenericRepository<T> {

    private static EntityManagerFactory emf;
    private Class<T> entityClass;

    public GenericRepositoryJPA(Class<T> entityClass, String persistenceUnit) {

        this.entityClass = entityClass;
        if(emf == null || !emf.isOpen()) emf = Persistence.createEntityManagerFactory(persistenceUnit);

    }

    @Override
    public void add(T obj) {

        executeInTransaction(em -> {
            
            //Testing for existing object manually (usual stacktrace is too long otherwise)
            if(em.find(entityClass, emf.getPersistenceUnitUtil().getIdentifier(obj)) == null) {
                em.persist(obj);
            } else {
                throw new DBEntityAlreadyExistsException(obj.toString());
            }
            
        });
        
    }

    @Override
    public void remove(T obj) {

        executeInTransaction(em -> {

            //We need to make the object managed before we can remove it
            T merged = em.merge(obj);
            em.remove(merged);
            
        });
        
    }

    @Override
    public void update(T obj) {
        
        executeInTransaction(em -> {
            
            //Testing for non existing object manually (usual stacktrace is too long otherwise)
            if(em.find(entityClass, emf.getPersistenceUnitUtil().getIdentifier(obj)) != null) {
                em.merge(obj);
            } else {
                throw new DBEntityDoesNotExistException(obj.toString());
            }
            
        });
        
    }

    @Override
    public T query(ISpecification specification) {

        List<T> resultList = queryList(specification);
        return resultList.size() >= 1 ? resultList.get(0) : null;

    }

    @Override
    public List<T> queryList(ISpecification specification) {
        
        if (specification instanceof IJPACriteriaQuerySpecification) {
            return executeCriteriaQuerySpecification((IJPACriteriaQuerySpecification) specification);
        } else if(specification instanceof IJPAFunctionSpecification) {
            return executeFunctionSpecification((IJPAFunctionSpecification) specification);
        } else {
            throw new DBNotImplementedSpecificationException("No implementation for specification: " + specification.getClass().getSimpleName());
        }
        
    }

    @Override
    public void execute(IAction action) {
        if(!(action instanceof IJPAAction)) throw new IllegalArgumentException("IAction must be instance of IJPAAction, but is: " + action.getClass().getSimpleName());
        executeInTransaction(action::run);
    }

    @Override
    public void close() {
        emf.close();
    }

    private List<T> executeCriteriaQuerySpecification(IJPACriteriaQuerySpecification specification) {

        return executeInTransaction(em -> { 
            
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> query = specification.toCriteriaQuery(cb);            

            return em.createQuery(query).getResultList();

        });

    }

    private List<T> executeFunctionSpecification(IJPAFunctionSpecification specification) {
        
        return executeInTransaction(em -> {
            return specification.toJPAFunction().apply(em);
        });
        
    }
    
    private void executeInTransaction(Consumer<EntityManager> consumer) {

        executeInTransaction(em -> {
            consumer.accept(em);
            return null;
        });

    }

    private List<T> executeInTransaction(Function<EntityManager, List> function) {

        EntityManager em = null;
        EntityTransaction tx = null;

        try {

            em = emf.createEntityManager();
            tx = em.getTransaction();

            tx.begin();

            List<T> resultList = function.apply(em);

            tx.commit();

            return resultList;

        } catch (Exception e1) {
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception e2) {
                    throw new DBException(e2);
                }
            }
            throw new DBException(e1);
        } finally {
            if (em != null) em.close();
        }

    }

}
