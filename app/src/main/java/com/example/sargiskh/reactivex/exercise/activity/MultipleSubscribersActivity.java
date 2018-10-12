package com.example.sargiskh.reactivex.exercise.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sargiskh.reactivex.R;
import com.example.sargiskh.reactivex.exercise.server.ServerMultipleSubscribers;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MultipleSubscribersActivity extends AppCompatActivity {

    ServerMultipleSubscribers serverMultipleSubscribers = new ServerMultipleSubscribers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_subscribers);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subscribe_1:
                serverMultipleSubscribers.observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(strings -> { Log.e("LOG_TAG", "disposable1: " + strings.hashCode()); });
                break;
            case R.id.subscribe_2:
                serverMultipleSubscribers.observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(strings -> { Log.e("LOG_TAG", "disposable2: " + strings.hashCode()); });
                break;
            case R.id.subscribe_3:
                serverMultipleSubscribers.observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(strings -> { Log.e("LOG_TAG", "disposable3: " + strings.hashCode()); });
                break;
            case R.id.subscribe_4:
                serverMultipleSubscribers.observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(strings -> { Log.e("LOG_TAG", "disposable4: " + strings.hashCode()); });
                break;
            case R.id.subscribe_5:
                serverMultipleSubscribers.observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(strings -> { Log.e("LOG_TAG", "disposable5: " + strings.hashCode()); });
                break;
            default:
                break;
        }
    }
}
