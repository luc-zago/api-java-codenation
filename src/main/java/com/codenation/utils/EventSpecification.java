package com.codenation.utils;

import com.codenation.enums.SearchOperation;
import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventSpecification implements Specification<Event> {

    private List<SearchCriteria> list;

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

        return builder.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }
}
