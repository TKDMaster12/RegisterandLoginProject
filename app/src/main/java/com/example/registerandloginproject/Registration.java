package com.example.registerandloginproject;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A Registration screen that offers register name/email/password/birthdate.
 */
public class Registration extends AppCompatActivity {

    @BindView(R.id.register_firstName) TextInputEditText _firstNameText;
    @BindView(R.id.register_firstName_text_input_layout) TextInputLayout _firstNameLayout;
    @BindView(R.id.register_lastName) TextInputEditText _lastNameText;
    @BindView(R.id.register_lastName_text_input_layout) TextInputLayout _lastNameLayout;
    @BindView(R.id.register_Email) TextInputEditText _emailText;
    @BindView(R.id.register_Email_text_input_layout) TextInputLayout _EmailLayout;
    @BindView(R.id.register_Password) TextInputEditText _passwordText;
    @BindView(R.id.register_Password_text_input_layout) TextInputLayout _PasswordLayout;
    @BindView(R.id.register_Confirm_Password) TextInputEditText _passwordConfirmText;
    @BindView(R.id.register_Confirm_Password_text_input_layout) TextInputLayout _PasswordConfirmLayout;
    @BindView(R.id.register_BirthDate) TextInputEditText _birthDateText;
    @BindView(R.id.register_BirthDate_text_input_layout) TextInputLayout _BirthDateLayout;
    @BindView(R.id.btn_SignUp) Button _signUpButton;
    @BindView(R.id.link_login) TextView _loginLink;
    @BindView(R.id.registration_form) View mRegistrationFormView;
    @BindView(R.id.registration_progress) View mProgressView;
    @BindView(R.id.messageLayout) View messageLayout;

