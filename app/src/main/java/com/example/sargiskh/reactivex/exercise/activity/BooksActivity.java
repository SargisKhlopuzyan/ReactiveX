package com.example.sargiskh.reactivex.exercise.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sargiskh.reactivex.R;
import com.example.sargiskh.reactivex.exercise.adapter.SimpleStringAdapter;
import com.example.sargiskh.reactivex.exercise.server.RestClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BooksActivity extends AppCompatActivity {

    private Disposable bookDisposable;

    private RecyclerView booksRecyclerView;
    private ProgressBar progressBar;

    private SimpleStringAdapter simpleStringAdapter;

    private RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        setTitle("Books Activity");
        restClient = new RestClient(this);
        configureLayout();
        createObservable();
    }

    private void configureLayout() {
        progressBar = findViewById(R.id.loader);
        booksRecyclerView = findViewById(R.id.books_list);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleStringAdapter = new SimpleStringAdapter();
        booksRecyclerView.setAdapter(simpleStringAdapter);
    }

    private void createObservable() {
        // A java.util.Callable is like a runnable but it can throw an exception and return a value.
        Observable<List<String>> booksObservable = Observable.fromCallable(() -> restClient.getFavoriteBooks());
        bookDisposable = booksObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> displayBooks(strings));
    }

    private void displayBooks(List<String> books) {
        simpleStringAdapter.setStrings(books);
        progressBar.setVisibility(View.GONE);
        booksRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bookDisposable != null && !bookDisposable.isDisposed()) {
            bookDisposable.dispose();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bookDisposable != null && !bookDisposable.isDisposed()) {
            bookDisposable.dispose();
        }
    }

}
