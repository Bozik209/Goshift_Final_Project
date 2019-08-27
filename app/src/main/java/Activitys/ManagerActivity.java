package Activitys;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.boaz.big_project.R;
import com.example.boaz.big_project.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

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
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
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
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        FirebaseAuth.getInstance().signOut();
//                        Toast.makeText(ManagerActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(ManagerActivity.this , LoginActivity.class));
//                    }
//                });
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

}
