package com.example.sargiskh.reactivex.study.rx_java.java.v_caching_values_of_completed_observables;

import android.util.Log;

import com.example.sargiskh.reactivex.study.rx_java.java.Todo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class CachingValuesOfCompletedObservables {

    public void mainConversionBetweenTypes() {

        Single<List<Todo>> todosSingle = Single.create(emitter -> {

            Thread thread = new Thread(() -> {
                try {
                    List<Todo> todosFromWeb = new ArrayList<>();// query a webservice
                    todosFromWeb.add(new Todo());
                    todosFromWeb.add(new Todo());
                    todosFromWeb.add(new Todo());
                    Log.e("LOG_TAG", "Called 4 times!");
                    emitter.onSuccess(todosFromWeb);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();

        });

//        todosSingle.subscribe((todos, throwable) -> {
//            Log.e("LOG_TAG", "todos: " + todos);
//        });
//        V.S.
        todosSingle.subscribe(todos -> {
            Log.e("LOG_TAG", "todos: " + todos);
        }, throwable -> {
            Log.e("LOG_TAG", "throwable: " + throwable);
        } );

//        todosSingle.subscribe(... " Show todos in gant diagram " ...);
//        showTodosInATable(todosSingle);
//        todosSingle.subscribe(... " Show todos in gant diagram " ...);


        // cache the result of the single, so that the web query is only done once
        Single<List<Todo>> cachedSingle = todosSingle.cache();

        cachedSingle.subscribe(todos -> {
            Log.e("LOG_TAG", "todos: " + todos);
        }, throwable -> {
            Log.e("LOG_TAG", "throwable: " + throwable);
        } );

//        cachedSingle.subscribe(... " Show todos times in a bar chart " ...);
//        showTodosInATable(cachedSingle);
//        cachedSingle.subscribe(... " Show todos in gant diagram " ...);
//        anotherMethodThatsSupposedToSubscribeTheSameSingle(cachedSingle);
    }

}
