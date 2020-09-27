package informatika.terapan.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
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

public class login extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    TextView toast_pass, daftar_text;

    EditText mail, pw;

    ImageButton img_back;
    Button login;
    Integer a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        daftar_text = findViewById(R.id.register);
        toast_pass = findViewById(R.id.fail_text_pass);

        mail = findViewById(R.id.et_email);
        pw = findViewById(R.id.et_pass);

        img_back = findViewById(R.id.img_back);
        login = findViewById(R.id.btn_login);

        ui_action();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (static_warga.getNama().equals("Guest")) {
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(login.this, pengumuman.class);
                    startActivity(intent);
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
                Intent intent = new Intent(login.this,register.class);
                startActivity(intent);
            }
        });

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
                                        Intent intent = new Intent(login.this,pengumuman.class);
                                        startActivity(intent);
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
                Toast t = Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_LONG);
                t.setGravity(Gravity.TOP | Gravity.CENTER, 0,0);
                t.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast t = Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_LONG);
                t.setGravity(Gravity.TOP | Gravity.CENTER, 0,0);
                t.show();
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
