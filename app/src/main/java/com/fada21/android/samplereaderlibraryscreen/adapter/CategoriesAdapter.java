package com.fada21.android.samplereaderlibraryscreen.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        final Context context = viewHolder.booksContainer.getContext();
        Category category = categoriesList.get(position);
        String name = context.getResources().getStringArray(R.array.categories)[category.getIndex()];
        List<Book> booksList = category.getBooksList();

        String heading = viewHolder.categoryHeading.getContext().getResources().getString(R.string.category_name, name, booksList.size());

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int margin = (int) context.getResources().getDimension(R.dimen.space_m);

        for (Book book : category.getBooksList()) {
            View bookLayout = createBookLayout(book, layoutInflater, viewHolder.booksContainer);

            final TextView tvTitle = (TextView) bookLayout.findViewById(R.id.title);
            tvTitle.setText(book.getTitle());

            TextView tvAuthors = (TextView) bookLayout.findViewById(R.id.authors);
            StringBuilder sb = new StringBuilder();
            for (String author : book.getAuthor()) {
                sb.append(author).append("\n");
            }
            if (sb.length() > 0)
                sb.deleteCharAt(sb.length() - 1);
            tvAuthors.setText(sb.toString());

            ImageView ivThumb = (ImageView) bookLayout.findViewById(R.id.thumb);
            Picasso.with(context).load(book.getThumbUrl()).into(ivThumb, new PicassoWithPaletteCallback(tvTitle, tvAuthors, ivThumb));

            setBookLayoutMargins(bookLayout, margin, margin);
            viewHolder.booksContainer.addView(bookLayout);

        }

        viewHolder.categoryHeading.setText(heading);
    }

    public static class PicassoWithPaletteCallback implements Callback {

        private TextView tvTitle;
        private TextView tvAuthors;
        private ImageView ivThumb;

        public PicassoWithPaletteCallback(TextView title, TextView authors, ImageView thumb) {
            this.tvTitle = title;
            this.tvAuthors = authors;
            this.ivThumb = thumb;
        }

        @Override
        public void onSuccess() {
            Bitmap bitmap = ((BitmapDrawable) ivThumb.getDrawable()).getBitmap();
            AsyncTask<Void, Void, Palette> paletteAsyncTask = Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    if (palette.getVibrantColor() != null) {
                        ObjectAnimator.ofArgb(tvTitle, "textColor", palette.getVibrantColor().getRgb()).start();
                    }
                    if (palette.getMutedColor() != null) {
                        ObjectAnimator.ofArgb(tvAuthors, "textColor", palette.getMutedColor().getRgb()).start();
                    }
                }
            });
        }

        @Override
        public void onError() {

        }
    }

    private View createBookLayout(Book book, LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.vg_book, parent, false);
        return view;
    }

    private void setBookLayoutMargins(View view, int leftMargin, int bottomMargin) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
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
