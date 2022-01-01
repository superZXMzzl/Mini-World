package com.laioffer.tinnews.ui.save;

import android.media.audiofx.AcousticEchoCanceler;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laioffer.tinnews.R;
import com.laioffer.tinnews.databinding.FragmentSaveBinding;
import com.laioffer.tinnews.model.Article;
import com.laioffer.tinnews.repository.NewsRepository;
import com.laioffer.tinnews.repository.NewsViewModelFactory;
import com.laioffer.tinnews.ui.search.SearchViewModel;

public class SaveFragment extends Fragment {
    private FragmentSaveBinding binding;

    private SaveViewModel viewModel;

    public SaveFragment() {
        // Required empty public constructor
    }

    //create a view before manage that view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_save, container, false);
        binding = FragmentSaveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //added the setup for the RecyclerView
        SavedNewsAdapter savedNewsAdapter = new SavedNewsAdapter();
        binding.newsSavedRecyclerView.setAdapter(savedNewsAdapter);
        binding.newsSavedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));



        NewsRepository repository = new NewsRepository();
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SaveViewModel.class);
        viewModel.getAllSavedArticles().observe(getViewLifecycleOwner(),
                savedArticles -> {
                    if (savedArticles != null){
                        Log.d("SaveFragment", savedArticles.toString());
                        //Set the data once fetch results are returned. In SaveFragment.onViewCreated
                        savedNewsAdapter.setArticles(savedArticles);
                    }
                });

        //Provide an anonymous implementation of ItemCallback to the savedNewsAdapter
        //in the SaveFragment.onViewCreated
        savedNewsAdapter.setItemCallback(new SavedNewsAdapter.ItemCallback() {
            @Override
            public void onOpenDetails(Article article) {
                // TODO
                Log.d("onOpenDetails", article.toString());
                SaveFragmentDirections.ActionNavigationSaveToNavigationDetails direction = SaveFragmentDirections.actionNavigationSaveToNavigationDetails(article);
                   NavHostFragment.findNavController(SaveFragment.this).navigate(direction);

            }

            //We call viewModel.deleteSavedArticle when onRemoveFavorite happens
            @Override
            public void onRemoveFavorite(Article article) {
                viewModel.deleteSavedArticle(article);
            }
        });

    }


}