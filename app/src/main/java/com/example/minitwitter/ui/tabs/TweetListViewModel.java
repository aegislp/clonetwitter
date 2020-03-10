package com.example.minitwitter.ui.tabs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.minitwitter.data.TweetRepository;
import com.example.minitwitter.retrofit.response.Tweet;

import java.util.List;

public class TweetListViewModel extends AndroidViewModel {

    private TweetRepository repository;
    private LiveData<List<Tweet>> allTweets;


    public TweetListViewModel(@NonNull Application application) {
        super(application);

        repository = new TweetRepository();

        allTweets = repository.getAllTweets();

    }

    public LiveData<List<Tweet>> getTweets(){ return allTweets;}
}
