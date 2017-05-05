package com.razacx.domain.db.factory;

import com.razacx.domain.db.IGenericRepository;
import com.razacx.domain.db.concrete.GenericRepositoryJPA;
import com.razacx.domain.db.concrete.GenericRepositoryPOJO;

public class GenericRepositoryFactory<T> {

    public IGenericRepository<T> getRepository(String dbType, Class<T> entityClass) {

        switch (dbType) {
            case "pojo":
                return new GenericRepositoryPOJO<>(entityClass);
            default:
                //Expecting dbType to be the name of a persistence unit
                return new GenericRepositoryJPA<>(entityClass, dbType);
        }

    }
    
}
