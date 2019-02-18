package com.example.registerandloginproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private static final int REQUEST_SIGN_UP = 0;
    private UserLocalStore userLocalStore;
    private User user;

    // UI references.
    @BindView(R.id.login_Email) TextInputEditText mEmailText;
    @BindView(R.id.login_Email_text_input_layout) TextInputLayout mEmailLayout;
    @BindView(R.id.login_Password) TextInputEditText mPasswordText;
    @BindView(R.id.login_Password_text_input_layout) TextInputLayout mPasswordLayout;
    @BindView(R.id.sign_in_button) Button SignInButton;
    @BindView(R.id.new_account_link) TextView registrationLink;
    @BindView(R.id.login_form) View mLoginFormView;
    @BindView(R.id.login_progress) View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        //Add textWatcher to validate paassword while user is typing
        mPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mPasswordLayout.setError(getString(R.string.error_field_required));
                } else if (s.length() < 3 ) {
                    mPasswordLayout.setError(getString(R.string.error_min_length));
                } else if (s.length() > 30){
                    mPasswordLayout.setError(getString(R.string.error_max_length));
                } else {
                    mPasswordLayout.setError(null);
                }
            }
        });

        //Add textWatcher to validate email while user is typing
        mEmailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mEmailLayout.setError(getString(R.string.error_field_required));
                } else if (!isEmailValid(s)) {
                    mEmailLayout.setError(getString(R.string.error_invalid_email));
                } else {
                    mEmailLayout.setError(null);
                }
            }
        });

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        registrationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SignUp activity
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivityForResult(intent, REQUEST_SIGN_UP);
            }
        });

        userLocalStore = new UserLocalStore(this);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailLayout.setError(null);
        mPasswordLayout.setError(null);

        // Store values at the time of the login attempt.
        String email = Objects.requireNonNull(mEmailText.getText()).toString();
        String password = Objects.requireNonNull(mPasswordText.getText()).toString();

        boolean cancel = false;
        View focusView = null;

        //validate password input
        //check if empty, less then 3 chars, more then 30 chars
        if (TextUtils.isEmpty(password)) {
            mPasswordLayout.setError(getString(R.string.error_field_required));
            focusView = mPasswordText;
            cancel = true;
        } else if (password.length() < 3 ) {
            mPasswordLayout.setError(getString(R.string.error_min_length));
            focusView = mPasswordText;
            cancel = true;
        } else if (password.length() > 30){
            mPasswordLayout.setError(getString(R.string.error_max_length));
            focusView = mPasswordText;
            cancel = true;
        }

        // Validate Email input
        // Check if empty, check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailLayout.setError(getString(R.string.error_field_required));
            focusView = mEmailText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailLayout.setError(getString(R.string.error_invalid_email));
            focusView = mEmailText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password, this);
            mAuthTask.execute((Void) null);
        }
    }

    //check if email address is proper format
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final Context mContext;

        UserLoginTask(String email, String password, Context context) {
            mEmail = email;
            mPassword = password;
            mContext= context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            DBTools dbTools = null;

            try{
                dbTools = new DBTools(mContext);
                user = dbTools.getUser(mEmail);

                if (user.userId > 0) {
                    // Account exists, check password.
                    return user.password.equals(mPassword);
                } else {
                    user.password=mPassword;
                    return false;
                }
            } finally{
                if (dbTools != null)
                    dbTools.close();
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                userLocalStore.storeUserData(user);
                userLocalStore.setUserLoggedIn(true);
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivityForResult(intent, 0);
                finish();
            } else {
                mPasswordLayout.setError(getString(R.string.error_incorrect_password));
                mPasswordLayout.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}