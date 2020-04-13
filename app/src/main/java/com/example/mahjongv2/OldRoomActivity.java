package com.example.mahjongv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OldRoomActivity extends AppCompatActivity {

    private Button btn_backToRooms;
    private TextView myRoomID, player1,player2,player3,player4;

    public String p2="",p3="",p4="" ,myP="";

    SessionManager sessionManager;
    String name;

    //Firebase Database
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_room);

        btn_backToRooms = findViewById(R.id.btn_backToRooms);
        myRoomID = findViewById(R.id.myRoomID);
        player1 = findViewById(R.id.tv_player1);
        player2 = findViewById(R.id.tv_player2);
        player3 = findViewById(R.id.tv_player3);
        player4 = findViewById(R.id.tv_player4);

        Log.v("leo",MainApp.RoomId);





        //抓自己的名字
        sessionManager = new SessionManager(this);
        HashMap<String,String> user = sessionManager.getUserDetail();
        name = user.get(sessionManager.NAME);
        Log.v("leo","myNameIs: "+name);

        //
        myRoomID.setText(MainApp.RoomId);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(MainApp.RoomId);
        Log.v("leo","++"+myRef.getKey());


        myRef.addListenerForSingleValueEvent(singleListener);
        myRef.addValueEventListener(listener);








    }

    //單次監聽取得人員名單 命且將位置補上
    ValueEventListener singleListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Member obj = dataSnapshot.getValue(Member.class);
            //get information
            p2=obj.getNames().get(1);
            p3=obj.getNames().get(2);
            p4=obj.getNames().get(3);



           if(p2.equals("")){
               myP="1";
                myRef.child("names").child(myP).setValue(name);

            }else if(p3.equals("")){
               myP="2";
                myRef.child("names").child(myP).setValue(name);

            }else if(p4.equals("")){
               myP="3";
                myRef.child("names").child(myP).setValue(name);

            }else {
               //人數已滿
               backToRooms(null);
           }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           Member obj = dataSnapshot.getValue(Member.class);
            player1.setText(obj.getNames().get(0));
            player2.setText(obj.getNames().get(1));
            player3.setText(obj.getNames().get(2));
            player4.setText(obj.getNames().get(3));



        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    //返回房間列表監聽事件
    public void backToRooms(View view) {
        //取消拿到的ID
        MainApp.RoomId="";
        myRef.removeEventListener(listener);
        myRef.child("names").child(myP).removeValue();



        Intent intent = new Intent(OldRoomActivity.this,RoomsActivity.class);
        startActivity(intent);
        OldRoomActivity.this.finish();
    }
}