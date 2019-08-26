package Activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boaz.big_project.R;
import com.example.boaz.big_project.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";
    private TextView textView_helloUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView_helloUser = findViewById(R.id.hello_User);

        // Firebase Log out
        // -----------------------------------------------------------------
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(MainActivity.this ,LoginActivity.class)); } }};
        // -----------------------------------------------------------------


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_Employees) {
            Toast.makeText(MainActivity.this, "EmployeeActivity", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this ,EmployeeActivity.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.action_logout) {
            Toast.makeText(MainActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this ,LoginActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Global array
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> array_checkBox_id  = new ArrayList<String>();

    public void CheckBox_func(View view) {
        //get "id" string
        int temp=view.getId();
        String IDname= getResources().getResourceEntryName(temp);

        //checked if CheckBox isChecked and add it to  array
        boolean checked=((CheckBox) view).isChecked();
        if (checked)
        {
            array_checkBox_id.add(IDname);
        }
    }


    // send the mark chackbox
    public void Send_info(View view) {
        //הוספת כל ה ID לתוך מערך
        //ואז לרוץ על כל המערך
        Spinner s = (Spinner) findViewById(R.id.spinner4);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_checkBox_id);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

//          משיכת מידע לתוך הספינר
//        db.collection("0R1DGYG")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, );
//                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                s.setAdapter(adapter);
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });

    }

    //create random number
    //use for ID_group
    public void random(View view) {
        String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random RANDOM = new Random();

        StringBuilder sb = new StringBuilder(7);

        for (int i = 0; i < 7; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }
        String RandomID=sb.toString();
        Toast.makeText(MainActivity.this, "Random: "+RandomID, Toast.LENGTH_LONG).show();


        //create collection named the random number
        //it will be the id group
        db.collection(""+sb.toString());

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();

        user.put("days", array_checkBox_id);


        // date
        Date currentTime = Calendar.getInstance().getTime();
        user.put("date", currentTime);

        // Add a new document with a generated ID
        db.collection(""+sb.toString())
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error adding document"+e, Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void Test_SQL_func(View view) {
        //FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        // Create a new user with a first, middle, and last name
        Map<String, Object> user = new HashMap<>();
        user.put("id", currentFirebaseUser.getUid());
        user.put("name", "111   bob");
        user.put("mail", currentFirebaseUser.getEmail());
        user.put("password", 123456);
        user.put("isMang", true);

        //  זה יוצר משתמש בתוך USER עם הID שלו
        db.collection("User").document(""+currentFirebaseUser.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

        //  זה מעדכן את התוכן
        db.collection("User").document(""+currentFirebaseUser.getUid())
                .update(user);

        //  מוציא את המידע
        DocumentReference docRef = db.collection("User").document(""+currentFirebaseUser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        //--------------------------------------

                        textView_helloUser.setText("HELLO " + document.getString("name")+"\n"+"isMang "+document.getBoolean("isMang"));

                        //--------------------------------------

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Log.d(TAG, "id:       " + document.getString("id"));
                        Log.d(TAG, "isMang    " + document.getBoolean("isMang"));
                        Log.d(TAG, "mail      " + document.getString("mail"));
                        Log.d(TAG, "name      " + document.getString("name"));
                        Log.d(TAG, "password  " + document.get("password"));

                        Toast.makeText(MainActivity.this, "Hello " + document.getString("name") , Toast.LENGTH_SHORT).show();

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
