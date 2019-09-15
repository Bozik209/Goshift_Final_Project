package Fragments;

import android.annotation.SuppressLint;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.boaz.big_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MA_Final_Fragment.MA_Final_FIListener} interface
 * to handle interaction events.
 * Use the {@link MA_Final_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MA_Final_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MA_Final_FIListener mListener;

    public MA_Final_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MA_Final_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MA_Final_Fragment newInstance(String param1, String param2) {
        MA_Final_Fragment fragment = new MA_Final_Fragment();
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
     * -----------------------------\/\/\/-------------------------------onCreateView-------------------------------\/\/\/-----------------------------------------
     */

    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    //static final ArrayList checked_getId = new ArrayList(); // יכיל את כל הcheckbox (המשמרות)
    static final List<String> checked_getId = new ArrayList<String>();
    final CollectionReference docRef = db
            .collection("User");
    //final CollectionReference docRef1 = db.collection("/User/wK9K3p7mtrbwlTc2VbmkduKoxkm2/UserCompany/בועז-המלך");

    //final DocumentReference userRefNEW = db.document("User/wK9K3p7mtrbwlTc2VbmkduKoxkm2/UserCompany/בועז-המלך");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ma__final_, container, false);

        spinner_test(v);

        return v;
        //return inflater.inflate(R.layout.fragment_ma__final_, container, false);
    }

    private View spinner_test(final View v) {
        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(final QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    // check if is Employee
                    if (documentSnapshot.get("isMang").toString().equals("false")) {
                        // Run again on DB

                        db.collection("User").document("" + documentSnapshot.getId())
                                .collection("UserCompany").document("בועז-המלך").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    Log.d(TAG, "document.getData => " + document.getData());
                                    Log.d(TAG, "document.getData => " + document.get("38"));
                                    if (document.get("38") != null) {
                                            checked_getId.add(document.get("38").toString());
                                        }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                                Send2Spinner(checked_getId, v);
                            }
                        });

//                        db.collection("User")
//                                .document("" + documentSnapshot.getId())
//                                .collection("UserCompany").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                                    Log.d(TAG, "documentSnapshot"+documentSnapshot);
//                                    // Run on all week
//                                    if (documentSnapshot.get("37") != null) {
//                                            checked_getId.add(documentSnapshot.get("37").toString());
//                                        }
//                                }
//                                // Sand to Spinner
//                                Send2Spinner(checked_getId, v);
//                            }
//                        });
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


//        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//
//                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                    // check if is Employee
//                    if (documentSnapshot.get("isMang").toString().equals("false")) {
//                        // Run again on DB
//                        db.collection("User")
//                                .document("" + documentSnapshot.getId())
//                                .collection("UserCompany").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                                    Log.d(TAG, "documentSnapshot"+documentSnapshot);
//                                    // Run on all week
//                                    if (documentSnapshot.get("37") != null) {
//                                            checked_getId.add(documentSnapshot.get("37").toString());
//                                        }
//                                }
//                                // Sand to Spinner
//                                Send2Spinner(checked_getId, v);
//                            }
//                        });
//                    }
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
        return v;
    }

    public void Send2Spinner(List<String> Checked_getId, View v) {
//        Log.d(TAG, "Send2Spinner: " + Checked_getId);
//        Log.d(TAG, "Send2Spinner3: " + Checked_getId.size());
//        Log.d(TAG, "Send2Spinner4: " + Checked_getId.toArray());
//        Log.d(TAG, "Send2Spinner5: " + Checked_getId.toArray().length);
//        Log.d(TAG, "Send2Spinner6: " + Checked_getId.toArray().toString());

        Spinner spinner = (Spinner) v.findViewById(R.id.spinner_MA_test);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Checked_getId);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }


    /**
     * ----------------------------------^^^--------------------------onCreateView----------------------------------------^^^----------------------------------
     */

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.MA_Final_FIListener(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MA_Final_FIListener) {
            mListener = (MA_Final_FIListener) context;
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
    public interface MA_Final_FIListener {
        // TODO: Update argument type and name
        void MA_Final_FIListener(Uri uri);
    }
}
