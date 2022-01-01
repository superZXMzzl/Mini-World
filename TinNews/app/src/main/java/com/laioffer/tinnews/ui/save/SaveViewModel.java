package com.laioffer.tinnews.ui.save;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.repository.NewsRepository;

import java.util.List;

public class SaveViewModel extends ViewModel {
    private final NewsRepository repository;
    public SaveViewModel(NewsRepository repository){
        this.repository = repository;
    }
    //getAllSavedArticles gets all the saved articles from the database
    //Any updates in the Article table will immediately trigger new updates through the LiveData
    public LiveData<List<Article>> getAllSavedArticles(){
        return repository.getAllSavedArticles();
    }
    public void deleteSavedArticle(Article article){
        repository.deleteSavedArticle(article);
    }
}
