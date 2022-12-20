package com.example.mathtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TopicInterface extends AppCompatActivity {

    private String topicTitle, topicId, topicLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        topicLevel = intent.getStringExtra("topicLevel");
        topicTitle = intent.getStringExtra("topicTitle");
        topicId = intent.getStringExtra("topicId");

        setContentView(R.layout.activity_topic_interface);

        TextView textView = findViewById(R.id.topicName);
        textView.setText(topicTitle);
        TextView theoryText = findViewById(R.id.theoryText);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference theoryRef = db.getReference(topicLevel+"/Topic"+topicId);
//        Log.i("rulref",String.valueOf(theoryRef));

        theoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.i("snapshot",snapshot.getKey());
                    if(String.valueOf(snapshot.getKey()).equals("Theory")){
                        String text = snapshot.getValue().toString();
                        theoryText.setText(text.replace("_n","\n"));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("snapshot", "failed");
            }
        });

    }



    public void onClick(View view) {

        Intent intent;

        int id = view.getId();

        if(id==R.id.btn_next) {
            if (topicTitle.equals("Простое сложение и вычитание")
                    || topicTitle.equals("Умножение") || topicTitle.equals("Уравнения")
                    || topicTitle.equals("Логарифмы")
            ){
                intent = new Intent(this, TestActivity.class);
            } else {
                intent = new Intent(this, ChoiceTestActivity.class);
            }

            intent.putExtra("Level", topicLevel);
            intent.putExtra("topicTitle", topicTitle);
            intent.putExtra("topicId", topicId);

            startActivity(intent);
        }

        if(id==R.id.btn_list) {
            intent = new Intent(this, TopicActivity.class);
            intent.putExtra("Level", topicLevel);
            startActivity(intent);
        }

    }

}
