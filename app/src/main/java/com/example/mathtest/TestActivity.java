package com.example.mathtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TestActivity extends AppCompatActivity {

    TextView exampleText;
    EditText answerText;
    Button btnNext;
    FloatingActionButton btnTryAgain, btnList, btnHome;
    SharedPreferences sharedPreferences;
    Integer correctAnswers,a,b,x,c,i, answerNum, rightAnswer;
    float percent, bestResult;
    Bundle args;
    private String topicTitle, topicLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = getIntent().getExtras();
        setContentView(R.layout.activity_test);

        exampleText=findViewById(R.id.TestTextView);

        answerText=findViewById(R.id.answerText);
        btnNext=findViewById(R.id.btnNextQuestion);
        btnTryAgain=findViewById(R.id.btnTryAgain);
        btnList=findViewById(R.id.btn_list);
        btnHome=findViewById(R.id.btn_home);


        answerNum=1;
        correctAnswers=0;
        percent = 0;

        setTest();

        sharedPreferences = getApplicationContext().
                getSharedPreferences("com.example.mathtest", Context.MODE_PRIVATE);
    }

    private void setTest() {

        String testText;
        btnTryAgain.setVisibility(View.INVISIBLE);
        btnList.setVisibility(View.INVISIBLE);
        btnHome.setVisibility(View.INVISIBLE);

        btnNext.setVisibility(View.VISIBLE);
        answerText.setVisibility(View.VISIBLE);

        topicTitle = args.getString("topicTitle");

        switch (topicTitle) {
            case "Простое сложение и вычитание":
                a = (int) ((100 - 1) * Math.random()) + 2;
                b = (int) ((100 - 1) * Math.random()) + 2;
                i = (int) Math.round(Math.random());
                if (i == 0) {
                    testText = (a + " + " + b + " = ?");
                    rightAnswer = a + b;
                } else {
                    if (a>b){
                        testText = (a + " - " + b + "= ?");
                        rightAnswer = a - b;
                    } else {
                        testText = (b + " - " + a + "= ?");
                        rightAnswer = b - a;
                    }
                }
                exampleText.setText(testText);
                break;

            case "Умножение":
                a = (int) ((9 - 1) * Math.random()) + 2;
                b = (int) ((9 - 1) * Math.random()) + 2;
                i = (int) Math.round(Math.random());
                if (i == 0) {
                    testText = (a + " * " + b + " = ?");
                    rightAnswer = a * b;
                } else {
                    testText = (a * b + " / " + b + "= ?");
                    rightAnswer = a;
                }
                exampleText.setText(testText);
                break;

            case "Уравнения":
                a = (int) ((10 - 1) * Math.random()) + 1;
                b = (int) ((100 - 1) * Math.random());
                x = rightAnswer = (int) ((10 - 1) * Math.random()) + 1;
                c = a * x + b;
                testText = (a + "x + " + b + " = " + c+"\nx = ?");
                exampleText.setText(testText);
                break;

            case "Логарифмы":
                i =  (int) (Math.round(Math.random()));
                double a = (Math.round((10-1) * Math.random())) +1;
                double b = (Math.round((3-1) * Math.random())) + 1;
                double c = Math.pow(a, b);
                if (i == 0){
                    rightAnswer = (int) b;
                    testText = ("log_"+(int)a+"{"+(int)c+"} = ?");
                    exampleText.setText(testText);
                } else {
                    rightAnswer = (int) a;
                    testText = ("log_?{"+(int)c+"} = "+(int)b);
                    exampleText.setText(testText);
                }
                break;
        }

    }

    public void onClickNextButton(View view) {

        if (answerText.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Введите ответ", Toast.LENGTH_LONG).show();
        } else {
            if (Integer.parseInt(answerText.getText().toString()) == rightAnswer) {
                correctAnswers += 1;
                Toast.makeText(getApplicationContext(), "Правильный ответ", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getApplicationContext(), "Неверный ответ", Toast.LENGTH_SHORT).show();


            if (answerNum < 5) {
                answerNum += 1;
                setTest();
            } else {
                percent= (float)correctAnswers/answerNum*100;
                btnNext.setVisibility(View.GONE);
                answerText.setVisibility(View.GONE);
                btnTryAgain.setVisibility(View.VISIBLE);
                btnList.setVisibility(View.VISIBLE);
                btnHome.setVisibility(View.VISIBLE);


                if (sharedPreferences.contains("Best "+topicTitle)){
                    bestResult = sharedPreferences.getFloat("Best "+topicTitle, 0.0f);
                    if (bestResult < percent){
                        bestResult = percent;
                        sharedPreferences.edit().putFloat("Best " + topicTitle, bestResult).apply();
                    }
                } else {
                    bestResult = percent;
                    sharedPreferences.edit().putFloat("Best " + topicTitle, percent).apply();
                }

                String stringPercent = String.format("%.1f", percent);
                String resultText = ("Ваш результат:\n\nВерно " + correctAnswers + " из " + answerNum + ". ( " + stringPercent +"% )\n\n\n" +
                        "Лучший результат: " + bestResult +"%");
                exampleText.setText(resultText);
            }
            answerText.setText("");
        }
    }

    public void onClick(View view){

        Intent intent;
        int id = view.getId();
        topicLevel = args.getString("Level");
        Log.i("TOPIC LEVEL",topicLevel);

        //ПРОЙТИ ЗАНОВО
        if(id==R.id.btnTryAgain) {
            answerNum = 1;
            correctAnswers = 0;
            setTest();
        }


        //СПИСОК
        if(id==R.id.btn_list) {
            intent = new Intent(this, TopicActivity.class);
            intent.putExtra("Level", topicLevel);
            startActivity(intent);
        }

        //ДОМОЙ
        if(id==R.id.btn_home) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

}