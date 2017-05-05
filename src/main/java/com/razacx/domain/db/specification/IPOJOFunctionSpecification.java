package com.razacx.domain.db.specification;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface IPOJOFunctionSpecification extends IPOJOSpecification {
    
    Function<Collection, List> toPOJOFunction();
    
}
