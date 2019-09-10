package Activitys;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boaz.big_project.R;
import com.example.boaz.big_project.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Fragments.EM_Final_Fragment;
import Fragments.EM_Summary_Fragment;
import Fragments.Em_Scheduling_Fragment;
import Fragments.MA_EmpList_Fragment;
import Fragments.MA_Final_Fragment;
import Fragments.MA_Scheduling_Fragment;
import Fragments.MA_Summary_Fragment;

public class ManagerActivity extends AppCompatActivity implements
        MA_EmpList_Fragment.MA_EMPLIST_FIListener,
        MA_Final_Fragment.MA_Final_FIListener,
        MA_Scheduling_Fragment.MA_SCHEDULING_FIListener,
        MA_Summary_Fragment.MA_SUMMARY_FIListener
{

    private static final String TAG = "ManagerActivity";
    private TextView textView_helloUser;


    private ClipboardManager myClipboard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        textView_helloUser = findViewById(R.id.MA_hello_User);
        Test_SQL_func();
        //Spinner spinner = findViewById(R.id.MA_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.number_test, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setGravity(Gravity.CENTER);
    }


    public void Fragment_move(View view) {
        // fregment_container = all the fragment will be on him
        FragmentManager manager= getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.MA_fragemt_container);

        if(fragment==null) //if it the first time to call the first fragmet
        {
            int id = view.getId();

            if (id == R.id.MA_Summary_button) {
                fragment = new MA_Summary_Fragment();
                FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null);
                transaction.add(R.id.MA_fragemt_container,fragment).commit();
            }
            else if (id == R.id.MA_Final_button) {
                fragment = new MA_Final_Fragment();
                FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null);
                transaction.add(R.id.MA_fragemt_container,fragment).commit();
            }
            else if (id == R.id.MA_Scheduling_button) {
                fragment = new MA_Scheduling_Fragment();
                FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null);
                transaction.add(R.id.MA_fragemt_container,fragment).commit();
            }
            else if (id == R.id.MA_EmpList_button) {
                fragment = new MA_EmpList_Fragment();
                FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null);
                transaction.add(R.id.MA_fragemt_container,fragment).commit();
            }

            else if (id == R.id.MA_Logout_button) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ManagerActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ManagerActivity.this , LoginActivity.class));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(ManagerActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ManagerActivity.this , LoginActivity.class));
                    }
                });
            }

        }

    }


    @Override
    public void MA_EMPLIST_FIListener(Uri uri) {

    }

    @Override
    public void MA_Final_FIListener(Uri uri) {

    }

    @Override
    public void MA_SCHEDULING_FIListener(Uri uri) {

    }

    @Override
    public void MA_SUMMARY_FIListener(Uri uri) {

    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    public void Test_SQL_func() {
//
//        //FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//
//        //  מוציא את המידע
//        DocumentReference docRef = db.collection("Company").document(""+currentFirebaseUser.getUid());
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        textView_helloUser.setText(document.getString("name"));
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });


    public void Test_SQL_func() {
        //FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        //  מוציא את המידע
        DocumentReference docRef = db.collection("User").document(""+currentFirebaseUser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        textView_helloUser.setText("שלום " + document.getString("name"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }




}
