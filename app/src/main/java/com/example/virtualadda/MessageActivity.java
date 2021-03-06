package com.example.virtualadda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.virtualadda.model.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private CircleImageView profile_image;
    private TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        toolbar=findViewById(R.id.toolbar);
        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);
         intent=getIntent();

         String userid=intent.getStringExtra("userid");

         firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
         reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

         reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 User user=snapshot.getValue(User.class);
                 username.setText(user.getUsername());
                 if (user.getImageURL().equals("default")){
                     profile_image.setImageResource(R.mipmap.ic_launcher);
                 }else {
                     Glide.with(MessageActivity.this).load(user.getImageURL()).into(profile_image);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

    }
}