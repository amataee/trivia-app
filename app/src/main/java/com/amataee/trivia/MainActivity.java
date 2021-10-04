package com.amataee.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amataee.trivia.controller.AppController;
import com.amataee.trivia.data.AnswerListAsyncResponse;
import com.amataee.trivia.data.Repository;
import com.amataee.trivia.model.Question;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Question> questions = new Repository().getQuestions(questionArrayList -> {
            Log.d("TAG", "onCreate: " + questionArrayList);
        });

    }
}