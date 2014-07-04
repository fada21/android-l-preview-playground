package com.fada21.android.samplereaderlibraryscreen.model;

import java.util.List;

public class Category {

    private String name;
    private List<Book> bookList;

    public Category(String name, List<Book> bookList) {
        if (name == null) throw new IllegalArgumentException("Category must hava a name!");
        this.name = name;
        this.bookList = bookList;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        this.name = mName;
    }

    public List<Book> getBooksList() {
        return bookList;
    }

    public void setBooksList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (!name.equals(category.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
