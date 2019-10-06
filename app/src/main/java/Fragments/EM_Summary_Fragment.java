package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.boaz.big_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EM_Summary_Fragment.EM_Summary_FIListener} interface
 * to handle interaction events.
 * Use the {@link EM_Summary_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EM_Summary_Fragment extends Fragment {

    private static final String TAG = "EmployeeActivity";

    private TextView textView_UserHourlyRate;
    private TextView textView_UserSalary;
    private TextView textView_CountWorkHours;
    private int cnt_of_Shift=0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DocumentReference docRef = db.collection("User").document("" + currentFirebaseUser.getUid());
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private double hourlyrate;
    private double intSalary;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EM_Summary_FIListener mListener;

    public EM_Summary_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EM_Summary_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EM_Summary_Fragment newInstance(String param1, String param2) {
        EM_Summary_Fragment fragment = new EM_Summary_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * ----------------------------------^^^--------------------------onCreateView----------------------------------------^^^----------------------------------
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_em__summary_, container, false);
        Fiil_week(v);
        textView_UserHourlyRate = (TextView) v.findViewById(R.id.EM_HourlyRate);         // תעריף לשעה
        getHourlyRate();
        textView_CountWorkHours = (TextView) v.findViewById(R.id.EM_countWorkHours);  // מספר שעות
        textView_UserSalary = (TextView) v.findViewById(R.id.EM_salary);    // שכר
        getHourFromDB(v);

        intSalary = hourlyrate * cnt_of_Shift;
        textView_UserSalary.setText(intSalary +"₪");

        return v;
    }



    public void getHourlyRate() {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        hourlyrate = document.getDouble("HourlyRate").doubleValue();

                        textView_UserHourlyRate.setText("" + hourlyrate);


                        int intCnt = Integer.parseInt(textView_CountWorkHours.getText().toString());


                        intSalary = hourlyrate * cnt_of_Shift;
                        textView_UserSalary.setText("" + intSalary);
                        Log.d(TAG, "intSalary: "+intSalary);
                    }
                }
            }
        });
    }
    private void getHourFromDB(View returnView) {
        final Calendar calender = Calendar.getInstance(); // calender.get(Calendar.WEEK_OF_YEAR)
        final String Current_Week = String.valueOf(calender.get(Calendar.WEEK_OF_YEAR));
        final Spinner spinner = returnView.findViewById(R.id.Week_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                db.collection("User").document("" + user.getUid())
                        .collection("UserCompany").document("Shifts_week")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                // The count of Shift the emp work
                                Log.d(TAG, "spinner.getSelectedItem(): "+spinner.getSelectedItem());

//                ArrayList Work_Shifts_Arry = (ArrayList) documentSnapshot.get(Current_Week);
                                ArrayList Work_Shifts_Arry = (ArrayList) documentSnapshot.get(spinner.getSelectedItem().toString());


//                                Log.d(TAG, "Shifts_Arry.size: "+Work_Shifts_Arry.size());
                                Log.d(TAG, "Shifts_Arry: "+Work_Shifts_Arry);
                                Log.d(TAG, "Shifts_Arry.!=null: "+(Work_Shifts_Arry!=null));

                                if (Work_Shifts_Arry!=null) {
                                    cnt_of_Shift=Work_Shifts_Arry.size()*8;
                                }
                                else{
                                    cnt_of_Shift=0;
                                    intSalary=0;
                                    cnt_of_Shift=0;
                                }

                                textView_CountWorkHours.setText(""+cnt_of_Shift);

                                intSalary = hourlyrate * cnt_of_Shift;
                                textView_UserSalary.setText("" + intSalary);
                                Log.d(TAG, "intSalary: "+intSalary +"₪");
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    private View Fiil_week(View returnView) {
        //get the current week
        final Calendar calender = Calendar.getInstance();
        ArrayList<String> array_Week = new ArrayList<String>();

        //set the spinner in Fragment
        final Spinner spinner = returnView.findViewById(R.id.Week_spinner);

        // Set all week in array_Week
        for (int i = 0; i <= 52; i++)
            array_Week.add("" + i);

        // Enter inside Spinner all week
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, array_Week);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setGravity(Gravity.CENTER);

        // setSelection = Jump directly to a specific item in the adapter data
        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(calender.get(Calendar.WEEK_OF_YEAR));
            }
        });


        return returnView;
    }
    /**
     * ----------------------------------^^^--------------------------onCreateView----------------------------------------^^^----------------------------------
     */
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.EM_Summary_FIListener(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EM_Summary_FIListener) {
            mListener = (EM_Summary_FIListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface EM_Summary_FIListener {
        // TODO: Update argument type and name
        void EM_Summary_FIListener(Uri uri);
    }




}