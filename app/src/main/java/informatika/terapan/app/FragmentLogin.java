package informatika.terapan.app;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView toast_pass, daftar_text;

    EditText mail, pw;

    ImageButton img_back;
    Button login;
    Integer a = 0;

    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        daftar_text = view.findViewById(R.id.register);
        toast_pass = view.findViewById(R.id.fail_text_pass);

        mail = view.findViewById(R.id.et_email);
        pw = view.findViewById(R.id.et_pass);

        img_back = view.findViewById(R.id.img_back);
        login = view.findViewById(R.id.btn_login);

        ui_action();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (static_warga.getNama().equals("Guest")) {
                    ((menu)getActivity()).Splash();
                } else {
                    ((menu)getActivity()).Pengumuman();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mail.length() > 0 && pw.length() > 0) {
                    validitas();
                } else {
                    toast_pass.setText("input tidak lengkap harap isi semua");
                    toast_pass.setVisibility(View.VISIBLE);
                }
            }
        });


        daftar_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((menu)getActivity()).Register();
            }
        });

        return view;
    }

    private void validitas(){
        a=0;
        db.collection("warga")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("email").equals(mail.getText().toString())||document.getString("no_hp").equals(mail.getText().toString())){
                                    if(document.getString("password").equals(pw.getText().toString())){
                                        static_warga.setId(document.getId());
                                        static_warga.setNama(document.getString("nama"));
                                        static_warga.setNo(document.getString("no_hp"));
                                        static_warga.setEmail(document.getString("email"));
                                        static_warga.setRole(document.getString("role"));
                                        ((menu)getActivity()).Pengumuman();
                                        db.collection("warga").document(document.getId()).update("id_hp", Build.ID);
                                        a=1;
                                        toast_pass.setVisibility(View.INVISIBLE);
                                    }
                                }
                            }
                        }
                        //input failure
                        if(a==1){
                            //aman
                        }else{
                            a=2;
                        }
                        if(a==2){
                            toast_pass.setText("email/nomer atau password salah");
                            toast_pass.setVisibility(View.VISIBLE);
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ((menu)getActivity()).SuccessLogin();
                //Toast t = Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_LONG);
                //t.setGravity(Gravity.TOP | Gravity.CENTER, 0,0);
                //t.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ((menu)getActivity()).FailedLogin();
                //Toast t = Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_LONG);
                //t.setGravity(Gravity.TOP | Gravity.CENTER, 0,0);
                //t.show();
            }
        });
    }

    private void ui_action(){
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast_pass.setVisibility(View.GONE);
            }
        });

        pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast_pass.setVisibility(View.GONE);
            }
        });
    }
}