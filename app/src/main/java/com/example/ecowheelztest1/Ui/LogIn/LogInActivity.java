package com.example.ecowheelztest1.Ui.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecowheelztest1.R;
import com.example.ecowheelztest1.Ui.Profile.ProfileActivity;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnBackL;
    private EditText etEmailLogIn, etPhoneLogIn;
    private String emailLogIn;
    private int phoneLogIn;
    private Button btnLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btnBackL = findViewById(R.id.btnBackL);
        btnBackL.setOnClickListener(this);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);

        etEmailLogIn = findViewById(R.id.emailLogIn);
        etPhoneLogIn = findViewById(R.id.phoneLogIn);

        emailLogIn = etEmailLogIn.getText().toString().trim();
        phoneLogIn = Integer.parseInt(etPhoneLogIn.getText().toString().trim());


    }

    @Override
    public void onClick(View v) {
        if (btnBackL == v)
        {
            Intent intent = new Intent(LogInActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }
}