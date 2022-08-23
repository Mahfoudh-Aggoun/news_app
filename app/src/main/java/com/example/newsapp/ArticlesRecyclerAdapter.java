package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArticlesRecyclerAdapter extends RecyclerView.Adapter<ArticlesRecyclerAdapter.MyViewHolder> {
    List<Article> articlesList;
    RecyclerItemClickListener mListener;
    Context mContext;

    public ArticlesRecyclerAdapter(List<Article> articlesList, Context mContext, RecyclerItemClickListener listener) {
        this.articlesList = articlesList;
        this.mContext = mContext;
        this.mListener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Article currentArticle = articlesList.get(position);
        holder.title.setText(currentArticle.getTitle());
        holder.sectionName.setText(currentArticle.getSectionName());
        holder.authorName.setText(currentArticle.getAuthorName());
        holder.publishDate.setText(currentArticle.getPublishDate());
        Glide.with(mContext).load(currentArticle.getThumbnailUrl()).into(holder.thumbnail);
        Glide.with(mContext).load(currentArticle.getAuthorImageUrl()).into(holder.authorImage);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView thumbnail = itemView.findViewById(R.id.thumbnail);
        ImageView authorImage = itemView.findViewById(R.id.author_image);
        TextView title = itemView.findViewById(R.id.article_name);
        TextView sectionName = itemView.findViewById(R.id.section_name);
        TextView authorName = itemView.findViewById(R.id.author_name);
        TextView publishDate = itemView.findViewById(R.id.publish_date);
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
                int position = getAdapterPosition();
                Article currentPlace = articlesList.get(position);
                mListener.onItemClick(currentPlace);
            }
        }
    }
}
