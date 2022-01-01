package com.laioffer.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.laioffer.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "lifecycle";

    //Generate after we enable "view binding" in build.gradle
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //use "inflate" to transfer XML into Java Class
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_main);


//        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
//        EditText nameEditText = findViewById(R.id.nameEditText);
//        EditText emailEditText = findViewById(R.id.emailEditText);
//        Button submitButton = findViewById(R.id.submitButton);

        //anonymous class --> View.OnClickListener()
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.nameEditText.getText().toString();
                String email = binding.emailEditText.getText().toString();
                binding.welcomeTextView.setText("Welcome " + name + ", your email is: " + email);
            }
        });
    }


}
