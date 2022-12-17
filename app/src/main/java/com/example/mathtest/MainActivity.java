package com.example.mathtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_primary, btn_middle, btn_high;

//    FirebaseAuth auth;
//    DatabaseReference users;
//    DatabaseReference currentUserRef;
//    SharedPreferences sharedPrefs;

    //процент прохождения общего теста
//    int progressInDB;

    //константы для определения уровня владения языка
//    private static final int REQUIRED_PROGRESS_FOR_INTERMEDIATE=45;
//    private static final int REQUIRED_PROGRESS_FOR_ADVANCED=80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().hide();

        btn_primary = findViewById(R.id.btn_primary);
        btn_middle = findViewById(R.id.btn_middle);
        btn_high = findViewById(R.id.btn_high);

//        auth=FirebaseAuth.getInstance();
//        users= FirebaseDatabase.getInstance().getReference("Users");
//        sharedPrefs = getSharedPreferences("com.example.EnglishApp",Context.MODE_PRIVATE);

//        btn_primary.setOnClickListener(onClickListener);
//        btn_middle.setOnClickListener(onClickListener);
//        btn_high.setOnClickListener(onClickListener);

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



//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        final String email = sharedPrefs.getString("email","empty");
//        Log.i("email from mem",email);
//        String password = sharedPrefs.getString("password","0");
//        currentUserRef=users.child(auth.getCurrentUser().getUid());
//        //получение уровня владения английским из бд и синхронизация с sharedprefs
//        Log.i("current user ref",currentUserRef.toString());
//        currentUserRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String userKey=dataSnapshot.getKey();
//                Log.i("userKey",userKey);
//                progressInDB=Integer.parseInt(dataSnapshot.child("progress").getValue().toString());
//                Log.i("progressInDB",String.valueOf(progressInDB));
//                int progress=0;
//                //Если прогресс есть в памяти телефона
//                if(sharedPrefs.contains(email+" Level percentages")){
//                    Log.i("Progress from storage",String.valueOf(sharedPrefs.getInt(" Level percentages",-1)));
//                    int progressInStorage = sharedPrefs.getInt(email+" Level percentages", -1);
//                    //Если прогресс в памяти телефона юольш, чем в бд
//                    if (progressInStorage>progressInDB){
//                        progressInDB=progressInStorage;
//                        progress=progressInDB;
//                        currentUserRef.child("progress").setValue(String.valueOf(progressInDB));
//                    }
//                    //Если прогресс в памяти телефона юольш, чем в бд
//                    else {
//                        progress=progressInDB;
//                    }
//
//                }//Если прогресса нет в памяти телефона
//                else {
//                    progress=progressInDB;
//                    sharedPrefs.edit().putInt(email+" Level percentages",progressInDB).apply();
//                }
//                //Вывод уровень владения языка в соответствии с прогрессом
//                if (progress<=REQUIRED_PROGRESS_FOR_INTERMEDIATE)
//                {
//                    tvProgress.setText(String.format("Ваш уровень - Beginner (%d%%)",progress));
//                }
//                else{
//                    if (progress<=REQUIRED_PROGRESS_FOR_ADVANCED)
//                    {
//                        tvProgress.setText(String.format("Ваш уровень - Intermediate (%d%%)",progress));
//                    }
//                    else{
//                        if (progress>REQUIRED_PROGRESS_FOR_ADVANCED)
//                        {
//                            tvProgress.setText(String.format("Ваш уровень - Advanced (%d%%)",progress));
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
    }

