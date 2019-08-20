package Activitys;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.boaz.big_project.R;

import Fragments.EM_Final_Fragment;
import Fragments.EM_Summary_Fragment;
import Fragments.Em_Scheduling_Fragment;

public class EmployeeActivity extends AppCompatActivity implements Em_Scheduling_Fragment.EM_Scheduling_FIListener ,
        EM_Summary_Fragment.EM_Summary_FIListener ,
        EM_Final_Fragment.EM_Final_FIListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
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

    public void Fragment_move(View view) {
        // fregment_container = all the fragment will be on him

        FragmentManager manager= getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragemt_container);

        if(fragment==null) //if it the first time to call the first fragmet
        {
            fragment = new Em_Scheduling_Fragment();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.add(R.id.fragemt_container,fragment,"0").commit();
        }
    }
}
