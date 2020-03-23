package com.bigbang.mvvmintro.network;

import com.bigbang.mvvmintro.model.GitResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.bigbang.mvvmintro.util.Constants.*;

public interface GitService {

    @GET(GET_URL_POSTFIX)
    Call<List<GitResult>> getRepositories(@Path(USER_NAME)String userName);

    @GET(GET_URL_POSTFIX)
    Observable<List<GitResult>> getRepositoriesRx(@Path(USER_NAME)String userName);
}
