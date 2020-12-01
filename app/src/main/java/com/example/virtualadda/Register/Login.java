package com.example.virtualadda.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.virtualadda.MainActivity;
import com.example.virtualadda.R;
import com.example.virtualadda.Start;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Login extends AppCompatActivity {
    private EditText emailET,passwordET;
    private MaterialButton registerBtn;
   MaterialToolbar toolbar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LogIn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email=emailET.getText().toString();
                String txt_pass=passwordET.getText().toString();

                if ( TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass)){
                    Toast.makeText(Login.this, "All field are empty", Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(txt_email,txt_pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent=new Intent(Login.this, Start.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(Login.this, "Authentication  Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void init() {
        toolbar=findViewById(R.id.toolbarRegister);
        emailET=findViewById(R.id.emailET);
        passwordET=findViewById(R.id.passwordET);
        registerBtn=findViewById(R.id.registerBtn);
        auth=FirebaseAuth.getInstance();

    }
}