package com.example.mathtest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChoiceTestActivity extends AppCompatActivity {

    TextView exampleText, textAns1, textAns2, textAns3, textAns4;
//    Button btnNext;
    FloatingActionButton btnTryAgain;
    Integer correctAnswers,v,t,a,b,x,c,i, answerNum;


    float percent;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = getIntent().getExtras();
        setContentView(R.layout.activity_choice_test);

        exampleText=findViewById(R.id.TestTextView);

        textAns1=findViewById(R.id.textAns1);
        textAns2=findViewById(R.id.textAns2);
        textAns3=findViewById(R.id.textAns3);
        textAns4=findViewById(R.id.textAns4);

        btnTryAgain=findViewById(R.id.btnTryAgain);

        answerNum=1;
        correctAnswers=0;
        percent = 0;

//        System.out.println(args.getString("PARAM"));
        setTest();
    }

    private void setTest() {

        btnTryAgain.setVisibility(View.INVISIBLE);

        textAns1.setVisibility(View.VISIBLE);
        textAns2.setVisibility(View.VISIBLE);
        textAns3.setVisibility(View.VISIBLE);
        textAns4.setVisibility(View.VISIBLE);

//        String[] objects = {"длины", "объема", "веса", "времени"};

        String[] measureCorrert = { "мм", "см", "м", "км"};
//                               { "л", "см^3", "м^3" },
//                               { "г", "кг", "т", "ц"},
//                               { "сек", "мин", "час"}};

        String[] measureWrong = {"г", "кг", "т", "ц"};

//        i = (int) (objects.length * Math.random());
//        String testText = ("Выберите меру измерения " + objects[i]);
        if (answerNum == 1){
        String testText = ("Выберите меру измерения длины");
        exampleText.setText(testText);

        textAns1.setText("мин");
        textAns2.setText("км");
        textAns3.setText("кг");
        textAns4.setText("м^3");}

        else {
            String testText = ("Выберите меру измерения времени");
            exampleText.setText(testText);

            textAns1.setText("км^3");
            textAns2.setText("т");
            textAns3.setText("мин");
            textAns4.setText("кг");

        }

    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.textAns1:
                Toast.makeText(getApplicationContext(), "Неверно", Toast.LENGTH_SHORT).show();
                break;

            case R.id.textAns2:
                if (answerNum == 1){
                Toast.makeText(getApplicationContext(), "Верно", Toast.LENGTH_SHORT).show();
                correctAnswers +=1;} else {
                    Toast.makeText(getApplicationContext(), "Неверно", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.textAns3:
                if (answerNum == 1){
                Toast.makeText(getApplicationContext(), "Неверно", Toast.LENGTH_SHORT).show();}
                else {
                    Toast.makeText(getApplicationContext(), "Верно", Toast.LENGTH_SHORT).show();
                    correctAnswers +=1;

                }
                break;

            case R.id.textAns4:
                Toast.makeText(getApplicationContext(), "Неверно", Toast.LENGTH_SHORT).show();
                break;
        }

        if (answerNum < 2) {
            answerNum += 1;
            setTest();
        } else {
            percent= (float)correctAnswers/answerNum*100;
        btnTryAgain.setVisibility(View.VISIBLE);
        textAns1.setVisibility(View.INVISIBLE);
        textAns2.setVisibility(View.INVISIBLE);
        textAns3.setVisibility(View.INVISIBLE);
        textAns4.setVisibility(View.INVISIBLE);
            String resultText = ("Верно " + correctAnswers + " из " + answerNum + ". ( " + percent +"% )");
            exampleText.setText(resultText);
        }

//        System.out.println(correctAnswers + "|" + answerNum);


    }


    public void onClickTryAgainBtn(View view){
        answerNum = 1;
        correctAnswers = 0;
        setTest();
    }
}