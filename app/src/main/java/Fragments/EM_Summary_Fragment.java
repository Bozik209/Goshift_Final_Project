package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.boaz.big_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


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
    private  TextView textView_CountWorkHours;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    DocumentReference docRef = db.collection("User").document(""+currentFirebaseUser.getUid());
    private int hourlyrate;
    private int cntHours;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_em__summary_, container, false);

        textView_UserHourlyRate = (TextView) v.findViewById(R.id.EM_HourlyRate);
        getHourlyRate();
        textView_CountWorkHours=(TextView) v.findViewById(R.id.EM_countWorkHours);
        textView_UserSalary = (TextView) v.findViewById(R.id.EM_salary);


        return v;
    }

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


    public void getHourlyRate() {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        hourlyrate = document.getLong("HourlyRate").intValue();
                        
                        textView_UserHourlyRate.setText(""+hourlyrate);

                        cntHours = document.getLong("countWorkHours").intValue();

                        textView_CountWorkHours.setText(""+cntHours);

                        int intCnt=Integer.parseInt(textView_CountWorkHours.getText().toString());
                        int intSalary= hourlyrate*intCnt;
                        textView_UserSalary.setText(""+intSalary);

                    }
                }
            }
        });
    }

}