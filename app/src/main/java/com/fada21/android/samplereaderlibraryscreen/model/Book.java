package com.fada21.android.samplereaderlibraryscreen.model;

import java.util.List;

public class Book {

    private String title;
    private List<String> authors;
    private String thumbUrl;

    public Book(String title, List<String> authors, String thumbUrl) {
        this.title = title;
        this.authors = authors;
        this.thumbUrl = thumbUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthor() {
        return authors;
    }

    public void setAuthor(List<String> author) {
        this.authors = authors;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (title != null ? !title.equals(book.title) : book.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
