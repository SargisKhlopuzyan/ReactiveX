package com.example.sargiskh.reactivex.study.rx_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.sargiskh.reactivex.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity_Java_Study extends AppCompatActivity {

    private TextView textView;
    private Button button;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java_study);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(view -> callDownloaderServerDownLoadObservable());
    }

    private void callDownloaderServerDownLoadObservable() {
        disposable = new Downloader().serverDownLoadObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    handle(integer);
                });
    }

    private void callDownloaderServerDownLoadObservable2() {

    }


    private void handle(Integer integer) {
        textView.setText(integer.toString());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
