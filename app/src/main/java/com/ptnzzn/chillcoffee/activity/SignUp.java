package com.ptnzzn.chillcoffee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.Snackbar.SnackbarLayout;
import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.dao.UserDAO;

public class SignUp extends AppCompatActivity {
    ImageView img_BackFromSignUp;
    EditText edt_SignUpUsername, edt_SignUpPassword, edt_SignUpConfirmPassword;
    Button btn_SignUp;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        layout = findViewById(R.id.layoutSignUp);
        img_BackFromSignUp = findViewById(R.id.img_BackFromSignUp);
        edt_SignUpUsername = findViewById(R.id.edt_SignUpUsername);
        edt_SignUpPassword = findViewById(R.id.edt_SignUpPassword);
        edt_SignUpConfirmPassword = findViewById(R.id.edt_SignUpConfirmPassword);
        btn_SignUp = findViewById(R.id.btn_SignUp);
        UserDAO dao = new UserDAO(this);

        img_BackFromSignUp.setOnClickListener(v -> finish());

        btn_SignUp.setOnClickListener(v -> {
            String username = edt_SignUpUsername.getText().toString();
            String password = edt_SignUpPassword.getText().toString();
            String repassword = edt_SignUpConfirmPassword.getText().toString();
            if (username.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
                errorSnkbar(layout, "Please fill all fields");
            } else if (password.length() < 8) {
                errorSnkbar(layout, "Password must be at least 8 characters");
            } else if (!password.matches(".*[A-Z].*")) {
                errorSnkbar(layout, "Password must contain at least 1 uppercase letter");
            } else if (!password.matches(".*[a-z].*")) {
                errorSnkbar(layout, "Password must contain at least 1 lowercase letter");
            } else if (!password.matches(".*[0-9].*")) {
                errorSnkbar(layout, "Password must contain at least 1 number");
            } else if (!password.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*")) {
                errorSnkbar(layout, "Password must contain at least 1 special character");
            } else if (!password.equals(repassword)) {
                errorSnkbar(layout, "Password and confirm password must be the same");
            } else {
                if (UserDAO.checkUsername(username)) {
                    errorSnkbar(layout, "Username already exists");
                } else {
                    UserDAO.insert(username, password);
                    successSnkbar(layout);
                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(SignUp.this, Login.class));
                        finish();
                    }, 1000);
                }
            }
        });

    }

    private void errorSnkbar(LinearLayout layout, String errorText) {
        final Snackbar snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_SHORT);
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.snkbar_error, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        @SuppressLint("RestrictedApi") SnackbarLayout snackbarLayout = (SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView tv_Error = view.findViewById(R.id.tv_Error);
        tv_Error.setText(errorText);
        snackbarLayout.addView(view, 0);
        snackbar.show();
    }

    @SuppressLint("SetTextI18n")
    private void successSnkbar(LinearLayout layout) {
        final Snackbar snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_SHORT);
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.snkbar_success, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        @SuppressLint("RestrictedApi") SnackbarLayout snackbarLayout = (SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView tv_Success = view.findViewById(R.id.tv_Success);
        tv_Success.setText("Sign up successfully");
        snackbarLayout.addView(view, 0);
        snackbar.show();
    }

}