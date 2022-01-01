package com.laioffer.tinnews.ui.save;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.databinding.SavedNewsItemBinding;
import com.laioffer.tinnews.model.Article;

import java.util.ArrayList;
import java.util.List;

//make SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>
public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder> {
    //We want to remove items from the saved favorite by clicking on the heart icon
    //We add an ItemCallback to relay the events from inside SaveNewsAdapter to SaveFragment
    interface ItemCallback{
        //onOpenDetails is to be implemented for opening a new fragment for article details
        void onOpenDetails(Article article);
        //onRemoveFavorite is te be implemented to remove articles in the saved database
        void onRemoveFavorite(Article article);
    }
    private ItemCallback itemCallback;
    public void setItemCallback(ItemCallback itemCallback){
        this.itemCallback = itemCallback;
    }

    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();
    public void setArticles(List<Article> newsList){
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();
    }

    // 2. Adapter overrides: Only as many view holders as needed to display the on-screen portion of the dynamic content are created
    //onCreateViewHolder is for providing the generated item views
    @NonNull
    @Override
    public SavedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_item, parent, false);
        return new SavedNewsViewHolder(view);
    }
    //onBindViewHolder is for binding the data with a view
    @Override
    public void onBindViewHolder(@NonNull SavedNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.authorTextView.setText(article.author);
        holder.descriptionTextView.setText(article.description);
        //Use the itemCallback to inform the implementer the onRemoveFavorite event when the favoriteIcon is clicked
        //Also inform the opening for details event
        holder.favoriteIcon.setOnClickListener(v -> itemCallback.onRemoveFavorite(article));
        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetails(article));
    }
    //getItemCount is for providing the current data collection size
    @Override
    public int getItemCount() {
        return articles.size();
    }

    // 3. SavedNewsViewHolder: add a SavedNewsViewHolder as an inner class for holding the view references
    public static class SavedNewsViewHolder extends RecyclerView.ViewHolder {
        TextView authorTextView;
        TextView descriptionTextView;
        ImageView favoriteIcon;
        public SavedNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SavedNewsItemBinding binding = SavedNewsItemBinding.bind(itemView);
            authorTextView = binding.savedItemAuthorContent;
            descriptionTextView = binding.savedItemDescriptionContent;
            favoriteIcon = binding.savedItemFavoriteImageView;
        }
    }
}
