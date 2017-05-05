package com.razacx.domain.db.action;

import javax.persistence.EntityManager;

public interface IJPAAction extends IAction<EntityManager> {

    @Override
    void run(EntityManager object);
    
}
