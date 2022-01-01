package com.laioffer.tinnews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.repository.NewsRepository;

public class HomeViewModel extends ViewModel {

    private final NewsRepository repository;
    //another pipe to store string
    private final MutableLiveData<String> countryInput = new MutableLiveData<>();

    //connect with repository by pass in parameter
    public HomeViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    //seperate into two APIs to manage the input and output
    //input API --> search specific country
    public void setCountryInput(String country) {
        countryInput.setValue(country);
    }
    //receive API --> get searched information
    public LiveData<NewsResponse> getTopHeadlines() {
        //if countryInput has item
        //then
        //it will pass the item in countryInput to next level --> nested function
        //:: is method reference
        return Transformations.switchMap(countryInput, repository::getTopHeadlines);
    }

    public void setFavoriteArticleInput(Article article) {
        repository.favoriteArticle(article);
    }

}
