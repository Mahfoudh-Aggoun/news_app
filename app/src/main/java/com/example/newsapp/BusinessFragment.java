package com.example.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class BusinessFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Article>>, RecyclerItemClickListener {


    private static final int ARTICLE_LOADER_ID = 4;

    int currentPage = 1;

    Uri.Builder builder = new Uri.Builder()
            .scheme("https")
            .authority("content.guardianapis.com")
            .appendPath("search")
            .appendQueryParameter("section", "business")
            .appendQueryParameter("page", Integer.toString(currentPage))
            .appendQueryParameter("show-fields", "thumbnail,body")
            .appendQueryParameter("show-tags", "contributor")
            .appendQueryParameter("api-key", "fdf2817a-0f90-45f1-b176-201532685161");
    private String myUrl = builder.build().toString();

    private RelativeLayout currentStateView;
    private TextView currentStateText;

    ArticlesRecyclerAdapter itemsAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_articles_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoaderManager loaderManager = getActivity().getLoaderManager();
        loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);

        FloatingActionButton nextPageButton = view.findViewById(R.id.next_page_sport);
        FloatingActionButton previousPageButton = view.findViewById(R.id.previous_page_sport);
        TextView currentPageIndicator = view.findViewById(R.id.current_page_sport);
        currentStateView = view.findViewById(R.id.current_state_layout);
        currentStateText = view.findViewById(R.id.current_state_text);
        currentStateText.setText(R.string.loading_data);

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                builder = new Uri.Builder()
                        .scheme("https")
                        .authority("content.guardianapis.com")
                        .appendPath("search")
                        .appendQueryParameter("section", "business")
                        .appendQueryParameter("page", Integer.toString(currentPage))
                        .appendQueryParameter("show-fields", "thumbnail,body")
                        .appendQueryParameter("show-tags", "contributor")
                        .appendQueryParameter("api-key", "fdf2817a-0f90-45f1-b176-201532685161");
                myUrl = builder.build().toString();
                currentPageIndicator.setText(Integer.toString(currentPage));
                loaderManager.restartLoader(ARTICLE_LOADER_ID, null, BusinessFragment.this);

            }
        });

        previousPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 1) {
                    currentPage--;
                    builder = new Uri.Builder()
                            .scheme("https")
                            .authority("content.guardianapis.com")
                            .appendPath("search")
                            .appendQueryParameter("section", "business")
                            .appendQueryParameter("page", Integer.toString(currentPage))
                            .appendQueryParameter("show-fields", "thumbnail,body")
                            .appendQueryParameter("show-tags", "contributor")
                            .appendQueryParameter("api-key", "fdf2817a-0f90-45f1-b176-201532685161");
                    myUrl = builder.build().toString();
                    currentPageIndicator.setText(Integer.toString(currentPage));
                    loaderManager.restartLoader(ARTICLE_LOADER_ID, null, BusinessFragment.this);
                }
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Loader<List<Article>> onCreateLoader(int id, @org.jetbrains.annotations.Nullable Bundle args) {
        return new ArticlesLoader(this.getContext(), myUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {

        if (articles == null) {
            currentStateText.setText(R.string.no_data_error_msg);
            currentStateView.setVisibility(View.VISIBLE);
        } else {
            currentStateView.setVisibility(View.GONE);
            itemsAdapter = new ArticlesRecyclerAdapter(articles, this.getContext(), this);
            RecyclerView recyclerView = (RecyclerView) requireView().findViewById(R.id.sports_recycler);
            RecyclerView.LayoutManager lm = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(lm);
            recyclerView.setAdapter(itemsAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
    }

    @Override
    public void onItemClick(Article currentArticle) {
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra("title", currentArticle.getTitle());
        intent.putExtra("authorName", currentArticle.getAuthorName());
        intent.putExtra("sectionName", currentArticle.getSectionName());
        intent.putExtra("thumbnailUrl", currentArticle.getThumbnailUrl());
        intent.putExtra("authorImageUrl", currentArticle.getAuthorImageUrl());
        intent.putExtra("webUrl", currentArticle.getWebUrl());
        intent.putExtra("publishDate", currentArticle.getPublishDate());
        intent.putExtra("body", currentArticle.getBody());
        startActivity(intent);
    }
}