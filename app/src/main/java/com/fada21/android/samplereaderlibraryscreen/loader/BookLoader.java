package com.fada21.android.samplereaderlibraryscreen.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.fada21.android.samplereaderlibraryscreen.model.Book;
import com.fada21.android.samplereaderlibraryscreen.model.Category;
import com.fada21.android.samplereaderlibraryscreen.model.CategoryEnum;
import com.fada21.android.samplereaderlibraryscreen.rest.json.BooksInCategory;
import com.fada21.android.samplereaderlibraryscreen.rest.BooksInCategoryService;
import com.fada21.android.samplereaderlibraryscreen.rest.json.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class BookLoader extends AsyncTaskLoader<List<Category>> {

    private List<Category> data;

    public BookLoader(Context context) {
        super(context);
    }

    @Override
    public List<Category> loadInBackground() {
        List<Category> result = new ArrayList<Category>();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://www.googleapis.com").setConverter(new JacksonConverter()).build();
        BooksInCategoryService booksInCategoryService = restAdapter.create(BooksInCategoryService.class);

        result.add(getBooksInCategories(booksInCategoryService, CategoryEnum.CRIME, 3));
        result.add(getBooksInCategories(booksInCategoryService, CategoryEnum.COMEDY, 10));
        result.add(getBooksInCategories(booksInCategoryService, CategoryEnum.HISTORY, 20));
        result.add(getBooksInCategories(booksInCategoryService, CategoryEnum.THRILLER, 5));
        return result;
    }

    private Category getBooksInCategories(BooksInCategoryService booksInCategoryService, CategoryEnum categoryEnum, int count) {
        BooksInCategory books = booksInCategoryService.getBooks(categoryEnum.getRoute(), count);
        List<Book> booksList = new ArrayList<Book>();
        for (Item item : books.getItems()) {
            String title = item.getVolumeInfo().getTitle();
            List<String> authors = item.getVolumeInfo().getAuthors();
            String thumb = item.getVolumeInfo().getImageLinks().getThumbnail();
            Book book = new Book(title, authors, thumb);
            booksList.add(book);
        }
        Category category = new Category(categoryEnum.getIndex(), booksList);
        return category;
    }

    @Override
    public void deliverResult(List<Category> data) {
        if (isReset()) {
            return;
        }

        List<Category> oldData = data;
        this.data = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (data != null) {
            deliverResult(data);
        }

        if (takeContentChanged() || data == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if (data != null) {
            data = null;
        }
    }

    @Override
    public void onCanceled(List<Category> data) {
        super.onCanceled(data);
    }
}
