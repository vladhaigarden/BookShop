package com.epam.bookshop.specification;

import com.epam.bookshop.model.Book;
import com.epam.bookshop.model.Genre;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

@AllArgsConstructor
public class BookSpecification implements Specification<Book> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        String key = criteria.getKey();
        Object value = criteria.getValue();
        switch (criteria.getOperation()) {
            case ">":
                return builder.greaterThanOrEqualTo(root.get(key), value.toString());
            case "<":
                return builder.lessThanOrEqualTo(root.get(key), value.toString());
            case ":":
                if (root.get(key).getJavaType() == String.class) {
                    return builder.like(root.get(key), "%" + value + "%");
                } else if (root.get(key).getJavaType() == Genre.class) {
                    return builder.equal(root.get(key), Genre.valueOf(value.toString()));
                } else if (root.get(key).getJavaType() == LocalDateTime.class) {
                    return builder.equal(root.get(key), LocalDateTime.parse(value.toString()));
                }
            default:
                return null;
        }
    }
}