package com.fada21.android.samplereaderlibraryscreen.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fada21.android.samplereaderlibraryscreen.R;
import com.fada21.android.samplereaderlibraryscreen.adapter.CategoriesAdapter;
import com.fada21.android.samplereaderlibraryscreen.model.Book;
import com.fada21.android.samplereaderlibraryscreen.model.Category;
import com.fada21.android.samplereaderlibraryscreen.model.CategoryEnum;
import com.fada21.android.samplereaderlibraryscreen.rest.BooksInCategory;
import com.fada21.android.samplereaderlibraryscreen.rest.BooksInCategoryService;
import com.fada21.android.samplereaderlibraryscreen.rest.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;


public class LibraryActivity extends Activity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_library);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setHasFixedSize(true); ?

        List<Category> categoryList = new ArrayList<Category>();
        categoryList.add(new Category("Crime", mockBookGenerator(3)));
        categoryList.add(new Category("Comedy", mockBookGenerator(10)));
        categoryList.add(new Category("History", mockBookGenerator(20)));
        categoryList.add(new Category("Thriller", mockBookGenerator(5)));
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(categoryList);

        recyclerView.setAdapter(categoriesAdapter);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://www.googleapis.com").setConverter(new JacksonConverter()).setLogLevel(RestAdapter.LogLevel.FULL).build();
        final BooksInCategoryService booksInCategoryService = restAdapter.create(BooksInCategoryService.class);

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... voids) {
                StringBuilder sb = new StringBuilder();
                BooksInCategory books = booksInCategoryService.getBooks(CategoryEnum.CRIME.getRoute(), 3);
                for (Item item : books.getItems()) {
                    String title = item.getVolumeInfo().getTitle();
                    sb.append(title).append(",");
                }
                return sb.toString();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(LibraryActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };
        asyncTask.execute("");

    }

    private List<Book> mockBookGenerator(int count) {
        List<Book> l = new ArrayList<Book>();
        for (int i = 0; i < count; i++) {
            l.add(new Book(null, null, null));
        }
        return l;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
