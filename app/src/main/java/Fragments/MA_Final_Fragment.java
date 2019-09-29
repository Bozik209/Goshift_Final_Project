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
import android.widget.Spinner;

import com.example.boaz.big_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    //private ArrayList<String> NameArray;
    private ArrayList<String> NameArray = new ArrayList<>();
    private List<String> Array_Shifts = new ArrayList<>();

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


    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    final CollectionReference docRef = db
            .collection("User");
    final Calendar calender = Calendar.getInstance(); // calender.get(Calendar.WEEK_OF_YEAR)
    final String Current_Week = String.valueOf(calender.get(Calendar.WEEK_OF_YEAR));

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

        final ViewGroup rootView = (ViewGroup) v.findViewById(R.id.fragment_ma_final).getRootView();  // מקבל את כל VIEW שיש בפרימנט
        final int childViewCount = rootView.getChildCount();
        //final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);

        for (int i = 0; i < childViewCount; i++) {
            View workWithMe = rootView.getChildAt(i);
            // chack what is Spinner
            if (workWithMe instanceof Spinner) {
                Spinner spinnerCheack = (Spinner) workWithMe;
                final String IDname = getResources().getResourceEntryName(spinnerCheack.getId());

                int IDnumber = spinnerCheack.getId();


                Log.d(TAG, "spinner_test: IDname" + IDname);


                spinnerCheack.findViewById(IDnumber);

                //NameArray=getNameArry(IDname,spinnerCheack);


                docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(final QuerySnapshot queryDocumentSnapshots) {
                        for (final DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // check if is Employee
                            if (documentSnapshot.get("isMang").toString().equals("false")) {
                                // Run again on DB
                                Log.d(TAG, "documentSnapshot.get(\"name\").toString() "+documentSnapshot.get("name").toString());

                                db.collection("User").document("" + documentSnapshot.getId())
                                        .collection("UserCompany").document("Shifts_week").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "documentSnapshot.get(\"name\").toString() "+documentSnapshot.get("name").toString());
                                            Array_Shifts.add(0, "");
                                            Array_Shifts.add(1, "");
                                            DocumentSnapshot document = task.getResult();
                                            if (document.get(Current_Week) != null) {
                                                Array_Shifts.set(0, documentSnapshot.get("name").toString());   //name
                                                Array_Shifts.set(1, document.get(Current_Week).toString());    //shifts

//                                                Log.d(TAG, ""+Array_Shifts.get(1)+".contains("+IDname+"): " +Array_Shifts.get(1).contains(IDname));
//                                                if (Array_Shifts.get(1).contains(IDname)) {
//                                                    if (!NameArray.contains(documentSnapshot.get("name").toString()))
//                                                        NameArray.add(documentSnapshot.get("name").toString());
//
//                                                }



                                                Log.d(TAG, "1 NameArray: " + NameArray);

                                            }
                                        } else {
                                            Log.w(TAG, "Error getting documents.", task.getException());
                                        }
                                    }

                                });

                            }
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });




                Log.d(TAG, "2 NameArray: " + NameArray);

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, NameArray);
                spinnerCheack.setAdapter(dataAdapter);
                NameArray.clear();
                Array_Shifts.clear();
            }
        }


        return v;
    }

    private ArrayList<String> getNameArry(final String IDname,final Spinner spinnerCheack) {
        // DB get info
        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(final QuerySnapshot queryDocumentSnapshots) {
                for (final DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    // check if is Employee
                    if (documentSnapshot.get("isMang").toString().equals("false")) {
                        // Run again on DB

                        db.collection("User").document("" + documentSnapshot.getId())
                                .collection("UserCompany").document("Shifts_week").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Array_Shifts.add(0, "");
                                    Array_Shifts.add(1, "");
                                    DocumentSnapshot document = task.getResult();
                                    if (document.get(Current_Week) != null) {
                                        Array_Shifts.set(0, documentSnapshot.get("name").toString());  //name
                                        Array_Shifts.set(1, document.get(Current_Week).toString());    //shifts

                                        Log.d(TAG, ""+Array_Shifts.get(1)+".contains("+IDname+"): " +Array_Shifts.get(1).contains(IDname));
                                        if (Array_Shifts.get(1).contains(IDname)) {
                                            if (!NameArray.contains(documentSnapshot.get("name").toString()))
                                                NameArray.add(documentSnapshot.get("name").toString());

                                        }


                                        Log.d(TAG, "1 NameArray: " + NameArray);

                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }

                        });

                    }
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, NameArray);
                spinnerCheack.setAdapter(dataAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return NameArray;
    }

    public int chackID(ViewGroup rootView, String name) {
        final int childViewCount = rootView.getChildCount();
        //Spinner spinnerCheack = (Spinner) v.findViewById();

        // give all ids
        for (int i = 0; i < childViewCount; i++) {
            View workWithMe = rootView.getChildAt(i);
            // chack what is Spinner
            if (workWithMe instanceof Spinner) {
                Spinner spinnerCheack = (Spinner) workWithMe;
                String IDname = getResources().getResourceEntryName(spinnerCheack.getId());

                int IDnumber = spinnerCheack.getId();

                if (name.equals(IDname)) {
                    Log.d(TAG, "chackID  " + "IDname: " + IDname + " name: " + name);
                    return IDnumber;
                }

            }
        }
        return 0;
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
