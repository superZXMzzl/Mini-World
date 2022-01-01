package com.laioffer.tinnews.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.databinding.SearchNewsItemBinding;
import com.laioffer.tinnews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {

    interface ItemCallback {
        void onOpenDetails(Article article);
    }

    private ItemCallback itemCallback;

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }

    //create a container
    private List<Article> articles = new ArrayList<>();
    //API Supporting data --> set articles
    public void setArticles(List<Article> newsList) {
        //clear previous content
        articles.clear();
        //set new content
        articles.addAll(newsList);
        //similar to "onClick"
        notifyDataSetChanged();
    }

    //create a new view
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        //create a view each time
        return new SearchNewsViewHolder(view);
    }

    //when we reuse a view
    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        //get current article
        Article article = articles.get(position);
        //pass title of article into Holder
        holder.itemTitleTextView.setText(article.title);
        //use "Picasso" library to get urlImage --> there is no directly set image url function
        Picasso.get().load(article.urlToImage).resize(200, 200).into(holder.itemImageView);
        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetails(article));
    }

    //size to manage the position(index)
    @Override
    public int getItemCount() {
        return articles.size();
    }

    // 3. SearchNewsViewHolder:
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;
        }
    }

}
