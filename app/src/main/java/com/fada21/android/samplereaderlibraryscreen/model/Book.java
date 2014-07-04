package com.fada21.android.samplereaderlibraryscreen.model;

public class Book {

    private String title;
    private String author;
    private String thumbUrl;

    public Book(String title, String author, String thumbUrl) {
        this.title = title;
        this.author = author;
        this.thumbUrl = thumbUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (thumbUrl != null ? !thumbUrl.equals(book.thumbUrl) : book.thumbUrl != null)
            return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (thumbUrl != null ? thumbUrl.hashCode() : 0);
        return result;
    }
}
