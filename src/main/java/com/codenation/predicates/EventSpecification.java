package com.codenation.predicates;

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

@Data
@AllArgsConstructor
public class EventSpecification implements Specification<Event> {

    private String description;
    private String origin;
    private LocalDate date;
    private Integer quantity;
    private String email;
    private String levelDescription;

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicateList = new ArrayList<>();

        predicateList.add(builder.and(
                builder.like(root.get("description"), "%" + description + "%"),
                builder.like(root.get("origin"), "%" + origin + "%"),
                builder.like(root.get("user").get("email"), "%" + email + "%"),
                builder.like(root.get("level").get("description"), "%" + levelDescription + "%"))
        );
        if (date != null) {
            predicateList.add(builder.equal(root.get("date"), date));
        } else if (quantity != null) {
            predicateList.add(builder.equal(root.get("quantity"), quantity));
        }

        return builder.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }
}
