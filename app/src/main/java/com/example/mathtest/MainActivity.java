package com.example.mathtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_primary, btn_middle, btn_high;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_primary = findViewById(R.id.btn_primary);
        btn_middle = findViewById(R.id.btn_middle);
        btn_high = findViewById(R.id.btn_high);



    }

    public void onClickBtn(View view) {
        Intent intent = new Intent(this,TopicActivity.class);
        int id = view.getId();

        if(id==R.id.btn_primary) {
            intent.putExtra("Level", "Primary");
            startActivity(intent);
        }
        else if (id==R.id.btn_middle) {
            intent.putExtra("Level", "Middle");
            startActivity(intent);
        }
        else if (id==R.id.btn_high) {
            intent.putExtra("Level", "High");
            startActivity(intent);
        }
    }


    }

