package informatika.terapan.app;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class Jadwal_ronda extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView nama;

    ImageButton pengumuman_btn,menu_btn;

    NavigationView nav;

    BottomNavigationView bnav;

    int nav_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_ronda);

        nama =  findViewById(R.id.tv_nama_p);

        menu_btn =  findViewById(R.id.img_person);
        nav = findViewById(R.id.nav_akun);
        bnav = findViewById(R.id.b_nav);

        Pengumuman();
        nav.getMenu().getItem(0).setTitle(static_warga.getNama());
        ui_role();

        //toolbar nav
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

        //menu nav profile
        nav.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (static_warga.getRole().equals("3")){
                    ((menu)getApplicationContext()).Login();
                    //Intent intent = new Intent(Jadwal_ronda.this, menu.class);
                    //startActivity(intent);
                }else {
                    Intent intent = new Intent(Jadwal_ronda.this, profile.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        //menu nav login/log out
        nav.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (static_warga.getRole().equals("3")){
                    ((menu)getApplicationContext()).Login();
                    //Intent intent = new Intent(Jadwal_ronda.this, menu.class);
                    //startActivity(intent);
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
                            Intent intent = new Intent(Jadwal_ronda.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });

                }
                return false;
            }
        });

        bnav.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Pengumuman();
                //Intent intent = new Intent(Jadwal_ronda.this,pengumuman.class);
                //startActivity(intent);
                return false;
            }
        });

        bnav.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                JadwalRonda();
                //Intent intent = new Intent(Jadwal_ronda.this,pengumuman.class);
                //startActivity(intent);
                return false;
            }
        });

    }

    public void Pengumuman(){
        nama.setText("Pengumuman");
        loadFragment(new FragmentPengumuman());
    }

    public void JadwalRonda(){
        nama.setText("Jadwal Ronda");
        loadFragment(new FragmentJadwalRonda());
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("null").commit();
            return true;
        }
        return false;
    }

    public void lihatjr(){
        Intent intent = new Intent(Jadwal_ronda.this,lihat_jr.class);
        startActivity(intent);
    }

    private void ui_role(){
        if (static_warga.getNama().equals("Guest")){
            nav.getMenu().getItem(0).setVisible(false);
            nav.getMenu().getItem(1).setTitle("Login");
        }else{
            nav.getMenu().getItem(0).setVisible(true);
            nav.getMenu().getItem(1).setTitle("Log Out");
        }
    }

    public void LoadPengumumanFailed(){
        Toast t = Toast.makeText(getApplicationContext(), "load pengumuman failed", Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
        t.show();
    }

    public void SavePengumumanSuccess(){
        Toast t = Toast.makeText(getApplicationContext(), "save pengumuman success", Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
        t.show();
    }

    public void SavePengumumanFailed(){
        Toast t = Toast.makeText(getApplicationContext(), "save pengumuman failed", Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
        t.show();
    }


}
