package com.codenation.utils;

import com.codenation.enums.SearchOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchCriteria {

    private String firstKey;
    private String secondKey;
    private Object value;
    private SearchOperation operation;

}
