package informatika.terapan.app;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Handler h;
    private Runnable r;

    int a=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r = new Runnable() {
            @Override
            public void run() {
                autologin();
            }
        };
        h = new Handler();

        h.postDelayed(r,2000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (h != null && r != null){
            h.removeCallbacks(r);
        }
    }

    public void autologin(){
        db.collection("warga")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("id_hp").equals(Build.ID)){
                                    static_warga.setId(document.getId());
                                    static_warga.setNama(document.getString("nama"));
                                    static_warga.setNo(document.getString("no_hp"));
                                    static_warga.setEmail(document.getString("email"));
                                    static_warga.setRole(document.getString("role"));
                                    a=1;
                                }
                            }
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(a==1) {
                    Intent intent = new Intent(MainActivity.this, Jadwal_ronda.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, menu.class);
                    startActivity(intent);
                }

            }
        });
    }
}
