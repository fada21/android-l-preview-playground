package com.fada21.android.samplereaderlibraryscreen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fada21.android.samplereaderlibraryscreen.R;
import com.fada21.android.samplereaderlibraryscreen.model.Book;
import com.fada21.android.samplereaderlibraryscreen.model.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<Category> categoriesList;
    private boolean[] expandedMap;

    public CategoriesAdapter(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
        expandedMap = new boolean[getItemCount()];
    }

    public void setData(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
        expandedMap = new boolean[getItemCount()];
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vg_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Category category = categoriesList.get(position);
        String name = category.getName();
        List<Book> booksList = category.getBooksList();

        String heading = viewHolder.categoryHeading.getContext().getResources().getString(R.string.category_name, name, booksList.size());

        Context context = viewHolder.booksContainer.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int margin = (int) context.getResources().getDimension(R.dimen.space_m);

        for (Book book : category.getBooksList()) {
            View bookLayout = createBookLayout(book, layoutInflater, viewHolder.booksContainer);
            setBookLayoutMargins(bookLayout, margin, margin);
            viewHolder.booksContainer.addView(bookLayout);

        }

        viewHolder.categoryHeading.setText(heading);
    }

    private View createBookLayout(Book book, LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.vg_book, parent, false);
        return view;
    }

    private void setBookLayoutMargins(View view, int leftMargin, int bottomMargin) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof  ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.setMargins(leftMargin, 0, 0, bottomMargin);
        }
    }

    @Override
    public int getItemCount() {
        return categoriesList != null ? categoriesList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryHeading;
        public LinearLayout booksContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryHeading = (TextView) itemView.findViewById(R.id.category_heading);
            booksContainer = (LinearLayout) itemView.findViewById(R.id.books);
        }
    }
}
