package com.fada21.android.samplereaderlibraryscreen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.fada21.android.samplereaderlibraryscreen.R;
import com.fada21.android.samplereaderlibraryscreen.adapter.CategoriesAdapter;
import com.fada21.android.samplereaderlibraryscreen.model.Book;
import com.fada21.android.samplereaderlibraryscreen.model.Category;

import java.util.ArrayList;
import java.util.List;


public class LibraryActivity extends Activity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_library);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Category> categoryList = new ArrayList<Category>();
        categoryList.add(new Category("Crime", mockBookGenerator(3)));
        categoryList.add(new Category("Comedy", mockBookGenerator(10)));
        categoryList.add(new Category("History", mockBookGenerator(20)));
        categoryList.add(new Category("Thriller", mockBookGenerator(5)));
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(categoryList);

        recyclerView.setAdapter(categoriesAdapter);

    }

    private List<Book> mockBookGenerator(int count) {
        List<Book> l = new ArrayList<Book>();
        for (int i = 0; i<count; i++) {
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
