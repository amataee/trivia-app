package com.amataee.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amataee.trivia.controller.AppController;
import com.amataee.trivia.data.Repository;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Repository().getQuestions();

    }
}