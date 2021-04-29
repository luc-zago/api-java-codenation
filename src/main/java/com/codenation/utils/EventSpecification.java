package com.codenation.utils;

import com.codenation.enums.SearchOperation;
import com.codenation.models.Event;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class EventSpecification implements Specification<Event> {

    private final List<SearchCriteria> list;

    public EventSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicateList = new ArrayList<>();

        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL) && criteria.getSecondKey() != null) {
                predicateList.add(builder.equal(root.get(criteria.getFirstKey()).get(criteria.getSecondKey()),
                        criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicateList.add(builder.equal(root.get(criteria.getFirstKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.LIKE)
                    && criteria.getSecondKey() != null) {
                predicateList.add(builder.like(root.get(criteria.getFirstKey()).get(criteria.getSecondKey()),
                        "%" + criteria.getValue().toString() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.LIKE)) {
                predicateList.add(builder.like(root.get(criteria.getFirstKey()),
                        "%" + criteria.getValue().toString() + "%"));
            }
        }

        return builder.and(predicateList.toArray(new Predicate[0]));
    }
}
