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
    private String order;
    private String sort;

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicateList = new ArrayList<>();

        Fetch<Event, User> eventUserFetch = root.fetch("email");
        Fetch<Event, Level> eventLevelFetch = eventUserFetch.fetch("description");

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

        if (order.equals("user")) {
            query.orderBy(builder.asc(root.get(order).get("email")));
            if (sort.equals("desc")) {
                query.orderBy(builder.desc(root.get(order).get("email")));
            }
        } else if (order.equals("level")) {
            System.out.println("Entrei aqui");
            query.orderBy(builder.asc(root.get(order).get("description")));
            if (sort.equals("desc")) {
                query.orderBy(builder.desc(root.get(order).get("description")));
            }
        }

        query.orderBy(builder.asc(root.get(order)));

        if (sort.equals("desc")) {
            query.orderBy(builder.desc(root.get(order)));
        } */

        return builder.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }
}
