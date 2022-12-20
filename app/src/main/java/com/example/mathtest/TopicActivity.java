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
import android.widget.ImageView;
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


                    }

                    if (title[i]==null)
                        title[i]="empty";
//                    topics[i] = title[i];
                }
                ArrayAdapter<String> adapter = new ArrayAdapter <String> (TopicActivity.this,
                        android.R.layout.simple_list_item_1, title);
                list_topics.setAdapter(adapter);
            }



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


    }


    public void onClick(View view) {

        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}