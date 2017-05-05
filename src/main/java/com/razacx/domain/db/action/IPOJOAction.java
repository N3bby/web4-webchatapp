package com.razacx.domain.db.action;

import java.util.Collection;

public interface IPOJOAction extends IAction<Collection> {

    @Override
    void run(Collection objects);
    
}
