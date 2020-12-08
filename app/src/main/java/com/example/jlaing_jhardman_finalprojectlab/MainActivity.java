package com.example.jlaing_jhardman_finalprojectlab;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    public AccountHandling accountHandling = new AccountHandling();

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
        setupPassword.setText(accountHandling.generateRandomString(10));
    }

    public void onGenUsernameAccount (View v){
        EditText setupUsername = findViewById(R.id.txtSubmissionsUsername);
        setupUsername.setText(accountHandling.generateRandomString(8));
    }

    public void onGenPasswordAccount(View v){
        EditText setupPassword = findViewById(R.id.txtSubmissionPassword);
        setupPassword.setText(accountHandling.generateRandomString(10));
    }

    @SuppressLint("SetTextI18n")
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
            ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(DataStorage.appSecrets));
            tmpList.add(appSecret);
            DataStorage.appSecrets = tmpList.toArray(new String[DataStorage.appSecrets.length + 1]);
            setContentView(R.layout.login);
        }
    }

    public void onAccountExists(View v){
        if(DataStorage.appSecrets.length == 0){
            TextView ErrorView = findViewById(R.id.viewError);
            ErrorView.setText("Could not find an existing account.\nPlease create a new one.");
        } else {setContentView(R.layout.login);}
    }

    public void onCreateAccountPress(View v){
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("SetTextI18n")
    public void onLogin(View v){
        EditText usernameLogin = findViewById(R.id.txtUsernameLogin);
        EditText passwordLogin = findViewById(R.id.txtPasswordLogin);

        String proposedSecret = usernameLogin.getText().toString() + ":" + passwordLogin.getText().toString();

        if(accountHandling.accountCheck(DataStorage.appSecrets, proposedSecret)){
            TextView accountNameBox = findViewById(R.id.textView3);

            for (String element : DataStorage.accountNames) {
                accountNameBox.setText(element);
            }

            setContentView(R.layout.account_display);
        } else {
            TextView ErrorView = findViewById(R.id.viewErrorTwo);
            ErrorView.setText("The credentials you entered could not be found.");
        }
    }

    public void onDelete(View v){
        DataStorage.appSecrets = new String[]{};
        DataStorage.accountNames = new String[]{};
        DataStorage.accountSecrets = new String[]{};

        setContentView(R.layout.activity_main);

        TextView ErrorView = findViewById(R.id.viewError);
        ErrorView.setText("Could not find an existing account.\nPlease create a new one.");
    }

    public void onCancelCreation(View v){
        setContentView(R.layout.account_display);
    }

    public void onSubmitAccountCreation(View v) {
        EditText accountUsername = findViewById(R.id.txtSubmissionsUsername);
        EditText accountPassword = findViewById(R.id.txtSubmissionPassword);
        EditText accountName = findViewById(R.id.txtSubmissionName);

        String Username = accountUsername.getText().toString();
        String Password = accountPassword.getText().toString();
        String Name = accountName.getText().toString();

        TextView ErrorView = findViewById(R.id.viewErrorThree);

        if (Username.contentEquals("") || Password.contentEquals("") || Name.contentEquals("")) {
            ErrorView.setText("Please make sure to make an entry in all available fields.");
        } else if (accountHandling.accountCheck(DataStorage.accountNames, Name)){
            ErrorView.setText("An account already exists with that name.\nPlease choose something else.");
        } else {
            ArrayList<String> tmpAccountNames = new ArrayList<>(Arrays.asList(DataStorage.accountNames));
            ArrayList<String> tmpAccountSecrets = new ArrayList<>(Arrays.asList(DataStorage.accountSecrets));
            tmpAccountSecrets.add(Username + "\n\n" + Password);
            tmpAccountNames.add(Name);

            DataStorage.accountNames = tmpAccountNames.toArray(new String[DataStorage.accountNames.length + 1]);
            DataStorage.accountSecrets = tmpAccountSecrets.toArray(new String[DataStorage.accountSecrets.length + 1]);



            for (String element : DataStorage.accountNames) {
                accountHandling.results += element + "\n";
                System.out.println(accountHandling.results);
            }

            setContentView(R.layout.account_display);

            TextView accountNameBox = findViewById(R.id.textView3);
            accountNameBox.setText(accountHandling.results);
        }
    }

    public void onAddNewAccount(View v){
        setContentView(R.layout.account_create);
    }

    public void onAccountSearch(View v){
        EditText queryItem = findViewById(R.id.txtAccountToSearch);
        String query = queryItem.getText().toString();

        if(query.contentEquals("")){} else {
            if(accountHandling.accountCheck(DataStorage.accountNames, query)){
                int index = accountHandling.accountIndex(DataStorage.accountNames, query);
                String result = DataStorage.accountSecrets[index];

                TextView resultsBox = findViewById(R.id.textView4);
                resultsBox.setText(result);
            } else {}
        }
    }
    

}