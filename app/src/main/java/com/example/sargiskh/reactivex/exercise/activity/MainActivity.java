package com.example.sargiskh.reactivex.exercise.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.sargiskh.reactivex.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.first:
                intent = new Intent(this, RxJavaSimpleActivity.class);
                break;
            case R.id.second:
                intent = new Intent(this, ColorsActivity.class);
                break;
            case R.id.third:
                intent = new Intent(this, BooksActivity.class);
                break;
            case R.id.four:
                intent = new Intent(this, SchedulerActivity.class);
                break;
            case R.id.five:
                intent = new Intent(this, MultipleSubscribersActivity.class);
                break;
                default:
                    break;
        }

        startActivity(intent);
    }
}
