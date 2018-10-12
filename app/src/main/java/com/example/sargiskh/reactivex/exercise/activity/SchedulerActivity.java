package com.example.sargiskh.reactivex.exercise.activity;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sargiskh.reactivex.R;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SchedulerActivity extends AppCompatActivity {

//    private Disposable subscription;
    private DisposableObserver disposableObserver;
    private ProgressBar progressBar;
    private TextView messagearea;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        setTitle("Scheduler Activity");
        configureLayout();
        createObservable();
    }

    private void configureLayout() {
        progressBar = findViewById(R.id.progressBar);
        messagearea = findViewById(R.id.messagearea);
        button = findViewById(R.id.scheduleLongRunningOperation);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.fromCallable(callable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable1 -> {
                            progressBar.setVisibility(View.GONE);
                            button.setEnabled(false);
                            messagearea.setText(messagearea.getText().toString() + "\n" + "Progressbar set visible");
                        })
                        .subscribe(getDisposableObserver());
            }
        });
    }

    private DisposableObserver<String> getDisposableObserver() {
        disposableObserver = new DisposableObserver<String>() {

            @Override
            public void onNext(String message) {
                messagearea.setText(messagearea.getText().toString() +"\n" +"onNext " + message );
            }

            @Override
            public void onError(Throwable e) {
                messagearea.setText(messagearea.getText().toString() +"\n" +"OnError" );
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                messagearea.setText(messagearea.getText().toString() +"\n" +"Hidding Progressbar");
            }

            @Override
            public void onComplete() {
                messagearea.setText(messagearea.getText().toString() +"\n" +"OnComplete" );
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                messagearea.setText(messagearea.getText().toString() +"\n" +"Hidding Progressbar" +"\n");
            }
        };

        return disposableObserver;
    }

    private Callable<String> callable = new Callable<String>() {

        @Override
        public String call() throws Exception {
            return doSomethingLong();
        }

    };

    public String doSomethingLong(){
        SystemClock.sleep(1000);
        return "Hello";
    }


    private void createObservable() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposableObserver != null && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }
    }
}
