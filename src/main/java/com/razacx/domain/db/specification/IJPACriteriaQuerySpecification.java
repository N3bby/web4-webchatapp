package com.razacx.domain.db.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface IJPACriteriaQuerySpecification extends IJPASpecification {
    
    CriteriaQuery toCriteriaQuery(CriteriaBuilder cb);
    
}
