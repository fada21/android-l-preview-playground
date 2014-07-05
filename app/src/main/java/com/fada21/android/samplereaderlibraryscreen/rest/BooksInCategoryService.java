package com.fada21.android.samplereaderlibraryscreen.rest;

import retrofit.http.GET;
import retrofit.http.Query;

public interface BooksInCategoryService {

    @GET("/books/v1/volumes?fields=items(volumeInfo(title,authors,imageLinks(thumbnail)))")
    BooksInCategory getBooks(@Query("q") String q, @Query("maxResults") int maxResults);

}
