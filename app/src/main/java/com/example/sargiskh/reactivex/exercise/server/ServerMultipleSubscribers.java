package com.example.sargiskh.reactivex.exercise.server;

import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

public class ServerMultipleSubscribers {

    /**
    public ConnectableObservable connectableObservable = Observable
            .create(subscriber -> { subscriber.onNext(createBooks());})
            .replay();
    */

    private List<String> createBooks() {
        SystemClock.sleep(10000);
        List<String> books = new ArrayList<>();
        books.add("Lord of the Rings");
        books.add("The dark elf");
        books.add("Eclipse Introduction");
        books.add("History book");
        books.add("Der kleine Prinz");
        books.add("7 habits of highly effective people");
        books.add("Other book 1");
        books.add("Other book 2");
        books.add("Other book 3");
        books.add("Other book 4");
        books.add("Other book 5");
        books.add("Other book 6");
        return books;
    }


    /**
    public Observable<List<String>> observable = Observable.just(createBooks())
            .publish()
            .autoConnect(5)
//            .map(strings -> {
//                Log.e("LOG_TAG","Expensive operation for " + strings);
//                return strings;
//            })
    ;
    */

    public Observable observable = Observable.create(emitter -> {
        Log.e("LOG_TAG", "serverDownloadObservable");
        List<String> strings = createBooks();
        emitter.onNext(strings);
//        emitter.onComplete();
    })
            .publish()
            .autoConnect(5);;
}