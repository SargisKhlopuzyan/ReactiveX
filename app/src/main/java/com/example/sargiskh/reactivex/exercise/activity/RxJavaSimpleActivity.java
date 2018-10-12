package com.example.sargiskh.reactivex.exercise.activity;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sargiskh.reactivex.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaSimpleActivity extends AppCompatActivity {

    private CompositeDisposable disposable = new CompositeDisposable();

    final Observable<Integer> serverDownloadObservable = Observable.create(emitter -> {
        Log.e("LOG_TAG", "serverDownloadObservable");
        SystemClock.sleep(5000); //simulate delay
        emitter.onNext(5);
        emitter.onComplete();
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_simple);
        setTitle("Rx Java Simple Activity");
        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            updateTheUserInterface(-1);
            view.setEnabled(false); // disables the button until execution has finished

            Disposable subscribe = serverDownloadObservable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(integer -> {
                        updateTheUserInterface(integer);
                        button.setEnabled(true);
                    });
            disposable.add(subscribe);

        });
    }

    private void updateTheUserInterface(Integer integer) {
        TextView textView = findViewById(R.id.resultView);
        textView.setText(String.valueOf(integer));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private int value = 0;
    public void onClick(View view) {
        Toast.makeText(this, "Still active " + value++ , Toast.LENGTH_SHORT).show();
    }

}
