package com.amataee.trivia.data;

import com.amataee.trivia.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {

    void processFinished(ArrayList<Question> questionArrayList);
}
