package com.example.allergicateversion2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class ForgotPassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextInputEditText editTextNewEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = FirebaseAuth.getInstance();
        ImageView backArrow = findViewById(R.id.backArrow);
        editTextNewEmail = findViewById(R.id.inputnewEmail);
        Button btnemail = findViewById(R.id.btnemail);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = editTextNewEmail.getText().toString();
                mAuth.sendPasswordResetEmail(Email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgotPassword.this, "Check your email", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ForgotPassword.this, "Unable to send, failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}


