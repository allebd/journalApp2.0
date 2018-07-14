package com.allebd.journalapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.welcome.Welcome;

public class Profile extends AppCompatActivity {

    //DECLARE THE FIELDS
    EditText userName, userContact;
    Button saveBtn;

    //FIRE BASE DATA REFERENCE
    DatabaseReference mDataRef;

    //STRING KEY
    String keyUser;

    String userNameString, userContactString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //GET USER KEY FROM INTENT
        keyUser = getIntent().getStringExtra("USER_KEY");

        //FIRE BASE DATABASE REFERENCE
        mDataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(keyUser);

        //ASSIGN ID'S
        userName = (EditText) findViewById(R.id.userNameEditText);
        userContact = (EditText) findViewById(R.id.userPhoneEditText);
        saveBtn = (Button) findViewById(R.id.userProfileBtn);

        //SAVE BTN LOGIN
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameString = userName.getText().toString().trim();
                userContactString = userContact.getText().toString().trim();

                Log.d("adf", userNameString + ":" + userContactString);

                if(!TextUtils.isEmpty(userNameString) && !TextUtils.isEmpty(userContactString))
                {
                    mDataRef.child("userName").setValue(userNameString);
                    mDataRef.child("userContact").setValue(userContactString);
                    mDataRef.child("isVerified").setValue("verified");

                    Toast.makeText(Profile.this, "User profile added", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(Profile.this, Welcome.class));
                }
            }
        });
    }
}
