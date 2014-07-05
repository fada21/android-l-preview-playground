package com.fada21.android.samplereaderlibraryscreen.model;

import com.fada21.android.samplereaderlibraryscreen.R;

public enum CategoryEnum {
    CRIME(0, "crime"),
    COMEDY(1, "comedy"),
    HISTORY(2, "history"),
    THRILLER(3, "thriller");

    private final int index;
    private final String route;
    public final static int catNameResId = R.array.categories;

    CategoryEnum(int idx, String route) {
        index = idx;
        this.route = route;
    }

    public int getIndex() {
        return index;
    }

    public String getRoute() {
        return route;
    }

    public static int getCatNameResId() {
        return catNameResId;
    }
}
