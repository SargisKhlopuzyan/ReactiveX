package com.example.sargiskh.reactivex.study.rx_android;

import android.os.SystemClock;
import android.util.Log;

import io.reactivex.Observable;

public class Downloader {

    final Observable<Integer> serverDownLoadObservable = Observable.create(emitter -> {

        for (int i = 0; i < 5; i++) {
            Log.e("LOG_TAG", "");
            emitter.onNext(i);
            SystemClock.sleep(3000); // simulate delay
        }
        emitter.onComplete();
    });

}
