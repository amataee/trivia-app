package com.amataee.trivia.data;

import android.util.Log;

import com.amataee.trivia.controller.AppController;
import com.amataee.trivia.model.Question;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    ArrayList<Question> questionArrayList = new ArrayList<>();

    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 1; i <= 100; i++) {
                        try {
                            Question question = new Question(response.getJSONArray(i).getString(0),
                                    response.getJSONArray(i).getBoolean(1));

                            // Add question to arrayList/List
                            questionArrayList.add(question);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    if(null != callBack) callBack.processFinished(questionArrayList);

                }, error -> {

        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}
