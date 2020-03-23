package com.bigbang.mvvmintro.network;


import com.bigbang.mvvmintro.model.GitResult;
import com.bigbang.mvvmintro.util.Constants;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitRetrofitInstance {

    private GitService gitService;

    private OkHttpClient client;

    public GitRetrofitInstance() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        gitService = createGitService(getRetrofitInstance());


    }

    private Retrofit getRetrofitInstance() {

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private GitService createGitService(Retrofit retrofitInstance) {
        return retrofitInstance.create(GitService.class);
    }

    public Call<List<GitResult>> getRepositories(String userName) {
        return gitService.getRepositories(userName);
    }

    public Observable<List<GitResult>> getRepositoriesRx(String userName) {
        return gitService.getRepositoriesRx(userName);
    }
}
