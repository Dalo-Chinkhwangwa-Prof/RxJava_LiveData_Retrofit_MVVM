package com.bigbang.mvvmintro.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.bigbang.mvvmintro.R;
import com.bigbang.mvvmintro.model.GitResult;
import com.bigbang.mvvmintro.util.DebugLogger;
import com.bigbang.mvvmintro.viewmodel.GitViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomeActivity extends AppCompatActivity {

    private GitViewModel viewModel;

//    LivaData
    private Observer<List<GitResult>> listObserver;

//    RxJava
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(GitViewModel.class);
//        LIVEDATA--------------------------------------------------------------------------->
        listObserver = listResult -> displayInformation(listResult);
        viewModel.getGitList("jdeshaies1982")
                .observe(this, listObserver);
//        END LIVEDATA--------------------------------------------------------------------------->

//        RxJava--------------------------------------------------------------------------->
        compositeDisposable.add(viewModel.getGitListRx("Dalo-Chinkhwangwa-Prof").subscribe(gitResults -> {
            displayInformationRx(gitResults);
        }, throwable -> {
            DebugLogger.logError(throwable);

        }));
//        RxJava--------------------------------------------------------------------------->
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
//    LiveData
    private void displayInformation(List<GitResult> gitResults) {
        for (int i = 0; i < gitResults.size(); i++) {
            DebugLogger.logDebug(gitResults.get(i).getName());
        }
    }

//    RxJava
    private void displayInformationRx(List<GitResult> gitResults) {
        for (int i = 0; i < gitResults.size(); i++) {
            DebugLogger.logDebug("RxJava : "+gitResults.get(i).getName());
        }
    }
}
