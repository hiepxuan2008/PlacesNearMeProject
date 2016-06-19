package com.itshareplus.placesnearme.Acitivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.itshareplus.placesnearme.Model.GlobalVars;
import com.itshareplus.placesnearme.Module.LoginToServer;
import com.itshareplus.placesnearme.Module.LoginToServerListener;
import com.itshareplus.placesnearme.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginToServerListener {

    Button btnLogin;
    Button btnRegister;
    EditText txtUsername;
    EditText txtPassword;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnReg);
        txtUsername = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(this);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnBack:
                this.finish();
                break;
        }
    }

    private void login() {
        String username = txtUsername.getText().toString();
        String password =txtPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username or password can't be blank!", Toast.LENGTH_SHORT).show();
            return;
        }
        new LoginToServer(this, username, password).execute();
    }


    @Override
    public void OnLoginToServerStart() {

    }

    @Override
    public void OnLoginToServerSuccess(int userID, String avatar) {
        GlobalVars.setUserId(userID);
        GlobalVars.setAvatar(avatar);
        this.finish();
    }

    @Override
    public void OnLoginToServerFailed(String error_message) {
        Toast.makeText(this, error_message, Toast.LENGTH_SHORT).show();
    }
}
