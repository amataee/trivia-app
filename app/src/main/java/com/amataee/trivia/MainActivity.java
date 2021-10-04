package com.amataee.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amataee.trivia.controller.AppController;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

public class MainActivity extends AppCompatActivity {
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("TAG", "onCreate: Yeah!");
                }, error -> {

        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
}