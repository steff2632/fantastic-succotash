package com.example.fantastic_succotash.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantastic_succotash.R;
import com.example.fantastic_succotash.activities.DetailsActivity;
import com.example.fantastic_succotash.data.News;
import com.squareup.picasso.Picasso;

/**
 * Created by stefanmay on 27/10/2016.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private News[] newsList = new News[0];
    private boolean gridLayout = false;

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsActivity.newsItem = (News) itemView.getTag();
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        return new NewsViewHolder(itemView);
    }

    public void setGridLayout(boolean gridLayout) {
        this.gridLayout = gridLayout;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsList.length;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Picasso.with(holder.image.getContext())
                .load(newsList[position].image)
                .resizeDimen(R.dimen.news_item_image_width, R.dimen.news_item_image_height)
                .centerCrop()
                .error(R.drawable.touchnote)
                .into(holder.image);

        holder.txtDescription.setText(newsList[position].description);
        holder.txtTitle.setText(newsList[position].title);
        holder.itemView.setTag(newsList[position]);

        if(gridLayout) {
            holder.txtDescription.setVisibility(View.GONE);
            holder.txtTitle.setVisibility(View.GONE);
        } else {
            holder.txtDescription.setVisibility(View.VISIBLE);
            holder.txtTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setData(News[] data) {
        newsList = data;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView txtDescription;
        TextView txtTitle;

        NewsViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
            txtDescription = (TextView) itemView.findViewById(R.id.txt_desc);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
        }
    }
}
