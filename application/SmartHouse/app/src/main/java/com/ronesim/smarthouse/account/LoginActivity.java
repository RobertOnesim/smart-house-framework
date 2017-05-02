package com.ronesim.smarthouse.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ronesim.smarthouse.R;
import com.ronesim.smarthouse.model.User;
import com.ronesim.smarthouse.remote.APIService;
import com.ronesim.smarthouse.remote.APIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;
    @BindView(R.id.input_username)
    EditText inputUsername;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.link_signup)
    TextView linkSignup;
    private boolean loginStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // Login system
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                login();
            }
        });

        // Start the SignUp activity
        linkSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }


    private void login() {
        // check input is correct
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        if (!validateInput(username, password)) {
            loginFailed();
            return;
        }


        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Login_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        APIService apiService = APIUtils.getAPIService();
        apiService.loginUser(username, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    loginStatus = true;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                loginStatus = false;
            }
        });

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (loginStatus)
                            loginSuccess();
                        else
                            loginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // Default: finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void loginSuccess() {
        btnLogin.setEnabled(true);
        finish();
    }

    private void loginFailed() {
        Toast.makeText(getBaseContext(), "Login failed. Invalid username or password.", Toast.LENGTH_LONG).show();

        btnLogin.setEnabled(true);
    }

    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            inputUsername.setError("Enter an username.");
            return false;
        } else
            inputUsername.setError(null);

        if (password.isEmpty()) {
            inputPassword.setError("Enter your password.");
            return false;
        } else
            inputPassword.setError(null);

        return true;
    }


}
