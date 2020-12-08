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

    // Make AccountHandling.java usable within this file
    public AccountHandling accountHandling = new AccountHandling();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    // Creates random string for the username box in activity_main.xml
    public void onGenUsernameIntro (View v){
        EditText setupUsername = findViewById(R.id.txtSetupUsername);
        setupUsername.setText(accountHandling.generateRandomString(8));
    }

    // Creates random string for the password box in activity_main.xml
    public void onGenPasswordIntro(View v){
        EditText setupPassword = findViewById(R.id.txtSetupPassword);
        setupPassword.setText(accountHandling.generateRandomString(10));
    }

    // Creates random string for the username box in account_create.xml
    public void onGenUsernameAccount (View v){
        EditText setupUsername = findViewById(R.id.txtSubmissionsUsername);
        setupUsername.setText(accountHandling.generateRandomString(8));
    }

    // Creates random string for the password box in account_create.xml
    public void onGenPasswordAccount(View v){
        EditText setupPassword = findViewById(R.id.txtSubmissionPassword);
        setupPassword.setText(accountHandling.generateRandomString(10));
    }

    // handles most important functions on activity_main's display
    @SuppressLint("SetTextI18n")
    public void onAccountCreate(View v){
        EditText setupPassword = findViewById(R.id.txtSetupPassword);
        EditText setupUsername = findViewById(R.id.txtSetupUsername);

        String Username = setupUsername.getText().toString();
        String Password = setupPassword.getText().toString();

        // checks if the username or password boxes are empty and prompts user to fill them
        if(Username.contentEquals("") || Password.contentEquals("")){
            TextView ErrorView = findViewById(R.id.viewError);
            ErrorView.setText("Please make sure to make an entry in all available fields.");
        } else {
            // Store the app's username and password in DataStorage class. Change screen back to login
            String appSecret = Username + ":" + Password;
            ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(DataStorage.appSecrets));
            tmpList.add(appSecret);
            DataStorage.appSecrets = tmpList.toArray(new String[DataStorage.appSecrets.length + 1]);
            setContentView(R.layout.login);
        }
    }

    public void onAccountExists(View v){
        /*Checks if there's any existing accounts in the array where they're stored by looking at length.
        If empty, it says there's no existing accounts. If it isn't then it switches to login screen */
        if(DataStorage.appSecrets.length == 0){
            TextView ErrorView = findViewById(R.id.viewError);
            ErrorView.setText("Could not find an existing account.\nPlease create a new one.");
        } else {setContentView(R.layout.login);}
    }

    // If the user selects to create an account (login page) they'll be taken to activity_main to make one
    public void onCreateAccountPress(View v){ setContentView(R.layout.activity_main);}


    // Handles login functions on login.xml's display
    @SuppressLint("SetTextI18n")
    public void onLogin(View v){
        EditText usernameLogin = findViewById(R.id.txtUsernameLogin);
        EditText passwordLogin = findViewById(R.id.txtPasswordLogin);

        String proposedSecret = usernameLogin.getText().toString() + ":" + passwordLogin.getText().toString();

        /* check if the appLogin information a user provided matches the username and passwords of DataStorage
        if it doesn't, it responds with an error. If it does it changes to the account_display screen */
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

    // Delete button on login screen. Clears contents of all arrays in DataStorage and prompts to create an account
    public void onDelete(View v){
        DataStorage.appSecrets = new String[]{};
        DataStorage.accountNames = new String[]{};
        DataStorage.accountSecrets = new String[]{};

        setContentView(R.layout.activity_main);

        TextView ErrorView = findViewById(R.id.viewError);
        ErrorView.setText("Could not find an existing account.\nPlease create a new one.");
    }

    // Returns from account_create to account_display. User doesn't add an entry
    public void onCancelCreation(View v){
        setContentView(R.layout.account_display);
    }

    // The submission button (checkmark) on account_create
    public void onSubmitAccountCreation(View v) {
        EditText accountUsername = findViewById(R.id.txtSubmissionsUsername);
        EditText accountPassword = findViewById(R.id.txtSubmissionPassword);
        EditText accountName = findViewById(R.id.txtSubmissionName);

        String Username = accountUsername.getText().toString();
        String Password = accountPassword.getText().toString();
        String Name = accountName.getText().toString();

        TextView ErrorView = findViewById(R.id.viewErrorThree);

        // If anyof the entry boxes are empty, it prompts to fill them without progressing until satisfied
        if (Username.contentEquals("") || Password.contentEquals("") || Name.contentEquals("")) {
            ErrorView.setText("Please make sure to make an entry in all available fields.");
        // Checks if the Name for the submission already exists in DataStorage account names. Prompts to change if true
        } else if (accountHandling.accountCheck(DataStorage.accountNames, Name)){
            ErrorView.setText("An account already exists with that name.\nPlease choose something else.");
        } else {
            // If everything is satisfactory, it saves the information in the according arrays and progresses to account_display
            ArrayList<String> tmpAccountNames = new ArrayList<>(Arrays.asList(DataStorage.accountNames));
            ArrayList<String> tmpAccountSecrets = new ArrayList<>(Arrays.asList(DataStorage.accountSecrets));
            tmpAccountSecrets.add(Username + "\n\n" + Password);
            tmpAccountNames.add(Name);

            DataStorage.accountNames = tmpAccountNames.toArray(new String[DataStorage.accountNames.length + 1]);
            DataStorage.accountSecrets = tmpAccountSecrets.toArray(new String[DataStorage.accountSecrets.length + 1]);


            /* iterate through array of names and concatenate them with newline delimiter to be
            displayed in the topmost textview of acount_display */
            for (String element : DataStorage.accountNames) {
                accountHandling.results += element + "\n";
            }

            setContentView(R.layout.account_display);

            TextView accountNameBox = findViewById(R.id.textView3);
            accountNameBox.setText(accountHandling.results);
        }
    }

    // change display from account_display to account_create
    public void onAddNewAccount(View v){ setContentView(R.layout.account_create);}

    // Handles search functions on account_display (account username and password)
    public void onAccountSearch(View v){
        EditText queryItem = findViewById(R.id.txtAccountToSearch);
        String query = queryItem.getText().toString();

        //If there's no result, do nothing. If there is then display it in bottom most text view
        if(query.contentEquals("")){} else {
            if(accountHandling.accountCheck(DataStorage.accountNames, query)){
                // Get the index of the Name and then use that to get the corresponding string of username and password
                int index = accountHandling.accountIndex(DataStorage.accountNames, query);
                String result = DataStorage.accountSecrets[index];

                TextView resultsBox = findViewById(R.id.textView4);
                resultsBox.setText(result);
            } else {}
        }
    }
    

}