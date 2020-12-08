package com.example.jlaing_jhardman_finalprojectlab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    public AccountHandling accountHandling = new AccountHandling();
    public DataStorage dataStorage = new DataStorage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void onGenUsernameIntro (View v){
        EditText setupUsername = findViewById(R.id.txtSetupUsername);
        setupUsername.setText(accountHandling.generateRandomString(8));
    }

    public void onGenPasswordIntro(View v){
        EditText setupPassword = findViewById(R.id.txtSetupPassword);
        setupPassword.setText(accountHandling.generateRandomString(8));
    }

    public void onAccountCreate(View v){
        EditText setupPassword = findViewById(R.id.txtSetupPassword);
        EditText setupUsername = findViewById(R.id.txtSetupUsername);

        String Username = setupUsername.getText().toString();
        String Password = setupPassword.getText().toString();

        if(Username.contentEquals("") || Password.contentEquals("")){
            TextView ErrorView = findViewById(R.id.viewError);
            ErrorView.setText("Please make sure to make an entry in all available fields.");
        } else {
            String appSecret = Username + ":" + Password;
            ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(dataStorage.appSecrets));
            tmpList.add(appSecret);
            dataStorage.appSecrets = tmpList.toArray(new String[dataStorage.appSecrets.length + 1]);
            setContentView(R.layout.login);
        }
    }

    public void onAccountExists(View v){
        if(dataStorage.appSecrets.length == 0){
            TextView ErrorView = findViewById(R.id.viewError);
            ErrorView.setText("Could not find an existing account.\nPlease create a new one.");
        } else {setContentView(R.layout.login);}
    }

    public void onCreateAccountPress(View v){
        setContentView(R.layout.activity_main);
    }

    public void onLogin(View v){
        EditText usernameLogin = findViewById(R.id.txtUsernameLogin);
        EditText passwordLogin = findViewById(R.id.txtPasswordLogin);

        String proposedSecret = usernameLogin.getText().toString() + ":" + passwordLogin.getText().toString();

        if(accountHandling.accountCheck(dataStorage.appSecrets, proposedSecret)){
            setContentView(R.layout.account_display);
        } else {
            TextView ErrorView = findViewById(R.id.viewErrorTwo);
            ErrorView.setText("The credentials you entered could not be found.");
        }
    }

    public void onDelete(View v){
        dataStorage.appSecrets = new String[]{};
        dataStorage.accountNames = new String[]{};
        dataStorage.accountSecrets = new String[]{};

        setContentView(R.layout.activity_main);

        TextView ErrorView = findViewById(R.id.viewError);
        ErrorView.setText("Could not find an existing account.\nPlease create a new one.");
    }

}