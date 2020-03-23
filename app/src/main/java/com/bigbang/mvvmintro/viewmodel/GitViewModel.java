package com.bigbang.mvvmintro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.bigbang.mvvmintro.model.GitResult;
import com.bigbang.mvvmintro.network.GitRetrofitInstance;
import com.bigbang.mvvmintro.util.Constants;
import com.bigbang.mvvmintro.util.DebugLogger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GitViewModel extends AndroidViewModel {

    private GitRetrofitInstance gitRetrofitInstance;
    private MutableLiveData<List<GitResult>> gitResultLiveData = new MutableLiveData<>();

    private int x =0;

    public GitViewModel(@NonNull Application application) {
        super(application);
        gitRetrofitInstance = new GitRetrofitInstance();

    }
    public MutableLiveData<List<GitResult>> getGitList(String userName) {
        gitRetrofitInstance.getRepositories(userName)
                .enqueue(new Callback<List<GitResult>>() {
                    @Override
                    public void onResponse(Call<List<GitResult>> call, Response<List<GitResult>> response) {

                        if(response.isSuccessful() && response.body()!= null)
                            gitResultLiveData.setValue(response.body());
                        else
                            DebugLogger.logError(new Throwable(Constants.RESULTS_NULL));
                    }
                    @Override
                    public void onFailure(Call<List<GitResult>> call, Throwable t) {
                        DebugLogger.logError(t);
                    }
                });

        return gitResultLiveData;

    }

    public Observable<List<GitResult>> getGitListRx(String userName) {
        return  gitRetrofitInstance
                .getRepositoriesRx(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
