package com.ptnzzn.chillcoffee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.dao.UserDAO;

public class ChangePassword extends AppCompatActivity {
    ImageView img_BackFromChangePassword;
    EditText edt_ChangeUsername, edt_ChangePassword, edt_ChangeNewPassword;
    Button btn_ChangePassword;
    UserDAO dao;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        img_BackFromChangePassword = findViewById(R.id.img_BackFromChangePassword);
        edt_ChangeUsername = findViewById(R.id.edt_ChangeUsername);
        edt_ChangePassword = findViewById(R.id.edt_ChangePassword);
        edt_ChangeNewPassword = findViewById(R.id.edt_ChangeNewPassword);
        btn_ChangePassword = findViewById(R.id.btn_ChangePassword);
        layout = findViewById(R.id.layoutChangePassword);

        dao = new UserDAO(this);

        img_BackFromChangePassword.setOnClickListener(v -> finish());

        btn_ChangePassword.setOnClickListener(v -> {
            String username = edt_ChangeUsername.getText().toString();
            String password = edt_ChangePassword.getText().toString();
            String newPassword = edt_ChangeNewPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty() || newPassword.isEmpty()) {
                errorSnkbar(layout, "Please fill all fields");
            } else if (newPassword.length() < 8) {
                errorSnkbar(layout, "Password must be at least 8 characters");
            } else if (!newPassword.matches(".*[A-Z].*")) {
                errorSnkbar(layout, "Password must contain at least 1 uppercase letter");
            } else if (!newPassword.matches(".*[a-z].*")) {
                errorSnkbar(layout, "Password must contain at least 1 lowercase letter");
            } else if (!newPassword.matches(".*[0-9].*")) {
                errorSnkbar(layout, "Password must contain at least 1 number");
            } else if (!newPassword.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*")) {
                errorSnkbar(layout, "Password must contain at least 1 special character");
            } else {
                if (UserDAO.checkLogin(username, password)) {
                    UserDAO.update(username, newPassword);
                    successSnkbar(layout, "Change password successfully");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 3000);
                } else {
                    errorSnkbar(layout, "Username or password is incorrect");
                }
            }
        });
    }

    private void errorSnkbar(LinearLayout layout, String errorText) {
        final Snackbar snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_SHORT);
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.snkbar_error, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView tv_Error = view.findViewById(R.id.tv_Error);
        tv_Error.setText(errorText);
        snackbarLayout.addView(view, 0);
        snackbar.show();
    }

    private void successSnkbar(LinearLayout layout, String successText) {
        final Snackbar snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_SHORT);
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.snkbar_success, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView tv_Success = view.findViewById(R.id.tv_Success);
        tv_Success.setText(successText);
        snackbarLayout.addView(view, 0);
        snackbar.show();
    }
}