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
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.boaz.big_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EM_Final_Fragment.EM_Final_FIListener} interface
 * to handle interaction events.
 * Use the {@link EM_Final_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EM_Final_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EM_Final_FIListener mListener;

    public EM_Final_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EM_Final_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EM_Final_Fragment newInstance(String param1, String param2) {
        EM_Final_Fragment fragment = new EM_Final_Fragment();
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

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_em__final_, container, false);
        //Fiil_Shift(v);



        final TextView textview = (TextView) v.findViewById(R.id.SuMo);
        Log.d(TAG, "1textview.getText(): "+textview.getText());
        textview.setText("test");
        Log.d(TAG, "2textview.getText(): "+textview.getText());


        return inflater.inflate(R.layout.fragment_em__final_, container, false);
    }

    private void Fiil_Shift(final View v) {
        final ViewGroup rootView = (ViewGroup) v.findViewById(R.id.fragment_em_final).getRootView();  // מקבל את כל VIEW שיש בפרימנט
        final int childViewCount = rootView.getChildCount();


        db.collection("Test").document("Final_shifts")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "documentSnapshot "+documentSnapshot);
                Log.d(TAG, "documentSnapshot "+documentSnapshot.getData().size());



                for (int i = 0; i < childViewCount; i++) {
                    View workWithMe = rootView.getChildAt(i);
                    // chack what is Spinner
                    if (workWithMe instanceof TextView) {
                        TextView textViewCheack = (TextView) workWithMe;
                        String IDname = getResources().getResourceEntryName(textViewCheack.getId());
                        int IDnumber = textViewCheack.getId();
                        Log.d(TAG, "documentSnapshot.get("+IDname+") "+documentSnapshot.get(IDname));
//                        Log.d(TAG, "documentSnapshot.get("+IDname+").equals(null) "+documentSnapshot.get(IDname).equals(null));

                        if (!IDname.startsWith("text",0))
                        {
                            Log.d(TAG, "2IDname " + IDname);
                            Log.d(TAG, "2textViewCheack.getText() " + textViewCheack.getText());
//                            textViewCheack.setText(documentSnapshot.get(IDname).toString());
                            TextView textView = (TextView) v.findViewById(IDnumber);
                            Log.d(TAG, "4textView: "+textView);
                            textView.setText("Ffff");

                        }
                    }
                }


            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.EM_Final_FIListener(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EM_Final_FIListener) {
            mListener = (EM_Final_FIListener) context;
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
    public interface EM_Final_FIListener {
        // TODO: Update argument type and name
        void EM_Final_FIListener(Uri uri);
    }
}
