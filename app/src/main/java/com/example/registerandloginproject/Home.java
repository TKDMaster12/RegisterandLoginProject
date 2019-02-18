package com.example.registerandloginproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity {

    private UserLocalStore userLocalStore;

    @BindView(R.id.username) TextView userName;
    @BindView(R.id.logout_button) Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (!authenticate()) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivityForResult(intent, 0);
        }
        else
            getUserName();
    }

    private boolean authenticate()
    {
        return userLocalStore.getUserLoggedIn();
    }

    private void getUserName()
    {
        User user = userLocalStore.getLoggedInUser();
        userName.setText(user.name);
    }

    private void showAlertDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Home.this);
        dialogBuilder.setTitle("Log Out");
        dialogBuilder.setMessage("Are you sure you want to Log Out?");
        dialogBuilder.setPositiveButton("LOG OUT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                finish();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivityForResult(intent, 0);
            }
        });
        dialogBuilder.setNegativeButton("CANCEL", null);
        dialogBuilder.setCancelable(false);
        dialogBuilder.show();
    }

    @Override
    public void onBackPressed() {

    }
}