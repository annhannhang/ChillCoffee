package com.ptnzzn.chillcoffee.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.Snackbar.SnackbarLayout;
import com.ptnzzn.chillcoffee.R;
import com.ptnzzn.chillcoffee.dao.UserDAO;

public class Login extends AppCompatActivity {
    ImageView img_BackFromLogin;
    EditText edt_Username, edt_Password;
    Button btn_Login;
    UserDAO dao;
    LinearLayout layout;
    CheckBox cb_LoginWithAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layout = findViewById(R.id.layoutLogin);
        img_BackFromLogin = findViewById(R.id.img_BackFromLogin);
        edt_Username = findViewById(R.id.edt_Username);
        edt_Password = findViewById(R.id.edt_Password);
        btn_Login = findViewById(R.id.btn_Login);
        cb_LoginWithAdmin = findViewById(R.id.cb_LoginWithAdmin);

        dao = new UserDAO(this);

        img_BackFromLogin.setOnClickListener(v -> finish());

        btn_Login.setOnClickListener(v -> {
            String username = edt_Username.getText().toString().trim();
            String password = edt_Password.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                errorSnkbar(layout, "Please enter username and password!");
            } else {
                if (cb_LoginWithAdmin.isChecked()) {
                    if (UserDAO.checkLogin(username, password)) {
                        successSnkbar(layout, "Login successfully!");
                        new Handler().postDelayed(() -> {
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        }, 1000);
                    } else {
                        errorSnkbar(layout, "Wrong username or password!");
                    }
                } else {
                    if (UserDAO.checkLogin(username, password)) {
                        successSnkbar(layout, "Login successfully!");
                        new Handler().postDelayed(() -> {
                            startActivity(new Intent(Login.this, MainActivityForStaff.class));
                            finish();
                        }, 1000);
                    } else {
                        errorSnkbar(layout, "Wrong username or password!");
                    }
                }
            }
        });
    }

    private void errorSnkbar(LinearLayout layout, String errorText) {
        final Snackbar snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_SHORT);
        View view = getLayoutInflater().inflate(R.layout.snkbar_error, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        @SuppressLint("RestrictedApi") SnackbarLayout snackbarLayout = (SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView tv_Error = view.findViewById(R.id.tv_Error);
        tv_Error.setText(errorText);
        snackbarLayout.addView(view, 0);
        snackbar.show();
    }

    private void successSnkbar(LinearLayout layout, String successText) {
        final Snackbar snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_SHORT);
        View view = getLayoutInflater().inflate(R.layout.snkbar_success, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        @SuppressLint("RestrictedApi") SnackbarLayout snackbarLayout = (SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView tv_Success = view.findViewById(R.id.tv_Success);
        tv_Success.setText(successText);
        snackbarLayout.addView(view, 0);
        snackbar.show();
    }
}