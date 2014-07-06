package com.fada21.android.samplereaderlibraryscreen.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.fada21.android.samplereaderlibraryscreen.R;
import com.fada21.android.samplereaderlibraryscreen.adapter.CategoriesAdapter;
import com.fada21.android.samplereaderlibraryscreen.loader.BookLoader;
import com.fada21.android.samplereaderlibraryscreen.model.Category;

import java.util.ArrayList;
import java.util.List;

import static com.fada21.android.samplereaderlibraryscreen.loader.StaticLoaderIdConsts.BOOK_LOADER_ID;

public class LibraryActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<Category>> {

    private RecyclerView recyclerView;
    private CategoriesAdapter categoriesAdapter;
    private ProgressBar progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_library);


        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Category> categoryList = new ArrayList<Category>();

        categoriesAdapter = new CategoriesAdapter(categoryList);
        recyclerView.setAdapter(categoriesAdapter);

        progressView = (ProgressBar) findViewById(R.id.progress);
        progressView.setVisibility(View.VISIBLE);
        getSupportLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.library, menu);
        menu.findItem(R.id.menu_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            progressView.setVisibility(View.VISIBLE);
            getSupportLoaderManager().restartLoader(BOOK_LOADER_ID, null, this);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<List<Category>> onCreateLoader(int loaderId, Bundle params) {
        return new BookLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Category>> listLoader, List<Category> categories) {
        if (listLoader.getId() == BOOK_LOADER_ID) {
            progressView.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    progressView.setVisibility(View.GONE);
                    progressView.setAlpha(1);
                }
            });
            if (recyclerView.getVisibility() != View.VISIBLE) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAlpha(0);
                recyclerView.animate().alpha(1);
                categoriesAdapter.setData(categories);
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<List<Category>> listLoader) {
        if (listLoader.getId() == BOOK_LOADER_ID) {
            categoriesAdapter.setData(null);
        }
    }
}
