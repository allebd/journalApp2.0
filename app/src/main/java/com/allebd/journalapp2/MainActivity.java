package com.allebd.journalapp2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loginpack.LoginActivity;
import com.welcome.Welcome;

public class MainActivity extends AppCompatActivity {

    //VIEWS AND WIDGET FIELDS
    Button createUser, moveToLogin;
    EditText userEmailEdit, userPassWordEdit;

    //FIREBASE AUTHENTICATION FIELDS
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ASSIGN ID
        createUser = (Button) findViewById(R.id.createUserBtn);
        moveToLogin = (Button) findViewById(R.id.moveToLogin);
        userEmailEdit = (EditText) findViewById(R.id.emailEditTextCreate);
        userPassWordEdit = (EditText) findViewById(R.id.passEditTextCreate);

        //ASSIGN INSTANCES
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){

                }else{
                    startActivity(new Intent(MainActivity.this, Welcome.class));
                }
            }
        };

        //ON CLICK LISTENERS

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmailString, userPassString;

                userEmailString = userEmailEdit.getText().toString().trim();
                userPassString = userPassWordEdit.getText().toString().trim();

                if(!TextUtils.isEmpty(userEmailString) && !TextUtils.isEmpty(userPassString))
                {
                    mAuth.createUserWithEmailAndPassword(userEmailString,userPassString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "User Account Created", Toast.LENGTH_LONG);
                                startActivity(new Intent(MainActivity.this, Welcome.class));
                            }else{
                                Toast.makeText(MainActivity.this, "Failed to create User Account", Toast.LENGTH_LONG);
                            }
                        }
                    });
                }

            }
        });

        moveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mAuthListener);
    }
}
