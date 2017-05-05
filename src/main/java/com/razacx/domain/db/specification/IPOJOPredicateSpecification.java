package com.razacx.domain.db.specification;

import java.util.function.Predicate;

public interface IPOJOPredicateSpecification extends IPOJOSpecification {
    
    Predicate toPredicate();
    
}
