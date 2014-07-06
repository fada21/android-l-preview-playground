package com.fada21.android.samplereaderlibraryscreen.adapter;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fada21.android.samplereaderlibraryscreen.R;
import com.fada21.android.samplereaderlibraryscreen.activity.BookActivity;
import com.fada21.android.samplereaderlibraryscreen.activity.LibraryActivity;
import com.fada21.android.samplereaderlibraryscreen.model.Book;
import com.fada21.android.samplereaderlibraryscreen.model.Category;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final Context context = viewHolder.categoryHeading.getContext();
        Category category = categoriesList.get(position);
        String name = context.getResources().getStringArray(R.array.categories)[category.getIndex()];
        List<Book> booksList = category.getBooksList();

        String heading = context.getResources().getString(R.string.category_name, name, booksList.size());
        viewHolder.categoryHeading.setText(heading);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int margin = (int) context.getResources().getDimension(R.dimen.space_m);

        LinearLayout container = prepareContainer(viewHolder, position);

        LinearLayout row = null;
        int size = category.getBooksList().size();
        for (int i = 0; i < size; i++) {
            final Book book = category.getBooksList().get(i);
            View bookLayout = createBookLayout(book, layoutInflater, container);

            final TextView tvTitle = setBookTitle(book, bookLayout);

            TextView tvAuthors = setBookAuthors(book, bookLayout);

            ImageView ivThumb = (ImageView) bookLayout.findViewById(R.id.thumb);
            Picasso.with(context).load(book.getThumbUrl()).into(ivThumb, new PicassoWithPaletteCallback(tvTitle, tvAuthors, ivThumb));

//            activity transitions not working
//            ivThumb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent i = new Intent(context, BookActivity.class);
//                    i.putExtra(BookActivity.THUMB_EXTRA, book.getThumbUrl());
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((LibraryActivity) view.getContext(), view, "thumb");
//                    context.startActivity(i, options.toBundle());
//                }
//            });

            setBookLayoutMargins(bookLayout, margin, margin);

            if (expandedMap[position]) {
                if (row == null) {
                    row = new LinearLayout(context);
                    row.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                    row.setOrientation(LinearLayout.HORIZONTAL);
                }
                row.addView(bookLayout);
                if (i % 3 == 2) {
                    container.addView(row);
                    row = null;
                } else if (i == size - 1) {
                    container.addView(row);
                }

            } else {
                container.addView(bookLayout);
            }
        }

        setExpandButton(viewHolder, position);

    }

    private void setExpandButton(ViewHolder viewHolder, final int position) {
        ImageButton imageButton = viewHolder.btn;
        final Drawable imageButtonDrawable = imageButton.getDrawable();
        imageButtonDrawable.setLevel(expandedMap[position] ? 1 : 0);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandedMap[position] = !expandedMap[position];
                notifyItemChanged(position);
            }
        });
    }

    private LinearLayout prepareContainer(ViewHolder viewHolder, int position) {
        LinearLayout container;
        if (expandedMap[position]) { // true for expanded
            container = viewHolder.booksExpandedContainer;
            viewHolder.booksCollapsed.setVisibility(View.GONE);
            viewHolder.booksExpandedContainer.setVisibility(View.VISIBLE);
        } else {
            container = viewHolder.booksCollapsedContainer;
            viewHolder.booksExpandedContainer.setVisibility(View.GONE);
            viewHolder.booksCollapsed.setVisibility(View.VISIBLE);
        }
        container.removeAllViews();
        return container;
    }

    private TextView setBookTitle(Book book, View bookLayout) {
        final TextView tvTitle = (TextView) bookLayout.findViewById(R.id.title);
        tvTitle.setText(book.getTitle());
        return tvTitle;
    }

    private TextView setBookAuthors(Book book, View bookLayout) {
        TextView tvAuthors = (TextView) bookLayout.findViewById(R.id.authors);
        StringBuilder sb = new StringBuilder();
        for (String author : book.getAuthor()) {
            sb.append(author).append("\n");
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        tvAuthors.setText(sb.toString());
        return tvAuthors;
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
        public ImageButton btn;

        public View booksCollapsed;
        public LinearLayout booksCollapsedContainer;
        public LinearLayout booksExpandedContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryHeading = (TextView) itemView.findViewById(R.id.category_heading);
            btn = (ImageButton) itemView.findViewById(R.id.btn_expand);
            booksCollapsed = itemView.findViewById(R.id.collapsed);
            booksCollapsedContainer = (LinearLayout) itemView.findViewById(R.id.books_collapsed);
            booksExpandedContainer = (LinearLayout) itemView.findViewById(R.id.books_expanded);
        }
    }
}
