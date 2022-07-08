package com.ritindia.digigram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    EditText etusername,etpassword;
    Button btnlogin;
    TextView tvregister;
    String username="";
    String password="";
    FirebaseFirestore db;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etusername=findViewById(R.id.etusername);
        etpassword=findViewById(R.id.etpassword);
        btnlogin=findViewById(R.id.btnlogin);
        tvregister=findViewById(R.id.tvnewuser);

        db=FirebaseFirestore.getInstance();

        //---- login
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username="+91"+etusername.getText().toString();
                LocalVariables l=new LocalVariables();
                l.user_id=username;
                password=etpassword.getText().toString();
                db.collection("User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                                        if(username.equalsIgnoreCase(snapshot.getString("Phonenumber"))&&password.equalsIgnoreCase(snapshot.getString("Password"))){
                                            startActivity(new Intent(MainActivity.this,HomePage.class));
                                            Toast.makeText(MainActivity.this, "Successfully logged in..", Toast.LENGTH_SHORT).show();
                                            break;
                                        }else{
                                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
            }
        });

        //----new user registration----
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), Verification.class);
                startActivity(i);
            }
        });

    }
}