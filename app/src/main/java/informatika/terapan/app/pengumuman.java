package informatika.terapan.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
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


public class pengumuman extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView p_text,nama;

    ImageButton jr_btn,menu_btn;

    NavigationView nav;
    BottomNavigationView bnav;
    EditText et_input;
    Button save;

    int nav_temp;

    Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengumuman);

        p_text =  findViewById(R.id.tv);
        nama =  findViewById(R.id.tv_nama_p);

        menu_btn =  findViewById(R.id.img_person);

        save =  findViewById(R.id.btn_save);
        et_input =  findViewById(R.id.input);
        nav = findViewById(R.id.nav_akun);
        bnav = findViewById(R.id.b_nav);

        nav.getMenu().getItem(0).setTitle(static_warga.getNama());
        ui_role();
        load();

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nav_temp==1){
                    nav.setVisibility(View.GONE);
                    nav_temp=0;
                }else{
                    nav.setVisibility(View.VISIBLE);
                    nav_temp=1;
                }
            }
        });

        nav.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (static_warga.getRole().equals("3")){
                    Intent intent = new Intent(pengumuman.this, login.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(pengumuman.this, profile.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        nav.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (static_warga.getRole().equals("3")){
                    Intent intent = new Intent(pengumuman.this, login.class);
                    startActivity(intent);
                }else {
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
                            Intent intent = new Intent(pengumuman.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });

                }
                return false;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        bnav.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(pengumuman.this,Jadwal_ronda.class);
                startActivity(intent);
                return false;
            }
        });
    }

    private void load(){
        db.collection("pengumuman")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                p_text.setText(document.getString("text"));
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast t = Toast.makeText(getApplicationContext(), "load pengumuman failed", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                t.show();
            }
        });
    }

    private void update(){
        db.collection("pengumuman").document("1").update("text",et_input.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast t = Toast.makeText(getApplicationContext(), "save pengumuman success", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                t.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast t = Toast.makeText(getApplicationContext(), "save pengumuman failed", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                t.show();
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                load();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void ui_role(){
        if (static_warga.getRole().equals("1")){
            save.setVisibility(View.VISIBLE);
            et_input.setVisibility(View.VISIBLE);
        }else{
            save.setVisibility(View.GONE);
            et_input.setVisibility(View.GONE);
        }

        if (static_warga.getNama().equals("Guest")){
            nav.getMenu().getItem(1).setTitle("Login");
            nav.getMenu().getItem(0).setVisible(false);
            //login_text.setText("Login");
        }else{
            nav.getMenu().getItem(1).setTitle("Log Out");
            nav.getMenu().getItem(0).setVisible(true);
            //login_text.setText("Log Out");
        }
    }

}
