package com.github.jjfhj.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDataForAdding {

    private String userId;
    private CollectionOfIsbns[] collectionOfIsbns;
}
