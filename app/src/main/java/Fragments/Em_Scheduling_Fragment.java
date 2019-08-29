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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boaz.big_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Activitys.EmployeeActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Em_Scheduling_Fragment.EM_Scheduling_FIListener} interface
 * to handle interaction events.
 * Use the {@link Em_Scheduling_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Em_Scheduling_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "EmployeeActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EM_Scheduling_FIListener mListener;

    public Em_Scheduling_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Em_Scheduling_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Em_Scheduling_Fragment newInstance(String param1, String param2) {
        Em_Scheduling_Fragment fragment = new Em_Scheduling_Fragment();
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
/** -----------------------------\/\/\/-------------------------------onCreateView-------------------------------\/\/\/-----------------------------------------*/
    // TODO: check how save the checkbox and to link to week in the spinner

    /** Global Array */
    ArrayList<String> array_checkBox_id  = new ArrayList<String>();
    ArrayList<String> array_Week  = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View returnView = inflater.inflate(R.layout.fragment_em__scheduling_, container, false);
        Chack_All_Chackbox(returnView);
        week_func(returnView);
        return returnView;
        //return inflater.inflate(R.layout.fragment_em__scheduling_, container, false);
    }

    /** Chack all the view in fragment if is chackbox and if is clicked */
    private View Chack_All_Chackbox(View returnView) {
        final ViewGroup rootView = (ViewGroup) returnView.findViewById(R.id.fragment_em__scheduling_ID).getRootView();
        final int childViewCount = rootView.getChildCount();

        //when you click on the button is enter all the ID checkbox that clicked to array_checkBox_id
        Button button=(Button) returnView.findViewById(R.id.TEST_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<childViewCount;i++){
                    View workWithMe = rootView.getChildAt(i);
                    if (workWithMe instanceof CheckBox)
                    {
                        CheckBox checked=(CheckBox) workWithMe ;
                        if (checked.isChecked())
                        {
                            if (!array_checkBox_id.contains(getResources().getResourceEntryName(checked.getId())))
                                array_checkBox_id.add(getResources().getResourceEntryName(checked.getId()));
                        }
                    }
                }
            }
        });

        Button button_ShowArry=(Button) returnView.findViewById(R.id.ShowArry_button);
        button_ShowArry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<array_checkBox_id.size() ; i++)
                {
                    Log.d(TAG, "array_checkBox_["+i+"]  = "+array_checkBox_id.get(i));
                }
            }
        });
        return returnView;
    }

    /** Get the current week and Enter inside Spinner*/
    private View week_func(View returnView) {
        //get the current week
        final Calendar calender = Calendar.getInstance();
        Log.d(TAG,"Current Week:" + calender.get(Calendar.WEEK_OF_YEAR));

        //set the spinner in Fragment
        final Spinner spinner = returnView.findViewById(R.id.Week_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set all week in array_Week
        for (int i=0;i<=52;i++)
            array_Week.add(" שבוע " + i);

        // Enter inside Spinner all week
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity(),
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

    /** ----------------------------------^^^--------------------------onCreateView----------------------------------------^^^----------------------------------*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.EM_Scheduling_FIListener(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EM_Scheduling_FIListener) {
            mListener = (EM_Scheduling_FIListener) context;
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
    public interface EM_Scheduling_FIListener {
        // TODO: Update argument type and name
        void EM_Scheduling_FIListener(Uri uri);
    }
}
