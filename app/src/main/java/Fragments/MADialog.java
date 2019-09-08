package Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Activitys.ManagerActivity;






public class MADialog extends AppCompatDialogFragment {


    private static final String TAG = "MADialog";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    DocumentReference docRef = db.collection("Company").document(""+currentFirebaseUser.getUid());


    private String str;

    private TextView textView_POPUPtext;

    private ClipboardManager myClipboard;
    TextView myTextView;
    private ClipData myClip;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("שלום")
                //.setMessage("לחץ להעתקת ID של החברה")
                .setMessage("" +    printID()  )
                .setPositiveButton("העתקה", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                        String text;

                        //myClip = ClipData.newPlainText("text", myTextView.getText().toString());
                        myClip = ClipData.newPlainText("text", ""+printID());
                        myClipboard.setPrimaryClip(myClip);
                        Toast.makeText(getActivity(), "Text Copied",Toast.LENGTH_SHORT).show();


                        Intent i = new Intent(getActivity(), ManagerActivity.class);
                        startActivity(i);

                    }
                });
        return builder.create();
    }



    public String printID() {

        Log.d(TAG, "AAAAAAAAAAAAAAA 0 AAAAAAAAAAAAAA" + str);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    Log.d(TAG, "BBBBBBBBBBBBBBBBBB 1 BBBBBBBBBBBBBBBBBB" + str);
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
//                        str = document.getString("group_name");
                        Log.d(TAG, "CCCCCCCCCCCCCCCCCCC 2 CCCCCCCCCCCCCCCCCCC" + document.getString("group_name").getClass().getName());
//                        textView_POPUPtext.setText(document.getString("group_name"));
//                        str = textView_POPUPtext.getText().toString();
                        str = "aaa";
                        Log.d(TAG, "DDDDDDDDDDDDDDDDDDDDD 2 DDDDDDDDDDDDDDDDDDDDD" + str);
                    }
                    Log.d(TAG, "EEEEEEEEEEEEEEEEEEE 3 EEEEEEEEEEEEEEEEEEE" + str);
                }
                Log.d(TAG, "FFFFFFFFFFFFFFF 4 FFFFFFFFFFFFFFFF" + str);
            }
        });
        Log.d(TAG, "ZZZZZZZZZZZZZZZZ" + str);
        return str;
    }
}