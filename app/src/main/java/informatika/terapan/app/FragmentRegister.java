package informatika.terapan.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegister#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegister extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText nama,hp,mail,pw;

    TextView fail;
    ImageButton back_btn;
    Button daftar_btn;

    Integer b=0;

    public FragmentRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegister.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegister newInstance(String param1, String param2) {
        FragmentRegister fragment = new FragmentRegister();
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
        View view =inflater.inflate(R.layout.fragment_register, container, false);

        nama =  view.findViewById(R.id.et_nama_r);
        hp =  view.findViewById(R.id.et_no_r);
        mail =  view.findViewById(R.id.et_email_r);
        pw =  view.findViewById(R.id.et_password_r);

        fail =  view.findViewById(R.id.toast_info);
        back_btn =  view.findViewById(R.id.img_back);
        daftar_btn =  view.findViewById(R.id.btn_reg);

        ui_action();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((menu)getActivity()).Login();
            }
        });


        daftar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((nama.length()>0)&&(mail.length()>0)&&(hp.length()>0)&&(pw.length()>0)) {
                    check_data();
                    //daftar_btn.setText("value data = "+String.valueOf(b));
                }else{
                    fail.setText("input tidak lengkap harap isi semua");
                    fail.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    private void check_data(){
        b=0;
        db.collection("warga")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("email").equals(mail.getText().toString())){
                                    fail.setText("email yang anda masukan sudah terpakai");
                                    b=1;
                                    fail.setVisibility(View.VISIBLE);
                                }

                                if(document.getString("no_hp").equals(hp.getText().toString())){
                                    fail.setText("nomor yang anda masukan sudah terpakai");
                                    b=2;
                                    fail.setVisibility(View.VISIBLE);
                                }

                                if(document.getString("nama").equals(nama.getText().toString())){
                                    fail.setText("nama yang anda masukan sudah terpakai");
                                    b=3;
                                    fail.setVisibility(View.VISIBLE);
                                }

                            }
                        } else {
                            fail.setText("tidak terhubung dengan database");
                            fail.setVisibility(View.VISIBLE);
                        }
                        add_data();
                    }

                });
    }


    private void add_data(){
        if (b<1||b>3){
            Map<String, Object> person = new HashMap<>();
            person.put("nama", nama.getText().toString());
            person.put("no_hp", hp.getText().toString());
            person.put("email", mail.getText().toString());
            person.put("password", pw.getText().toString());
            person.put("role", "2");
            person.put("id_hp", "");

            db.collection("warga").add(person).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    ((menu)getActivity()).RegisterSuccess();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ((menu)getActivity()).RegisterFailed();
                }
            });
        }
    }

    private void ui_action(){
        nama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fail.setVisibility(View.GONE);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fail.setVisibility(View.GONE);
            }
        });

        hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fail.setVisibility(View.GONE);
            }
        });

        pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fail.setVisibility(View.GONE);
            }
        });
    }
}