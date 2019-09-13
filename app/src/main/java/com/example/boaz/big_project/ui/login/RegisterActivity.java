package com.example.boaz.big_project.ui.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boaz.big_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Activitys.EmployeeActivity;
import Activitys.MainActivity;
import Activitys.ManagerActivity;
import Fragments.MADialog;
import Fragments.POP_UP;
import Fragments.Register_EM_Fragment;
import Fragments.Register_MA_Fragment;

public class RegisterActivity extends AppCompatActivity implements
        Register_EM_Fragment.Register_EM_Fragment_InteractionListener ,
        Register_MA_Fragment.Register_MA_Fragment_InteractionListener {

    private FirebaseAuth mAuth;
    private LoginViewModel loginViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "RegisterActivity";
    final boolean IsManger=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.Register_Email);
        final EditText passwordEditText = findViewById(R.id.Register_password);
        //final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });



        //keep an android user logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // User is signed in
//            Toast.makeText(RegisterActivity.this, "login as "+user.getEmail(), Toast.LENGTH_SHORT).show();
//
//            if (IsManger){
//
//
//                Intent i = new Intent(RegisterActivity.this, ManagerActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);
//            }
//            else {
//                Intent i = new Intent(RegisterActivity.this, EmployeeActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);
//            }
//
//
//        } else {
//            // User is signed out
//            Log.d(BATTERY_SERVICE, "onAuthStateChanged:signed_out");
//        }
    }
    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }


    //---------------------------------------------------------------------------------------------------------------------------------------

    public void Register_func(View view) {
        // TODO: organize the code
        String group_name_String=null;
        //Email
        TextView emailEditText = (TextView) findViewById(R.id.Register_Email);
        final String userMail = emailEditText.getText().toString();
        //Password
        TextView passwordEditText = (TextView) findViewById(R.id.Register_password);
        final String password = passwordEditText.getText().toString();
        // FullName
        TextView nameEditText = (TextView) findViewById(R.id.Register_FullName);
        final String fullName = nameEditText.getText().toString();
        // Phone
        TextView phoneEditText = (TextView) findViewById(R.id.Register_UserPhone);
        final String strUserphone = phoneEditText.getText().toString();
        final int phone = Integer.parseInt(strUserphone);
        // User ID
        TextView UserIDEditText = (TextView) findViewById(R.id.Register_UserID);
        final String strUserID = UserIDEditText.getText().toString();
        final int userID = Integer.parseInt(strUserID);

        //check if is manger
        final boolean IsManger=((CheckBox) findViewById(R.id.checkBox_Ma)).isChecked();

        if (IsManger) {

            TextView CompanyName = (TextView) findViewById(R.id.MA_CompanyName);
            group_name_String = CompanyName.getText().toString();
        }

        else  {
            //group name
            TextView group_name = (TextView) findViewById(R.id.EM_CopiedCompanyID);
            group_name_String = group_name.getText().toString();
        }

        final String group=group_name_String;

        mAuth.createUserWithEmailAndPassword(userMail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "Authentication success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                            //FirebaseAuth currentFirebaseCompany = FirebaseAuth.getInstance();

                            Map<String, Object> userMAP = new HashMap<>();
                            Map<String, Object> userCompanyMAP = new HashMap<>();
                            userMAP.put("name", fullName);
                            userMAP.put("mail", userMail);
                            userMAP.put("password", password);
                            userMAP.put("phone", phone);
                            userMAP.put("RealUserID",userID);
                            userMAP.put("isMang", IsManger);
                            userCompanyMAP.put("group_name", group);

                            for (int i=1;i<=52;i++)
                                userCompanyMAP.put(String.valueOf(i),null);
//                            //check if is manger
//                            if (IsManger) {
//
//                               // userCompanyMAP.put("ID_group", db.collection("Company").document(""+currentFirebaseUser.getUid()));
//                                userCompanyMAP.put("group_name", group);
//
//                            }
//                            else{
//                                userCompanyMAP.put("group_name", group);
//                            }


                            db.collection("User").document(""+currentFirebaseUser.getUid())
                                    .set(userMAP)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot USER successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });
                            db.collection("User").document(""+currentFirebaseUser.getUid()).collection("UserCompany")
                                    .add(userCompanyMAP);

                            db.collection("Company").document(""+currentFirebaseUser.getUid())
                                    .set(userCompanyMAP).addOnSuccessListener(
                                    new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot COMPANY successfully written!");
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error writing document", e);
                                        }
                                    });


                            if (IsManger) {

                                Intent i = new Intent(getApplicationContext(), ManagerActivity.class);
                                startActivity(i);

                            }
                            else {

                                Intent i = new Intent(getApplicationContext(), EmployeeActivity.class);
                                startActivity(i);
                            }

                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "מייל קיים במערכת", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(RegisterActivity.this, "Authentication failed" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // TODO: BACKUP of create User
//        mAuth.createUserWithEmailAndPassword(user, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(RegisterActivity.this, "Authentication success", Toast.LENGTH_SHORT).show();
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(i);
//                        } else {
//                            // If sign in fails, display a message to the user.
//
//                            Toast.makeText(RegisterActivity.this, "Authentication failed" + task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        // TODO: chack how add custom details to user
        //  https://www.youtube.com/watch?v=7Yc3Pt37coM
//        mAuth.createUserWithEmailAndPassword(user, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                    .setDisplayName("yak").build();
//
//                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(RegisterActivity.this, "User profile updated.", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(i);
//
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//
//                            Toast.makeText(RegisterActivity.this, "Authentication failed" + task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });



    }






    public void FragmentView(View view) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.Fragment_container);

        final CheckBox Manager_checkBox = (CheckBox) findViewById(R.id.checkBox_Ma);
        final CheckBox Employess_checkBox = (CheckBox) findViewById(R.id.checkBox_Em);

        //Manager_checkBox.setOnCheckedChangeListener();
        Manager_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Employess_checkBox.setChecked(false);
                }
            }
        });
        Employess_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Manager_checkBox.setChecked(false);
                }
            }
        });



        if (fragment == null)
        {

            if(Manager_checkBox.isChecked())
            {

                fragment = new Register_MA_Fragment();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.add(R.id.Fragment_container,fragment).addToBackStack(null).commit();
            }
            if(Employess_checkBox.isChecked())
            {
                fragment = new Register_EM_Fragment();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.add(R.id.Fragment_container,fragment).commit();
            }
        }

        if (!(fragment == null))
        {

            if(Manager_checkBox.isChecked())
            {
                fragment = new Register_MA_Fragment();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.Fragment_container,fragment).commit();
            }
            if(Employess_checkBox.isChecked())
            {
                fragment = new Register_EM_Fragment();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.Fragment_container,fragment).commit();
            }
        }
    }


    @Override
    public void Register_MA_Fragment_InteractionListener(Uri uri) {

    }
    @Override
    public void Register_EM_Fragment_InteractionListener(Uri uri) {

    }
}