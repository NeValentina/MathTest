package com.example.mathtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChoiceTestActivity extends AppCompatActivity {

    TextView exampleText, textViewTimer;
    FloatingActionButton btnTryAgain, btnList, btnHome;
    Integer correctAnswers, answerNum;
    SharedPreferences sharedPreferences;
    private String topicLevel, topicTitle, topicId;

    DatabaseReference questionRef;
    FirebaseDatabase db;
    ArrayList<Questions> questions = new ArrayList<>();
    Button btn1, btn2, btn3, btn4;
    int quantity = 0, seconds;
    float percent, bestResult;;
    Bundle args;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = getIntent().getExtras();
        topicLevel = args.getString("Level");
        topicTitle = args.getString("topicTitle");
        topicId = args.getString("topicId");

        switch (topicTitle) {
            case ("Задачи на движение"):
                seconds = 30;
                break;

            case("Единицы измерения"):
                seconds = 5;
                break;

            case("Десятичные дроби"):
                seconds = 10;
                break;

            case("Обыкновенные дроби"):
                seconds = 10;
                break;

            case("Проценты"):
                seconds = 15;
                break;

            case("Тригонометрия"):
                seconds = 15;
                break;
        }


        setContentView(R.layout.activity_choice_test);

        exampleText=findViewById(R.id.TestTextView);
        textViewTimer=findViewById(R.id.textViewTimer);

        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);


        btnTryAgain=findViewById(R.id.btnTryAgain);
        btnList=findViewById(R.id.btn_list);
        btnHome=findViewById(R.id.btn_home);

        sharedPreferences = getApplicationContext().
                getSharedPreferences("com.example.mathtest", Context.MODE_PRIVATE);

        answerNum=0;
        correctAnswers=0;
        percent = 0;


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAnsw(view);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnsw(view);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnsw(view);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnsw(view);
            }
        });


        db = FirebaseDatabase.getInstance();
//        Log.i("Topic Level",String.valueOf(topicLevel));
//        Log.i("Topic Id",String.valueOf(topicId));

        questionRef = db.getReference(topicLevel).child("Topic"+topicId).child("Test");

        questionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                quantity = (int)snapshot.getChildrenCount();
                Log.i("Quantity of quest",String.valueOf(quantity));

                for (int i=0; i<quantity;i++) {
                    Questions question = snapshot.child("Question " + (i+1)).getValue(Questions.class);
                    questions.add(question);
                    Log.i("Question "+(i+1),String.valueOf(questions.size()));
                }

                setTest();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setTest() {

        btnTryAgain.setVisibility(View.INVISIBLE);
        btnList.setVisibility(View.INVISIBLE);
        btnHome.setVisibility(View.INVISIBLE);


        countDownTimer = new CountDownTimer(seconds*1000, 1000) {
            @Override
            public void onTick(long millis) {
                textViewTimer.setText("Осталось:\n" + (int)((millis+1000)/1000)+" c");

            }

            @Override
            public void onFinish() {
                answerNum++;
                if (answerNum<5){
                    setTest();}
                else {

                    percent= (float)correctAnswers/answerNum*100;
                    btnTryAgain.setVisibility(View.VISIBLE);
                    btnList.setVisibility(View.VISIBLE);
                    btnHome.setVisibility(View.VISIBLE);
                    btn1.setVisibility(View.INVISIBLE);
                    btn2.setVisibility(View.INVISIBLE);
                    btn3.setVisibility(View.INVISIBLE);
                    btn4.setVisibility(View.INVISIBLE);
                    textViewTimer.setVisibility(View.INVISIBLE);

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

                    String resultText = ("Ваш результат:\n\nВерно " + correctAnswers + " из " + answerNum + ". ( " + percent +"% )\n\n\n" +
                            "Лучший результат: " + bestResult +"%");
                    exampleText.setText(resultText);
                }
            }
        }.start();

        exampleText.setText(questions.get(answerNum).getQuestionText().replace("_n","\n"));
        btn1.setText(questions.get(answerNum).getV1());
        btn2.setText(questions.get(answerNum).getV2());
        btn3.setText(questions.get(answerNum).getV3());
        btn4.setText(questions.get(answerNum).getV4());

    }

    public void checkAnsw(View view) {

        countDownTimer.cancel();
        int id = view.getId();
        Button btn = view.findViewById(id);

        String right = questions.get(answerNum).getRight();

        Log.i("Right ",String.valueOf(right));

        if (questions.get(answerNum).getRight().equals(btn.getText())){
            answerNum++;
            correctAnswers++;
            Toast.makeText(getApplicationContext(), "Верно", Toast.LENGTH_SHORT).show();
        } else {
            answerNum++;
            Toast.makeText(getApplicationContext(), "Неверно", Toast.LENGTH_SHORT).show();
        }


        if (answerNum<5){
            setTest();
        } else {
            percent= (float)correctAnswers/answerNum*100;
            btnTryAgain.setVisibility(View.VISIBLE);
            btnList.setVisibility(View.VISIBLE);
            btnHome.setVisibility(View.VISIBLE);
            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.INVISIBLE);
            btn3.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.INVISIBLE);
            textViewTimer.setVisibility(View.INVISIBLE);

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

            String resultText = ("Ваш результат:\n\nВерно " + correctAnswers + " из " + answerNum + ". ( " + percent +"% )\n\n\n" +
                    "Лучший результат: " + bestResult +"%");
            exampleText.setText(resultText);
        }

    }


    public void onClickMenu(View view){

        Intent intent;
        int id = view.getId();

        //ПРОЙТИ ЗАНОВО
        if(id==R.id.btnTryAgain) {
            answerNum = 0;
            correctAnswers = 0;

            intent = new Intent(this, ChoiceTestActivity.class);
            intent.putExtra("Level", topicLevel);
            intent.putExtra("topicTitle", topicTitle);
            intent.putExtra("topicId", topicId);
            startActivity(intent);
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