package com.fada21.android.samplereaderlibraryscreen.model;

import android.util.SparseArray;

import java.util.List;

public class Category {

    private int index;
    private List<Book> bookList;

    public Category(int index, List<Book> bookList) {
        this.index = index;
        this.bookList = bookList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

        if (index != category.index) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return index;
    }
}
