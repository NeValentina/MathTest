package com.example.mathtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    FloatingActionButton btnTryAgain;
    Integer correctAnswers,v,t,a,b,x,c,i, answerNum, rightAnswer;
    float percent;
    Bundle args;
    private String topicTitle, testText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = getIntent().getExtras();
        setContentView(R.layout.activity_test);

        exampleText=findViewById(R.id.TestTextView);

        answerText=findViewById(R.id.answerText);
        btnNext=findViewById(R.id.btnNextQuestion);
        btnTryAgain=findViewById(R.id.btnTryAgain);

        answerNum=1;
        correctAnswers=0;
        percent = 0;

//        System.out.println(args.getString("PARAM"));
        setTest();
    }

    private void setTest() {

        btnTryAgain.setVisibility(View.INVISIBLE);

        btnNext.setVisibility(View.VISIBLE);
        answerText.setVisibility(View.VISIBLE);

        topicTitle = args.getString("topicTitle");
//        switch (topicTitle):

        switch (topicTitle) {
            case "Простое сложение и вычитание":
                a = (int) ((100 - 1) * Math.random()) + 2;
                b = (int) ((100 - 1) * Math.random()) + 2;
                i = (int) Math.round(Math.random());
                if (i == 0) {
                    testText = (a + " + " + b + " = ?");
                    rightAnswer = a + b;
                } else {
                    testText = (a + " - " + b + "= ?");
                    rightAnswer = a - b;
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
                String testText = (a + "x + " + b + " = " + c);
                exampleText.setText(testText);
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


            if (answerNum < 2) {
                answerNum += 1;
                setTest();
            } else {
                percent= (float)correctAnswers/answerNum*100;
                btnNext.setVisibility(View.GONE);
                answerText.setVisibility(View.GONE);
                btnTryAgain.setVisibility(View.VISIBLE);

                String resultText = ("Ваш результат:\n\nВерно " + correctAnswers + " из " + answerNum + ". ( " + percent +"% )");
                exampleText.setText(resultText);
            }
            answerText.setText("");
//            System.out.println(correctAnswers + "|" + answerNum);
        }
    }

    public void onClickTryAgainBtn(View view){
        answerNum = 1;
        correctAnswers = 0;
        setTest();
    }

}