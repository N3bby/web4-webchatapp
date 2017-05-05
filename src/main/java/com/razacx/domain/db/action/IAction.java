package com.razacx.domain.db.action;

public interface IAction<T> {
    
    void run(T object);
    
}
