package com.example.socialmedia.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialmedia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ReplyActivity extends AppCompatActivity {
    String uid,question,post_key,key;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference,reference2;

    TextView nametv,questiontv,tvreply;
    RecyclerView recyclerView;
    ImageView imageViewQue, imageViewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        nametv = findViewById(R.id.name_reply_tv);
        questiontv = findViewById(R.id.que_reply_tv);
        imageViewQue = findViewById(R.id.iv_que_user);
        imageViewUser = findViewById(R.id.iv_reply_user);
        tvreply = findViewById(R.id.answer_tv);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            uid = extra.getString("uid");
            post_key = extra.getString("postkey");
            question = extra.getString("q");
          //  key = extra.getString("key");
        }
        else{
            Toast.makeText(this, "opps", Toast.LENGTH_SHORT).show();
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();

        reference = db.collection("user").document(uid);
        reference2 = db.collection("user").document(currentuid);

        tvreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReplyActivity.this, AnswerActivity.class);
                intent.putExtra("u",uid);
                //intent.putExtra("q",question);
                intent.putExtra("p",post_key);
                //   intent.putExtra("key",privacy);
                startActivity(intent);


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){
                    String url = task.getResult().getString("url");
                    String name = task.getResult().getString("name");
                    Picasso.get().load(url).into(imageViewQue);
                    nametv.setText(name);
                    questiontv.setText(question);

                }else {
                  Toast.makeText(ReplyActivity.this,"error",Toast.LENGTH_SHORT).show();
                }
            }
        });

        reference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){
                    String url = task.getResult().getString("url");
                    Picasso.get().load(url).into(imageViewUser);
                   // nametv.setText(name);

                }else {
                    Toast.makeText(ReplyActivity.this,"error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}