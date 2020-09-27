package informatika.terapan.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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

public class register extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText nama,hp,mail,pw;

    TextView fail;
    ImageButton back_btn;
    Button daftar_btn;

    Integer b=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nama =  findViewById(R.id.et_nama_r);
        hp =  findViewById(R.id.et_no_r);
        mail =  findViewById(R.id.et_email_r);
        pw =  findViewById(R.id.et_password_r);

        fail =  findViewById(R.id.toast_info);
        back_btn =  findViewById(R.id.img_back);
        daftar_btn =  findViewById(R.id.btn_reg);

        ui_action();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this,login.class);
                startActivity(intent);

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
                    Toast t = Toast.makeText(getApplicationContext(), "register success", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                    t.show();
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast t = Toast.makeText(getApplicationContext(), "register failed", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                    t.show();
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
