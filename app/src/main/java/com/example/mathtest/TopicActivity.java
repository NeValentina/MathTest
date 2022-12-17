package com.example.mathtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {

    DatabaseReference topicsRef;
    FirebaseDatabase db;

    ListView list_topics;


    int quantity = 0;
    private String topicLevel;
    String topics[] = new String[quantity];
    String title[] = new String[quantity];
    private String onTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topicLevel = getIntent().getStringExtra("Level");


//        db=FirebaseDatabase.getInstance();

        setContentView(R.layout.activity_topic);

        list_topics = findViewById(R.id.listTopics);

        String[] primary_topics = {"Простое сложение и вычитание",
                "Умножение", "Задачи на движение", "Единицы измерения"};

        String[] middle_topics = {"Дроби", "Проценты", "Уравнения"};

        String[] high_topics = {"Тригонометрия", "Логарифмы"};


//        switch (topicLevel) {
//            case "Primary": {
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, primary_topics);
//                list_topics.setAdapter(adapter);
//                break;
//            }
//            case "Middle": {
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, middle_topics);
//                list_topics.setAdapter(adapter);
//                break;
//            }
//            case "High": {
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, high_topics);
//                list_topics.setAdapter(adapter);
//                break;
//            }
//        }
//
//        list_topics.setOnItemClickListener((adapterView, view, i, l) -> {
//            Intent intent = new Intent(TopicActivity.this, TopicInterface.class);
////            intent.putExtra("topicLevel", topicLevel);
////                intent.putExtra("lessonNumber",i+1);
////                Log.i("Stroka title",title[i]);
//            TextView tv = (TextView) view;
//            title = tv.getText().toString();
//            intent.putExtra("topicTitle", title);
//            intent.putExtra("topicId", String.valueOf(l)); // < --- НУЖНО ЛИ ЭТО? (ДОСТАВАТЬ НАЗВАНИЕ ИЗ БД)
//            startActivity(intent);
//
//        });

//    }

        db = FirebaseDatabase.getInstance();

        topicsRef = db.getReference(topicLevel);

        topicsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quantity = (int)dataSnapshot.getChildrenCount();
                Log.i("qu",String.valueOf(quantity));

                topics = new String[(int)quantity];
                title = new String[(int)quantity];
                for(int i=0;i<quantity;i++) {
                    topics[i]="Topic"+(i+1);

//                    Log.i("TrueorFalse",String.valueOf(dataSnapshot.hasChild("Title")));

                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Log.i("key",String.valueOf(snapshot.getKey()));

                        if (String.valueOf(snapshot.getKey()).equals(topics[i]))
                        {Log.i("baby",String.valueOf(snapshot.getKey()));
                            for (DataSnapshot deeperSnapshot : snapshot.getChildren())
                            {
                                Log.i("b",String.valueOf(deeperSnapshot.getKey()));
                                title[i] = deeperSnapshot.getValue().toString();
                                Log.i("Children of Topic"+(i+1), title[i]);
                            }
                        }

//                        Log.i("snapshot",snapshot.getKey());
//                    if(String.valueOf(snapshot.getKey()).equals(topics[i])){
//
//                    }
//                        String text = snapshot.getValue().toString();
//                        theoryText.setText(text.replace("_n","\n"));
                    }

                    if (title[i]==null)
                        title[i]="empty";
//                    topics[i] = title[i];
                }
                ArrayAdapter<String> adapter = new ArrayAdapter <String> (TopicActivity.this,
                        android.R.layout.simple_list_item_1, title);
                list_topics.setAdapter(adapter);
            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
////                String value = dataSnapshot.getValue(String.class);
////                Log.i("snapshot", value);
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Log.i("snapshot",snapshot.getKey());
//                    if(snapshot.getKey().equals("Theory")){
//                        String text = snapshot.getValue().toString();
//                        theoryText.setText(text.replace("_n","\n"));
//                    }
//                }
//            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("snapshot", "failed");
            }
        });

        list_topics.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(TopicActivity.this, TopicInterface.class);
            intent.putExtra("topicLevel", topicLevel);
//                intent.putExtra("lessonNumber",i+1);
//                Log.i("Stroka title",title[i]);
            TextView tv = (TextView) view;
            onTitle = tv.getText().toString();
            intent.putExtra("topicTitle", onTitle);
            intent.putExtra("topicId", String.valueOf(l+1));
            startActivity(intent);

        });


////        выводим с бд уроки по данному уровню
//        topicsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                quantity = (int)dataSnapshot.getChildrenCount();
//                Log.i("qu",String.valueOf(quantity));
//                lessons = new String[(int)quantity];
//                title = new String[(int)quantity];
//                for(int i=0;i<quantity;i++) {
//                    lessons[i]="Lesson "+(i+1);
//                    Log.i("Quantity of lessons ",lessons[i]);
//                    Log.i("TrueorFalse",String.valueOf(dataSnapshot.hasChild("Title")));
//
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
//                    {
//                        Log.i("g",String.valueOf(snapshot.getKey()));
//                        if (String.valueOf(snapshot.getKey()).equals(lessons[i]))
//                        {Log.i("b",String.valueOf(snapshot.getKey()));
//                            for (DataSnapshot deeperSnapshot : snapshot.getChildren())
//                            {
//                                Log.i("b",String.valueOf(deeperSnapshot.getKey()));
//                                title[i] = deeperSnapshot.getValue().toString();
//                                Log.i("Children of Lesson"+(i+1), title[i]);
//                            }
//                        }
//                    }
//
//                    if (title[i]==null)
//                        title[i]="empty";
//                    lessons[i]="Lesson "+(i+1)+"\n "+title[i];
//                }
//                ArrayAdapter<String> adapter = new ArrayAdapter <> (LessonActivity.this,
//                        android.R.layout.simple_list_item_1, lessons);
//                list_lessons.setAdapter(adapter);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

}
}