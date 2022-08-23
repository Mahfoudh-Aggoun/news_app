package com.example.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class NewsDetailsActivity extends AppCompatActivity {

    private String title;
    private String authorName;
    private String sectionName;
    private String thumbnailUrl;
    private String authorImageUrl;
    private String webUrl;
    private String publishDate;
    private String body;

    ImageView thumbnailView;
    ImageView authorImageView;
    TextView authorNameView;
    TextView publishDateView;
    TextView sectionNameView;
    TextView titleView;
    TextView bodyView;
    ExtendedFloatingActionButton openInBrowserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        thumbnailView = findViewById(R.id.thumbnail_detail);
        authorNameView = findViewById(R.id.author_name_detail);
        publishDateView = findViewById(R.id.publish_date_detail);
        sectionNameView = findViewById(R.id.section_name_detail);
        titleView = findViewById(R.id.title_detail);
        bodyView = findViewById(R.id.body);
        authorImageView = findViewById(R.id.author_image_detail);
        openInBrowserButton = findViewById(R.id.open_in_browser);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        authorName = intent.getStringExtra("authorName");
        sectionName = intent.getStringExtra("sectionName");
        thumbnailUrl = intent.getStringExtra("thumbnailUrl");
        authorImageUrl = intent.getStringExtra("authorImageUrl");
        webUrl = intent.getStringExtra("webUrl");
        publishDate = intent.getStringExtra("publishDate");
        body = intent.getStringExtra("body");
        authorImageUrl = intent.getStringExtra("authorImageUrl");
        Glide.with(this).load(thumbnailUrl).into(thumbnailView);
        Glide.with(this).load(authorImageUrl).into(authorImageView);
        authorNameView.setText(authorName);
        publishDateView.setText(publishDate);
        sectionNameView.setText(sectionName);
        titleView.setText(title);
        bodyView.setText(Html.fromHtml(Html.fromHtml(body).toString()));

        openInBrowserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri articlesUri = Uri.parse(webUrl);

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articlesUri);
                if (websiteIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(websiteIntent);
                }

            }
        });
    }
}