package com.amataee.trivia.data;

import android.util.Log;

import com.amataee.trivia.controller.AppController;
import com.amataee.trivia.model.Question;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    ArrayList<Question> questionArrayList = new ArrayList<>();

    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("Repo", "getQuestions: Yeah!");
                }, error -> {

        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return null;
    }
}
