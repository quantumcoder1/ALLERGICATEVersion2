package com.example.allergicateversion2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    TextView textView;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        TextView btn_register = findViewById(R.id.alreadyHaveAccount);
        editTextUsername = findViewById(R.id.inputPasswords);
        editTextEmail = findViewById(R.id.inputEmail);
        editTextPassword = findViewById(R.id.inputPassword);
        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextConfirmPassword = findViewById(R.id.inputConfirmPassword);
        editTextConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        buttonReg = findViewById(R.id.btnRegister);
        textView = findViewById(R.id.alreadyHaveAccount);
        btn_register.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                UserInfo infoClass = new UserInfo();
//                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                String inputUsername, inputEmail, inputPassword, inputConfirmPassword;
                inputUsername = String.valueOf(editTextUsername.getText());
                inputEmail = String.valueOf(editTextEmail.getText());
                inputPassword = String.valueOf(editTextPassword.getText());
                inputConfirmPassword = String.valueOf(editTextConfirmPassword.getText());
                reference.child(inputUsername).setValue(infoClass);

                if (TextUtils.isEmpty(inputUsername)) {
                    Toast.makeText(RegisterActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(inputEmail)) {
                    Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(inputPassword)) {
                    Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(inputConfirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Enter password again to confirm", Toast.LENGTH_SHORT).show();
                }

                                mAuth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    UserInfo user = new UserInfo(userId, inputUsername, inputEmail, inputPassword);
                                    usersRef.child(userId).setValue(user);
                                    Intent intent = new Intent(getApplicationContext(), AllergyList.class);
                                    intent.putExtra("keyuser", user);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    getApplicationContext().startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Authentication failed. Password should be at least 6 characters.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


            }
        }


