package Activitys;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.boaz.big_project.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
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

    public void Fragment_move(View view) {
        // fregment_container = all the fragment will be on him
        FragmentManager manager= getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.ma_fragemt_container);

        if(fragment==null) //if it the first time to call the first fragmet
        {
            fragment = new Em_Scheduling_Fragment();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.add(R.id.ma_fragemt_container,fragment).commit();
        }

    }
}
