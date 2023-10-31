package com.example.allergicateversion2;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class AllergyList extends AppCompatActivity {

    Button btnNext;
    CheckBox peanuts;
    CheckBox eggs;
    CheckBox dairy;
    CheckBox treeNuts;
    CheckBox shellfish;
    CheckBox sesame;
    CheckBox lactose;
    CheckBox wheat;
    TextInputEditText otherAllergies;
    String allergy;

    private ArrayList<String> allergyList = new ArrayList<>();

//    public AllergyList(String allergy){
//        this.allergy = allergy;
//    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy_list);
        btnNext = findViewById(R.id.btnNext);
        peanuts = findViewById(R.id.peanuts);
        eggs = findViewById(R.id.eggs);
        dairy = findViewById(R.id.dairy);
        treeNuts = findViewById(R.id.treeNuts);
        shellfish = findViewById(R.id.shellfish);
        sesame = findViewById(R.id.sesame);
        lactose = findViewById(R.id.lactose);
        wheat = findViewById(R.id.wheat);
        otherAllergies = findViewById(R.id.otherAllergies);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otherAllergies != null) {
                    String [] otherAllergy = otherAllergies.getText().toString().split("\\s*,\\s*");
                    for(int i = 0; i < otherAllergy.length; i++){
                        allergyList.add(otherAllergy[i]);
                    }
                }
                UserInfo user = getIntent().getParcelableExtra("keyuser");
//                user.addAllergyList(allergyList);
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                usersRef.child(userId).child("allergyList").setValue(allergyList);
                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                intent.putExtra("keyuser", user);
                startActivity(intent);
                finish();
            }
        });


    }

    private void addAllergy(String allergy){
        allergyList.add(allergy);
        return;
    }

    private void removeAllergy(String allergy){
        allergyList.remove(allergy);
        return;
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.peanuts:
                if (checked){
                    addAllergy("Peanuts");
                } else {
                    removeAllergy("Peanuts");
                }
                break;
            case R.id.eggs:
                if (checked){
                    addAllergy("Eggs");
                } else {
                    removeAllergy("Eggs");
                }
                break;
            case R.id.dairy:
                if (checked){
                    addAllergy("Dairy");
                } else {
                    removeAllergy("Dairy");
                }
                break;
            case R.id.treeNuts:
                if (checked){
                    addAllergy("Tree Nuts");
                } else {
                    removeAllergy("Tree Nuts");
                }
                break;
            case R.id.shellfish:
                if (checked){
                    addAllergy("Shellfish");
                } else {
                    removeAllergy("Shellfish");
                }
                break;
            case R.id.sesame:
                if (checked){
                    addAllergy("Sesame");
                } else {
                    removeAllergy("Sesame");
                }
                break;
            case R.id.lactose:
                if (checked){
                    addAllergy("Lactose");
                } else {
                    removeAllergy("Lactose");
                }
                break;
            case R.id.wheat:
                if (checked){
                    addAllergy("Wheat");
                } else {
                    removeAllergy("Wheat");
                }
        }
    }

}