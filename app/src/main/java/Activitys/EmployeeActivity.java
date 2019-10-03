package Activitys;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.boaz.big_project.R;
import com.example.boaz.big_project.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import Fragments.EM_Final_Fragment;
import Fragments.EM_Summary_Fragment;
import Fragments.Em_Scheduling_Fragment;

public class EmployeeActivity extends AppCompatActivity implements
        Em_Scheduling_Fragment.EM_Scheduling_FIListener ,
        EM_Summary_Fragment.EM_Summary_FIListener ,
        EM_Final_Fragment.EM_Final_FIListener {

    private static final String TAG = "EmployeeActivity";
    private TextView textView_helloUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        TypefaceProvider.registerDefaultIconSets();


        textView_helloUser = findViewById(R.id.hello_User);
        Test_SQL_func();

//        Spinner spinner = findViewById(R.id.EM_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EmployeeActivity.this,
//                R.array.number_test, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setGravity(Gravity.CENTER);
    }

    public void Fragment_move(View view) {
        // fregment_container = all the fragment will be on him
        FragmentManager manager= getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.EM_fragemt_container);

        if(fragment==null) //if it the first time to call the first fragmet
        {
            int id = view.getId();

            if (id == R.id.EM_Summary_button) {
                fragment = new EM_Summary_Fragment();
                FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null);
                transaction.add(R.id.EM_fragemt_container,fragment).commit();
            }
            else if (id == R.id.EM_Final_button) {
                fragment = new EM_Final_Fragment();
                FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null);
                transaction.add(R.id.EM_fragemt_container,fragment).commit();
            }
            else if (id == R.id.EM_Scheduling_button) {
                fragment = new Em_Scheduling_Fragment();
                FragmentTransaction transaction = manager.beginTransaction().addToBackStack(null);
                transaction.add(R.id.EM_fragemt_container,fragment).commit();
            }
            else if (id == R.id.EM_Logout_button) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(EmployeeActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EmployeeActivity.this , LoginActivity.class));
            }

        }

    }
    public void func(View v)
    {

    }
    @Override
    public void EM_Scheduling_FIListener(Uri uri) {

    }

    @Override
    public void EM_Summary_FIListener(Uri uri) {

    }

    @Override
    public void EM_Final_FIListener(Uri uri) {

    }



    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
