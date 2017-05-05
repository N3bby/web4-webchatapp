package com.razacx.domain.db.specification;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;

public interface IJPAFunctionSpecification extends IJPASpecification {

    Function<EntityManager, List> toJPAFunction();
    
}
