package com.razacx.domain.db;

import com.razacx.domain.db.action.IAction;
import com.razacx.domain.db.specification.ISpecification;

import java.util.List;

public interface IGenericRepository<T> {

    void add(T obj);
    void remove(T obj);
    void update(T obj);

    T query(ISpecification specification);
    List<T> queryList(ISpecification specification);

    void execute(IAction action);
    
    void close();
    
}
