package com.example.socialmedia.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialmedia.R;
import com.example.socialmedia.model.QuestionMember;
import com.example.socialmedia.view.Viewholder_Question;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Related extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related);

        recyclerView = findViewById(R.id.rv_related);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid = user.getUid();

        reference = database.getReference("favoritelist").child(currentUserid);

        FirebaseRecyclerOptions<QuestionMember> options = new FirebaseRecyclerOptions.Builder<QuestionMember>()
                .setQuery(reference,QuestionMember.class).build();
        FirebaseRecyclerAdapter<QuestionMember, Viewholder_Question> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<QuestionMember, Viewholder_Question>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Viewholder_Question holder, int position, @NonNull QuestionMember model) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String currentUserid = user.getUid();

                final String postkey = getRef(position).getKey();

                holder.setitemRelated(getApplication(),model.getName(),model.getUrl(),model.getUserid(),model.getKey(),
                        model.getQuestion(),model.getPrivacy(),model.getTime());

//                final String que = getItem(position).getQuestion();
//                final String name = getItem(position).getName();
//                final String url = getItem(position).getUrl();
//                final String time = getItem(position).getTime();
//                final String privacy = getItem(position).getPrivacy();
//                final String userid = getItem(position).getUserid();


            }

            @NonNull
            @Override
            public Viewholder_Question onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_item,parent, false);
                return new Viewholder_Question(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}