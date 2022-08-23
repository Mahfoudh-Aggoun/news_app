package com.example.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class ArticlesLoader extends AsyncTaskLoader<List<Article>> {

    private String mUrl;

    public ArticlesLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public List<Article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Article> articles = Utils.fetchArticlesData(mUrl);
        return articles;
    }
}