    private DatePickerDialog datePickerDialog;
    private int year;
    private int month;
    private int dayOfMonth;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);

        _signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        //Add textWatcher to validate firstname while user is typing
        _firstNameText.addTextChangedListener(new TextWatcher() {
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
                    _firstNameLayout.setError(getString(R.string.error_field_required));
                } else if (s.length() < 3 ) {
                    _firstNameLayout.setError(getString(R.string.error_min_length));
                } else if (s.length() > 30){
                    _firstNameLayout.setError(getString(R.string.error_max_length));
                } else {
                    _firstNameLayout.setError(null);
                }
            }
        });

        //Add textWatcher to validate lastname while user is typing
        _lastNameText.addTextChangedListener(new TextWatcher() {
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
                    _lastNameLayout.setError(getString(R.string.error_field_required));
                } else if (s.length() < 3 ) {
                    _lastNameLayout.setError(getString(R.string.error_min_length));
                } else if (s.length() > 30){
                    _lastNameLayout.setError(getString(R.string.error_max_length));
                } else {
                    _lastNameLayout.setError(null);
                }
            }
        });

        //Add textWatcher to validate email while user is typing
        _emailText.addTextChangedListener(new TextWatcher() {
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
                    _EmailLayout.setError(getString(R.string.error_field_required));
                } else if (!isEmailValid(s)) {
                    _EmailLayout.setError(getString(R.string.error_invalid_email));
                } else {
                    _EmailLayout.setError(null);
                }
            }
        });

        //Add onclick listener to create date picker popup
        _birthDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                // Set the timezone which you want to display time.
                calendar.setTimeZone(TimeZone.getTimeZone("America/New_York"));

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Registration.this, R.style.CustomDatePickerDialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                String newDate = (month + 1) + "/" + day + "/" + year;
                                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                                Date date = new Date();

                                try {
                                    date = dateFormat.parse(newDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                _birthDateText.setText(dateFormat.format(date));
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        //Add textWatcher to validate password while user is typing
        _passwordText.addTextChangedListener(new TextWatcher() {
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
                    _PasswordLayout.setError(getString(R.string.error_field_required));
                } else if (s.length() < 5 ) {
                    _PasswordLayout.setError(getString(R.string.error_min_length_password));
                } else if (s.length() > 30){
                    _PasswordLayout.setError(getString(R.string.error_max_length));
                } else {
                    _PasswordLayout.setError(null);
                }
            }
        });

        //Add textWatcher to validate confirm password while user is typing
        _passwordConfirmText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    _PasswordConfirmLayout.setError(getString(R.string.error_field_required));
                } else if (s.length() < 5 ) {
                    _PasswordConfirmLayout.setError(getString(R.string.error_min_length_password));
                } else if (s.length() > 30){
                    _PasswordConfirmLayout.setError(getString(R.string.error_max_length));
                } else if (!s.toString().equals(_passwordText.getText().toString())) {
                    _PasswordConfirmLayout.setError(getString(R.string.error_match_password));
                } else {
                    _PasswordConfirmLayout.setError(null);
                }
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    protected void signUp() {
        boolean cancel = false;
        View focusView = null;

        //disable signup button
        _signUpButton.setEnabled(false);

        //set all error to null
        _firstNameLayout.setError(null);
        _lastNameLayout.setError(null);
        _BirthDateLayout.setError(null);
        _EmailLayout.setError(null);
        _PasswordLayout.setError(null);

        //store value at the time of the registration attempt
        String fname = Objects.requireNonNull(_firstNameText.getText()).toString();
        String lname = Objects.requireNonNull(_lastNameText.getText()).toString();
        String birthDate = Objects.requireNonNull(_birthDateText.getText()).toString();
        String email = Objects.requireNonNull(_emailText.getText()).toString();
        String password = Objects.requireNonNull(_passwordText.getText()).toString();
        String confirmPassword = Objects.requireNonNull(_passwordConfirmText.getText()).toString();

        //validate first name input
        //check if empty, less then 3 chars, more then 30 chars
        if (TextUtils.isEmpty(fname)) {
            _firstNameLayout.setError(getString(R.string.error_field_required));
            focusView = _firstNameText;
            cancel = true;
        } else if (fname.length() < 3 ) {
            _firstNameLayout.setError(getString(R.string.error_min_length));
            focusView = _firstNameText;
            cancel = true;
        } else if (fname.length() > 30){
            _firstNameLayout.setError(getString(R.string.error_max_length));
            focusView = _firstNameText;
            cancel = true;
        }

        //validate last name input
        //check if empty, less then 3 chars, more then 30 chars
        if (TextUtils.isEmpty(lname)) {
            _lastNameLayout.setError(getString(R.string.error_field_required));
            focusView = _lastNameText;
            cancel = true;
        } else if (lname.length() < 3 ) {
            _lastNameLayout.setError(getString(R.string.error_min_length));
            focusView = _lastNameText;
            cancel = true;
        } else if (lname.length() > 30){
            _lastNameLayout.setError(getString(R.string.error_max_length));
            focusView = _lastNameText;
            cancel = true;
        }

        // Validate Email input
        // Check if empty, check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            _EmailLayout.setError(getString(R.string.error_field_required));
            focusView = _emailText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            _EmailLayout.setError(getString(R.string.error_invalid_email));
            focusView = _emailText;
            cancel = true;
        }

        // Validate BirthDate input
        // Check if empty.
        if (birthDate.isEmpty()) {
            _BirthDateLayout.setError(getString(R.string.error_field_required));
            focusView = _birthDateText;
            cancel = true;
        }

        //validate password input
        //check if empty, less then 5 chars, more then 30 chars
        if (TextUtils.isEmpty(password)) {
            _PasswordLayout.setError(getString(R.string.error_field_required));
            focusView = _passwordText;
            cancel = true;
        } else if (password.length() < 5 ) {
            _PasswordLayout.setError(getString(R.string.error_min_length_password));
            focusView = _passwordText;
            cancel = true;
        } else if (password.length() > 30){
            _PasswordLayout.setError(getString(R.string.error_max_length));
            focusView = _passwordText;
            cancel = true;
        }

        //validate confirm password input
        //check if empty, less then 5 chars, more then 30 chars, match password
        if (TextUtils.isEmpty(confirmPassword)) {
            _PasswordConfirmLayout.setError(getString(R.string.error_field_required));
            focusView = _passwordConfirmText;
            cancel = true;
        } else if (confirmPassword.length() < 5 ) {
            _PasswordConfirmLayout.setError(getString(R.string.error_min_length_password));
            focusView = _passwordConfirmText;
            cancel = true;
        } else if (confirmPassword.length() > 30){
            _PasswordConfirmLayout.setError(getString(R.string.error_max_length));
            focusView = _passwordConfirmText;
            cancel = true;
        } else if (!confirmPassword.equals(password)) {
            _PasswordConfirmLayout.setError(getString(R.string.error_match_password));
        }

        //if no errors found register user else set focus on first error
        if (cancel) {
            focusView.requestFocus();
            _signUpButton.setEnabled(true);
        } else {
            showProgress(true);
            User user = new User(0, fname, email, birthDate, password);
            registerUser(user);
        }
    }

    //check if email address is proper format
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void registerUser(User user) {
        //TODO Future send data to database
        DBTools dbTools = null;

        try{
            finish();
            dbTools = new DBTools(getApplicationContext());
            user = dbTools.insertUser(user);
        } finally{
            if (dbTools != null)
                dbTools.close();
        }

        //Show success message then redirect to login page
        mRegistrationFormView.setVisibility(View.GONE);
        messageLayout.setVisibility(View.VISIBLE);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 3 seconds
                finish();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivityForResult(intent, 0);
            }
        }, 3000);
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

        mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mRegistrationFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
}