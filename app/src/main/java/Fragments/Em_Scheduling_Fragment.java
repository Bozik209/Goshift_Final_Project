package Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.local.ReferenceSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    // TODO: How remember the choice

    /**
     * Global Array
     */
    ArrayList<String> array_Week = new ArrayList<String>();
    Map<String, ArrayList> Map_array_id = new HashMap<String, ArrayList>();  // MAP עם זה הפיירביס עובד

    // ממלא את החודשים
    public void full_week(Map<String, ArrayList> Map_array_id) {
        for (int i = 0; i <= 52; i++)
            Map_array_id.put("" + i, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View returnView = inflater.inflate(R.layout.fragment_em__scheduling_, container, false);
        full_week(Map_array_id);
        week_func(returnView);
        sendButton(returnView);
        return returnView;
        //return inflater.inflate(R.layout.fragment_em__scheduling_, container, false);
    }

    private View sendButton(View returnView) {
        final ViewGroup rootView = (ViewGroup) returnView.findViewById(R.id.fragment_em__scheduling_ID).getRootView();  // מקבל את כל VIEW שיש בפרימנט
        final int childViewCount = rootView.getChildCount();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Calendar calender = Calendar.getInstance();

        final Spinner spinner = returnView.findViewById(R.id.Week_spinner);

        // מקבל את הניתוב
        final DocumentReference docRef = db
                .collection("User").document("" + user.getUid())
                .collection("UserCompany").document("Shifts_week");

        // ברגע שמעברים חודש אז הוא טוען את כל הנתונים ושם את המשמרות לפי חודש
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + spinner.getSelectedItem());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Log.d(TAG, "spinner.getSelectedItem().toString() " + spinner.getSelectedItem().toString());

                                ArrayList<String> arrayList = (ArrayList<String>) document.get(spinner.getSelectedItem().toString());
                                //Do what you need to do with your ArrayList
                                Log.d(TAG, "arrayList s: " + arrayList);
                                // מאפס את הכול אם עוברים לחודש שלא מילינו בו משמרות
                                for (int i = 0; i < childViewCount; i++) {
                                    View workWithMe = rootView.getChildAt(i);
                                    if (workWithMe instanceof CheckBox) {
                                        CheckBox checked = (CheckBox) workWithMe;
                                        checked.setChecked(false);
                                    }
                                }
                                // זה בודק שהמערך לא רק כי אם הוא רק זה קורס
                                if (arrayList != null) {
                                    //מאפס את הכול
                                    for (int i = 0; i < childViewCount; i++) {
                                        View workWithMe = rootView.getChildAt(i);
                                        if (workWithMe instanceof CheckBox) {
                                            CheckBox checked = (CheckBox) workWithMe;
                                            checked.setChecked(false);
                                        }
                                    }
                                    // רץ על המערך מהבסיס נתונים ומסמן את המשמרות
                                    for (String s : arrayList) {
                                        for (int i = 0; i < childViewCount; i++) {
                                            View workWithMe = rootView.getChildAt(i);
                                            if (workWithMe instanceof CheckBox) {
                                                CheckBox checked = (CheckBox) workWithMe;
                                                String IDname = getResources().getResourceEntryName(checked.getId());
                                                if (s.equals(IDname))
                                                    checked.setChecked(true);
                                            }

                                        }
                                    }
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayList Array_Shifts = new ArrayList(); // יכיל את כל הcheckbox (המשמרות)


        /** Chack all the view in fragment if is chackbox and if is clicked */
        //when you click on the button is enter all the ID checkbox that clicked to array_id

        Button button = (Button) returnView.findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // עובר על כל checkbox
                Array_Shifts.clear();
                for (int i = 0; i < childViewCount; i++) {
                    View workWithMe = rootView.getChildAt(i);
                    if (workWithMe instanceof CheckBox) {
                        CheckBox checked = (CheckBox) workWithMe;
                        // בודק אם הוא בחר משמרת ומוסיף
                        if (checked.isChecked()) {
                            // שלא יכניס אותה משמרת פעמיים
                            if (!Array_Shifts.contains(getResources().getResourceEntryName(checked.getId()))) {
                                Array_Shifts.add(getResources().getResourceEntryName(checked.getId()));
                                Toast.makeText(getActivity(), "משמרות נשלחו לאישור", Toast.LENGTH_LONG).show();


                            }
                        }
                    }
                }

                /** Enter to the select week the select shift  **/
                Map_array_id.put(spinner.getSelectedItem().toString(), new ArrayList(Array_Shifts));


                /** Enter data to path **/
                // מעדכן רק את השבוע הספציפי וככה הוא לא דורס את הכול
//                db.collection("User").document("1hexykJT5uTYoZZILjFmPBfjhKE3")
//                        .collection("UserCompany").document("36f05C7PhdGMXZL0cEbc")
//                DocumentReference userRef = db.document("company/users");


                db.collection("User").document("" + user.getUid())
                        .collection("UserCompany").document("Shifts_week")
                        .update(spinner.getSelectedItem().toString(), new ArrayList(Array_Shifts)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + user.getUid());
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                // מעדכן רק את השבוע הספציפי אבל דורס גם את הכול
//                db.collection("User").document("1hexykJT5uTYoZZILjFmPBfjhKE3")
//                        .collection("UserCompany").document("36f05C7PhdGMXZL0cEbc")
//                        .set(Map_array_id).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + user.getUid());
//                    }
//                })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "Error adding document", e);
//                            }
//                        });
            }
        });


        /** Button the clear all selected checkbox **/
        Button Clear_button = (Button) returnView.findViewById(R.id.Clear_button);
        Clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < childViewCount; i++) {
                    View workWithMe = rootView.getChildAt(i);
                    if (workWithMe instanceof CheckBox) {
                        CheckBox checked = (CheckBox) workWithMe;
                        checked.setChecked(false);
                    }
                }
                //Map_array_id.clear();
                Toast.makeText(getActivity(), "אין משמרות!", Toast.LENGTH_LONG).show();
                Array_Shifts.clear();
            }
        });
        return returnView;
    }


    /**
     * Get the current week and Enter inside Spinner
     */
    private View week_func(View returnView) {
        //get the current week
        final Calendar calender = Calendar.getInstance();

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
