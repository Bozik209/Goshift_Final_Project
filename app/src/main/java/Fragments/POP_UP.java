package Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.boaz.big_project.R;
import com.example.boaz.big_project.ui.login.RegisterActivity;

public class POP_UP extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ma_pop_register);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
    }

    public void MA_RegisterPOPUPbutton (View view) {

        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);

    }



}
