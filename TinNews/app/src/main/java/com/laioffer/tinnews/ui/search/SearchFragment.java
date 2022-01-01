package com.laioffer.tinnews.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.databinding.FragmentSearchBinding;
import com.laioffer.tinnews.repository.NewsRepository;
import com.laioffer.tinnews.repository.NewsViewModelFactory;

public class SearchFragment extends Fragment {
    private SearchViewModel viewModel;
    //"view Binding" --> connect xml layout with java class
    //eg. this one is from fragment_search
    private FragmentSearchBinding binding;

    public SearchFragment() {
        // Required empty public constructor
    }

    //create a view before manage that view by "inflate"
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //way 1: we pass xml directly here --> fragment_search
        //return inflater.inflate(R.layout.fragment_search, container, false);
        //way 2: use view binding we created before to get xml
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        //return top root of binding
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchNewsAdapter newsAdapter = new SearchNewsAdapter();
        //we need more than 1 column --> use gridLayout
        //parameter "requireContext" --> return the context connect with fragment
        //parameter "2" --> we need 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        binding.newsResultsRecyclerView.setLayoutManager(gridLayoutManager);
        binding.newsResultsRecyclerView.setAdapter(newsAdapter);

        //STEP 1 --> set input
        //"newsSearchView" is xml ID in our binding
        //set listener --> similar to "onClick"
        //need a anonymous class
        binding.newsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //when we submit
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    //similar to "covid-19"
                    viewModel.setSearchInput(query);
                }
                //to fix a bug --> onQueryTextSubmit processed twice
                binding.newsSearchView.clearFocus();
                return true;
            }

            @Override
            //when we edit in text
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //STEP 2 --> show output
        //create a repository
        NewsRepository repository = new NewsRepository();
        //way 1 --> create a viewModel each time by pass repository to viewModel
        //viewModel = new SearchViewModel(repository);
        //way 2 --> use factory pattern to build viewModel for reuse and store user history in that fixed viewModel
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SearchViewModel.class);
        //sample input
        //viewModel.setSearchInput("Covid-19");

        //output
        viewModel
                //return liveData
                .searchNews()
                //get item from liveData pipeline
                .observe(
                        //create a lifeCycle controller to manage when to destroy the lifeCycle
                        getViewLifecycleOwner(),
                        //use lambda to return response
                        newsResponse -> {
                            if (newsResponse != null) {
                                Log.d("SearchFragment", newsResponse.toString());
                                //send API return to newsAdaptor
                                newsAdapter.setArticles(newsResponse.articles);
                            }
                        });
        newsAdapter.setItemCallback(article -> {
            SearchFragmentDirections.ActionNavigationSearchToNavigationDetails direction = SearchFragmentDirections.actionNavigationSearchToNavigationDetails(article);
            NavHostFragment.findNavController(SearchFragment.this).navigate(direction);
        });

    }
}