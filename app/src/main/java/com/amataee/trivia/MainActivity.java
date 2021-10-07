package com.amataee.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Handler;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.amataee.trivia.data.Repository;
import com.amataee.trivia.databinding.ActivityMainBinding;
import com.amataee.trivia.model.Question;
import com.amataee.trivia.model.Score;
import com.amataee.trivia.util.Prefs;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    List<Question> questionList;
    private int currentQuestionIndex = 1;
    private final Handler handler = new Handler();
    private int scoreCounter = 0;
    private Score score;
    private Prefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        score = new Score();

        prefs = new Prefs(this);
        currentQuestionIndex = prefs.getState();

        binding.scoreTextView.setText(MessageFormat.format("Score: {0}", String.valueOf(score.getScore())));
        binding.highestScoreTextView.setText(MessageFormat.format("Highest: {0}", String.valueOf(prefs.getHighestScore())));


        questionList = new Repository().getQuestions(questionArrayList -> {
            binding.questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());

            updateCounter(questionArrayList);
        });

        binding.buttonTrue.setOnClickListener(view -> {
            checkAnswer(true);
            updateQuestion();

        });

        binding.buttonFalse.setOnClickListener(view -> {
            checkAnswer(false);
            updateQuestion();

        });

    }

    private void checkAnswer(boolean userChoseCorrect) {
        boolean answer = questionList.get(currentQuestionIndex).isAnswerTrue();
        int snackMessageId = 0;

        if (userChoseCorrect == answer) {
            snackMessageId = R.string.correct_answer;
            fadeAnim();
            addPoints();
        } else {
            snackMessageId = R.string.incorrect;
            shakeAnim();
            deductPoints();
        }

        Snackbar.make(binding.cardView, snackMessageId, Snackbar.LENGTH_SHORT)
                .show();


    }

    private void getNextQuestion() {
        currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
        updateQuestion();
    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.textViewOutOf.setText(String.format(getString(R.string.text_formatted),
                currentQuestionIndex, questionArrayList.size()));
    }

    private void fadeAnim() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
                getNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.questionTextView.setText(question);
        updateCounter((ArrayList<Question>) questionList);
    }

    private void shakeAnim() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_anim);
        binding.cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
                getNextQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void addPoints() {
        scoreCounter += 100;
        score.setScore(scoreCounter);
        binding.scoreTextView.setText(MessageFormat.format("Score: {0}", String.valueOf(score.getScore())));
    }

    private void deductPoints() {
        if (scoreCounter > 0) {
            scoreCounter -= 100;
            score.setScore(scoreCounter);
            binding.scoreTextView.setText(MessageFormat.format("Score: {0}", String.valueOf(score.getScore())));
        } else {
            scoreCounter = 0;
            score.setScore(scoreCounter);
        }
    }

    @Override
    protected void onPause() {
        prefs.saveHighestScore(score.getScore());
        prefs.setState(currentQuestionIndex);
        super.onPause();
    }
}