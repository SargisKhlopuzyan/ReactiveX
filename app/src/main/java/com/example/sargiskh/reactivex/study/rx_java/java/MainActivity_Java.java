package com.example.sargiskh.reactivex.study.rx_java.java;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sargiskh.reactivex.R;

import java.util.Collections;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class MainActivity_Java extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java);

        // *********************** Creating observables *********************** //
        // EXAMPLE ----- Observable -----
        // Emits 0 or n items and terminates with an success or an error event.
        Observable<Todo> todoObservable = Observable.create(new ObservableOnSubscribe<Todo>() {
            @Override
            public void subscribe(ObservableEmitter<Todo> emitter) throws Exception {
//                List<Todo> todos = RxJavaUnitTest.this.getTodos();
                try {
                    List<Todo> todos = getTodos();
                    for (Todo todo : todos) {
                        emitter.onNext(todo);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });

        Observable<Todo> todoObservableLambda = Observable.create(emitter -> {
            try {
                List<Todo> todos = getTodos();
                for (Todo todo : todos) {
                    emitter.onNext(todo);
                }
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

        //========================================================================================//

        // EXAMPLE ----- Maybe -----
        // Succeeds with an item, or no item, or errors. The reactive version of an Optional.
        Maybe<List<Todo>> todoMaybe = Maybe.create(emitter -> {
            try {
                List<Todo> todos = getTodos();
                if(todos != null && !todos.isEmpty()) {
                    emitter.onSuccess(todos); // java.util.Optional has a value
                } else {
                    emitter.onComplete(); // java.util.Optional contains no value → null
                }
            } catch (Exception e) {
                emitter.onError(e); // An error occurred
            }
        });

        //========================================================================================//



        // *********************** Subscribing in RxJava *********************** //

        // Simply subscribe with a io.reactivex.functions.Consumer<T>, which will be informed onNext()
        Disposable disposable = todoObservable.subscribe(todo -> Log.e("LOG_TAG", "todo"));

        // Dispose the subscription when not interested in the emitted data any more
        disposable.dispose();

        //========================================================================================//

        // Also handle the error case with a second io.reactivex.functions.Consumer<T>
        Disposable disposableWithError = todoObservable.subscribe(t -> System.out.print(t), e -> e.printStackTrace());
        disposableWithError.dispose();


        //========================================================================================//


        // ----- subscribeWith -----
        DisposableObserver<Todo> disposableObserver = todoObservable.subscribeWith(new DisposableObserver<Todo>() {

            @Override
            public void onNext(Todo todo) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
//        disposableObserver.dispose();


        //========================================================================================//

        // *********************** Disposing subscriptions and using CompositeDisposable *********************** //

        Single<List<Todo>> todosSingle = Single.create(emitter -> {
            try {
                emitter.onSuccess(getTodos());
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

        Disposable disposableSingle = todosSingle.subscribeWith(new DisposableSingleObserver<List<Todo>>() {

            @Override
            public void onSuccess(List<Todo> todos) {
                // work with the resulting todos
            }

            @Override
            public void onError(Throwable e) {
                // handle the error case
            }
        });

        // continue working and dispose when value of the Single is not interesting any more
        disposableSingle.dispose();

        //========================================================================================//

        CompositeDisposable compositeDisposable = new CompositeDisposable();

        Single<List<Happiness>> happinessSingle = Single.create(emitter -> {
            try {
                emitter.onSuccess(getHappinesses());
            } catch (Exception e) {
                emitter.onError(e);
            }
        });


        compositeDisposable.add(todosSingle.subscribeWith(new DisposableSingleObserver<List<Todo>>() {

            @Override
            public void onSuccess(List<Todo> todos) {

            }

            @Override
            public void onError(Throwable e) {

            }

        }));

        compositeDisposable.add(happinessSingle.subscribeWith(new DisposableSingleObserver<List<Happiness>>() {

            @Override
            public void onSuccess(List<Happiness> happinesses) {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("LOG_TAG","Don't worry, be happy! :-P");
            }
        }));

        compositeDisposable.dispose();


        //========================================================================================//



    }

    private List<Happiness> getHappinesses() {
        List<Happiness> happinesses = Collections.emptyList();
        happinesses.add(new Happiness());
        happinesses.add(new Happiness());
        happinesses.add(new Happiness());
        happinesses.add(new Happiness());
        return happinesses;
    }

    private List<Todo> getTodos() {
        List<Todo> todos = Collections.emptyList();
        todos.add(new Todo());
        todos.add(new Todo());
        todos.add(new Todo());
        todos.add(new Todo());
        return todos;
    }

}
