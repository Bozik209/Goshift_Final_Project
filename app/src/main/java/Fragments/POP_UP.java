package Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boaz.big_project.R;
import com.example.boaz.big_project.ui.login.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Activitys.EmployeeActivity;
import Activitys.ManagerActivity;

public class POP_UP extends Activity {

    private static final String TAG = "POP_UP";
    private TextView textView_POPUPtext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textView_POPUPtext = findViewById(R.id.MA_RegisterPOPUPtext);
        printID();
        setContentView(R.layout.ma_pop_register);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void printID() {

        //FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        //FirebaseUser currentCompanyID = FirebaseAuth.getInstance().getCurrentUser();


                //  מוציא את המידע
        DocumentReference docRef =  db.collection("Company").document(""+currentFirebaseUser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        textView_POPUPtext.setText("שלום " + document.getString("name"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void MA_RegisterPOPUPbutton (View view) {
        finish();
    }



}
