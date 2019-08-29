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
// -----------------------------------------------------------------------------------------------------------------------------------

    ArrayList<String> array_checkBox_id  = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View returnView = inflater.inflate(R.layout.fragment_em__scheduling_, container, false);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        //get "id" string
        final int temp=returnView.getId();
        //final String IDname= getResources().getResourceEntryName(temp);

//
//        final CheckBox checkBox_var= (CheckBox) returnView.findViewById(R.id.checkBox_SuMo);
//        checkBox_var.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkBox_var.isChecked())
//                    Log.d(TAG, "hereeeeeeeeeeeeeeeeeeeeeeeeeee");
//            }
//        });

//            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_em__scheduling_, container, false);
//
//            for (int i=0; i < rootView.getChildCount(); i++) {
//                View v = rootView.getChildAt(i);
//                if (v instanceof CheckBox) {
//                    array_checkBox_id.add(Integer.toString( v.getId()));
//                }
//            }

        final ViewGroup rootView = (ViewGroup) returnView.findViewById(R.id.linearLayoutCompat).getRootView();
        final int childViewCount = rootView.getChildCount();

        Log.w("boaz",childViewCount+"");
        for (int i=0; i<childViewCount;i++){
            View workWithMe = rootView.getChildAt(i);

            Log.d(TAG, "i "+ i);
            Log.d(TAG, "workWithMe "+ workWithMe);
            Log.d(TAG, "childViewCount "+ childViewCount);
            if (workWithMe instanceof CheckBox)
            {
                CheckBox checked=(CheckBox) workWithMe ;
                if (checked.isChecked())
                    Log.d(TAG, "checked.isChecked() "+ checked.isChecked());
                array_checkBox_id.add(Integer.toString(temp));
            }
        }
            Log.d(TAG, "rootView.getChildCount() "+ rootView);

            Button button=(Button) returnView.findViewById(R.id.TEST_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                for (int i=0; i<childViewCount;i++){
//                    View workWithMe = rootView.getChildAt(i);
//                    Log.d(TAG, "i "+ i);
//                    Log.d(TAG, "workWithMe "+ workWithMe);
//                    Log.d(TAG, "childViewCount "+ childViewCount);
//                    if (workWithMe instanceof CheckBox)
//                    {
//                        CheckBox checked=(CheckBox) workWithMe ;
//                        if (checked.isChecked())
//                            Log.d(TAG, "checked.isChecked() "+ checked.isChecked());
//                            array_checkBox_id.add(Integer.toString(temp));
//                    }
//                }
                }
            });
            Log.d(TAG, "array_checkBox_id "+array_checkBox_id);

            return rootView;
            //return inflater.inflate(R.layout.fragment_em__scheduling_, container, false);
        }
// -----------------------------------------------------------------------------------------------------------------------------------
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
