package com.example.mathtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class TopicInterface extends AppCompatActivity {
    private DatabaseReference myDB;
//    FirebaseDatabase db;
//    FirebaseAuth auth;

    private String topicTitle;
    private String topicId;
    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        return super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.vocabulary_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int itemId = item.getItemId();
//        if (itemId == R.id.AddWord)
//        {
//            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
//
//            dialog.setTitle("Новое слово");
//            dialog.setMessage("Введите новое слово");
//
//            //Объект для получение шаблона окна
//            LayoutInflater inflater = LayoutInflater.from(this);
//            View addWordWindow =  inflater.inflate(R.layout.add_word_window,null);
//            //Устанавливаем шаблон для всплывающего окна
//            dialog.setView(addWordWindow);
//            final MaterialEditText word = addWordWindow.findViewById(R.id.wordField);
//            final MaterialEditText translation = addWordWindow.findViewById(R.id.translationField);
//            translation.setVisibility(View.GONE);
//
//            dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            });
//            dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    if (TextUtils.isEmpty(word.getText().toString())){
//                        Toast logon = Toast.makeText(getApplicationContext(),"Введите слово",Toast.LENGTH_SHORT);
//                        logon.setGravity(Gravity.TOP,0,250);
//                        logon.show();
//                        return;
//                    }
//
//                    currUserVocabulary.push().
//                            //Заносим в БД новое слово
//                                    setValue(
//                                    new VocabularyElement(
//                                            word.getText().toString(),
//                                            //translation.getText().toString()
//                                            "empty"
//                                    )
//                            );
//                }
//            });
//            dialog.show();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
//                getSupportFragmentManager(),intent.getStringExtra("lessonLevel"),
//                intent.getIntExtra("lessonNumber",0));

        String topicLevel = intent.getStringExtra("topicLevel");
        topicTitle = intent.getStringExtra("topicTitle");
        topicId = intent.getStringExtra("topicId");


        setContentView(R.layout.activity_topic_interface);


        TextView textView = findViewById(R.id.topicName);
        textView.setText(topicTitle);
        TextView theoryText = findViewById(R.id.theoryText);

//        myDB = FirebaseDatabase.getInstance().getReference();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference theoryRef = db.getReference(topicLevel+"/Topic"+topicId);
        Log.i("rulref",String.valueOf(theoryRef));

        theoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                String value = dataSnapshot.getValue(String.class);
//                Log.i("snapshot", value);
                //попробовать сделать доступ через Child("путь")
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



//        auth = FirebaseAuth.getInstance();
//        db= FirebaseDatabase.getInstance();
//        currUserVocabulary = db.getReference("Vocabularies").child(auth.getCurrentUser().getUid());
//        getSupportActionBar().setTitle(intent.getStringExtra("lessonTitle"));

//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);
    }



    public void onBtnPass(View view) {

        Intent intent;

        if (topicTitle.equals("Единицы измерения")){
            intent = new Intent(this, ChoiceTestActivity.class);
        } else {
            intent = new Intent(this, TestActivity.class);
        }

        intent.putExtra("topicTitle", topicTitle);
        intent.putExtra("topicId", topicId);

        startActivity(intent);

    }
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.right_in,R.anim.right_out);
//    }
}
