package com.razacx.domain.db.concrete;

import com.razacx.domain.db.IGenericRepository;
import com.razacx.domain.db.action.IAction;
import com.razacx.domain.db.action.IPOJOAction;
import com.razacx.domain.db.exception.DBException;
import com.razacx.domain.db.specification.ISpecification;
import com.razacx.domain.db.specification.IPOJOFunctionSpecification;
import com.razacx.domain.db.specification.IPOJOPredicateSpecification;
import com.razacx.domain.db.exception.DBEntityAlreadyExistsException;
import com.razacx.domain.db.exception.DBEntityDoesNotExistException;
import com.razacx.domain.db.exception.DBNotImplementedSpecificationException;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class GenericRepositoryPOJO<T> implements IGenericRepository<T> {

    private Set<T> objects;
    private int idCounter = 0;
    
    private Class<T> entityClass;

    public GenericRepositoryPOJO(Class<T> entityClass) {
        this.objects = new HashSet<>();
        this.entityClass = entityClass;
    }

    @Override
    public void add(T obj) {

        //Auto generate id if needed
        generateObjIdIfRequired(obj);
        
        //Try to copy when adding so we can prevent unwanted changes
        obj = getCopyOfT(obj);
        
        if (objects.contains(obj)) throw new DBEntityAlreadyExistsException(obj.toString());

        objects.add(obj);
    }

    private void generateObjIdIfRequired(T obj) {
        
        //Find field that is marked as Id and GeneratedValue + is of type Long
        Field idField = Arrays.stream(entityClass.getDeclaredFields())
                            .filter(f -> f.isAnnotationPresent(Id.class) &&
                                         f.isAnnotationPresent(GeneratedValue.class) &&
                                         f.getType().equals(Long.TYPE)) //Refers to primitive type long
                            .findFirst().orElse(null);
        
        if(idField == null) return;

        try {
            idField.setAccessible(true);
            idField.set(obj, idCounter);
            idField.setAccessible(false);
            idCounter++;
        } catch (IllegalAccessException e) {
            throw new DBException("Problem generating and setting id of object: " + entityClass.getSimpleName() + " : " + obj.toString(), e);
        }

    }

    @Override
    public void remove(T obj) {
        if (!objects.contains(obj)) throw new DBEntityDoesNotExistException(obj.toString());
        objects.remove(obj);
    }

    @Override
    public void update(T obj) {
        if (!objects.contains(obj)) throw new DBEntityDoesNotExistException(obj.toString());
        objects.remove(obj);
        objects.add(obj);
    }

    @Override
    public T query(ISpecification specification) {

        List<T> resultList = queryList(specification);
        return resultList.size() >= 1 ? resultList.get(0) : null;

    }

    @Override
    public List<T> queryList(ISpecification specification) {

        List<T> objects;

        if (specification instanceof IPOJOPredicateSpecification) {
            objects = executePredicateSpecification((IPOJOPredicateSpecification) specification);
        } else if (specification instanceof IPOJOFunctionSpecification) {
            objects = executePOJOFunctionSpecification((IPOJOFunctionSpecification) specification);
        } else {
            throw new DBNotImplementedSpecificationException("No implementation for specification: " + specification.getClass().getSimpleName());
        }

        //Clone each object so we can prevent direct changes without calling update 
        List<T> clonedObjects = new ArrayList<T>();
        objects.forEach(o -> {
            try {
                clonedObjects.add(getCopyOfT(o));
            } catch (Exception ignored) {
            }
        });

        return clonedObjects;

    }

    @Override
    public void execute(IAction action) {
        if (!(action instanceof IPOJOAction))
            throw new IllegalArgumentException("IAction must be instance of IPOJOAction, but is: " + action.getClass().getSimpleName());
        action.run(objects);
    }

    @Override
    public void close() {
        //Does nothing in this case
    }

    private List<T> executePredicateSpecification(IPOJOPredicateSpecification specification) {

        return (List<T>) objects.stream().filter(specification.toPredicate()).collect(Collectors.toList());

    }

    private List<T> executePOJOFunctionSpecification(IPOJOFunctionSpecification specification) {

        return (List<T>) specification.toPOJOFunction().apply(objects);

    }

    private T getCopyOfT(Object t) {

        try {
            return entityClass.getConstructor(entityClass).newInstance(t);
        } catch (Exception e) {
            throw new DBException("Unable to find copy constructor for class: " + entityClass.getSimpleName(), e);
        }

    }

}
