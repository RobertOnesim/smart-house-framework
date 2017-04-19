package com.ronesim.smarthouse.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
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

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.input_username)
    EditText inputUsername;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_email2)
    EditText inputEmail2;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.btn_signup)
    AppCompatButton btnSignup;
    @BindView(R.id.link_login)
    TextView linkLogin;
    private boolean signUpStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        // SignUp system
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        // back to the login page
        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    private void signUp() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String email2 = inputEmail2.getText().toString();
        String password = inputPassword.getText().toString();

        // check info
        if (!validateForm(username, email, email2, password)) {
            signUpFailed();
            return;
        }

        btnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Login_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();


        APIService apiService = APIUtils.getAPIService();
        apiService.registerUser(username, email, email2, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    signUpStatus = true;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                signUpStatus = false;
            }
        });

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (signUpStatus)
                            signUpSuccess();
                        else
                            signUpFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    private void signUpSuccess() {
        btnSignup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    private void signUpFailed() {
        Toast.makeText(getBaseContext(), "Sign up failed. Username or Email exists.", Toast.LENGTH_LONG).show();

        btnSignup.setEnabled(true);
    }

    private boolean validateForm(String username, String email, String email2, String password) {
        if (username.isEmpty() || username.contains(" ")) {
            inputUsername.setError("No Spaces Allowed.");
            return false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Enter a valid email address.");
            return false;
        }

        if (email2.isEmpty() || !email2.equals(email)) {
            inputEmail2.setError("Enter does not match.");
            return false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            inputPassword.setError("Between 4 and 20 alphanumeric characters");
            return false;
        }

        return true;
    }
}
