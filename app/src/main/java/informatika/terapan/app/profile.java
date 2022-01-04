package informatika.terapan.app;

import android.content.Intent;
import android.os.Build;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class profile extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText nama,hp,mail;

    ImageButton back_btn;
    Button log_out;
    TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nama =  findViewById(R.id.et_nama_r);
        hp =  findViewById(R.id.et_no_r);
        mail =  findViewById(R.id.et_email_r);

        back_btn =  findViewById(R.id.img_back);
        log_out =  findViewById(R.id.logout);
        save = findViewById(R.id.save_profil);

        load_user();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (static_warga.getNama().equals("Guest")) {
                    Intent intent = new Intent(profile.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(profile.this, Jadwal_ronda.class);
                    startActivity(intent);
                }

            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("warga").document(static_warga.getId()).update("id_hp", "").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast t = Toast.makeText(getApplicationContext(), "Log Out success", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                        t.show();
                        static_warga.setRole("");
                        static_warga.setEmail("");
                        static_warga.setNo("");
                        static_warga.setNama("");
                        static_warga.setId("");
                        Intent intent = new Intent(profile.this,MainActivity.class);
                        startActivity(intent);
                    }
                });


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    private void load_user(){
        nama.setText(static_warga.getNama());
        hp.setText(static_warga.getNo());
        mail.setText(static_warga.getEmail());
    }

    private void update(){
        db.collection("pengumuman")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getId().equals(static_warga.getId())){
                                    db.collection("warga").document(document.getId()).update("nama",nama.getText().toString());
                                    db.collection("warga").document(document.getId()).update("no_hp",hp.getText().toString());
                                    db.collection("warga").document(document.getId()).update("email",mail.getText().toString());
                                }
                            }
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Toast t = Toast.makeText(getApplicationContext(), "save success", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                t.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast t = Toast.makeText(getApplicationContext(), "save failed", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                t.show();
            }
        });
    }
}
