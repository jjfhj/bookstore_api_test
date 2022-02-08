package com.github.jjfhj.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookListData {

    @JsonProperty("books")
    private BookList[] books;

    public BookList[] getBooks() {
        return books;
    }

    public void setBooks(BookList[] books) {
        this.books = books;
    }
}
