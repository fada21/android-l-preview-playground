package com.fada21.android.samplereaderlibraryscreen.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.fada21.android.samplereaderlibraryscreen.R;
import com.squareup.picasso.Picasso;

public class BookActivity extends FragmentActivity {

    public static final String THUMB_EXTRA = "THUMB_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_book);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView iv = (ImageView) findViewById(R.id.thumb);
        String stringExtra = getIntent().getStringExtra(THUMB_EXTRA);
        Picasso.with(this).load(stringExtra).into(iv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
