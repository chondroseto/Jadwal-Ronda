package informatika.terapan.app;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class lihat_jr extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    LinearLayout interface_a1,interface_a2,interface_a3,interface_a4,interface_a5;

    TextView set,waktu,a1,a2,a3,a4,a5,info_a,title;

    ImageView ga1,ga2,ga3,ga4,ga5;

    ImageButton back_btn;
    Button in;

    String hari,hour,min;
    int t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_jr);

        interface_a1 =  findViewById(R.id.layout_anggota_1);
        interface_a2 =  findViewById(R.id.layout_anggota_2);
        interface_a3 =  findViewById(R.id.layout_anggota_3);
        interface_a4 =  findViewById(R.id.layout_anggota_4);
        interface_a5 =  findViewById(R.id.layout_anggota_5);

        title =  findViewById(R.id.title_ljr);
        set =  findViewById(R.id.st);
        waktu =  findViewById(R.id.jam);
        info_a =  findViewById(R.id.toast_a);

        a1 =  findViewById(R.id.anggota_1);
        a2 =  findViewById(R.id.anggota_2);
        a3 =  findViewById(R.id.anggota_3);
        a4 =  findViewById(R.id.anggota_4);
        a5 =  findViewById(R.id.anggota_5);


        ga1 =  findViewById(R.id.gambar_a1);
        ga2 =  findViewById(R.id.gambar_a2);
        ga3 =  findViewById(R.id.gambar_a3);
        ga4 =  findViewById(R.id.gambar_a4);
        ga5 =  findViewById(R.id.gambar_a5);

        back_btn =  findViewById(R.id.img_back);
        in =  findViewById(R.id.join);

        title.setText(Jadwal_ronda.getDay());
        ui_action();
        data();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lihat_jr.this,Jadwal_ronda.class);
                startActivity(intent);
            }
        });

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join();
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(),"time picker");
            }
        });

    }

    private void data(){
        db.collection("sistem")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.get("hari").equals(title.getText().toString().toLowerCase())) { //jika bukan equals tidak bisa sad
                                    a1.setText(document.getString("anggota 1"));
                                    a2.setText(document.getString("anggota 2"));
                                    a3.setText(document.getString("anggota 3"));
                                    a4.setText(document.getString("anggota 4"));
                                    a5.setText(document.getString("anggota 5"));
                                    waktu.setText(document.getString("jam")+" : "+document.getString("menit"));
                                }

                            }
                        }
                        check_data();
                        check_user();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast t = Toast.makeText(getApplicationContext(), "load data failed", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                t.show();
            }
        });
    }



    private void ui_action(){
        if (static_warga.getRole().equals("3")){
            in.setVisibility(View.GONE);
            set.setVisibility(View.GONE);
        }else{
            in.setVisibility(View.VISIBLE);
            if(static_warga.getRole().equals("1")){
                set.setVisibility(View.VISIBLE);
            }else{
                set.setVisibility(View.GONE);
            }
        }
    }

    private void check_data(){
        if(a1.length()<1){
            interface_a1.setVisibility(View.GONE);
            a1.setVisibility(View.GONE);
            ga1.setVisibility(View.GONE);
        }
        if(a2.length()<1){
            interface_a2.setVisibility(View.GONE);
            a2.setVisibility(View.GONE);
            ga2.setVisibility(View.GONE);
        }
        if(a3.length()<1){
            interface_a3.setVisibility(View.GONE);
            a3.setVisibility(View.GONE);
            ga3.setVisibility(View.GONE);
        }
        if(a4.length()<1){
            interface_a4.setVisibility(View.GONE);
            a4.setVisibility(View.GONE);
            ga4.setVisibility(View.GONE);
        }
        if(a5.length()<1){
            interface_a5.setVisibility(View.GONE);
            a5.setVisibility(View.GONE);
            ga5.setVisibility(View.GONE);
        }

        if(a1.length()>0){
            interface_a1.setVisibility(View.VISIBLE);
            a1.setVisibility(View.VISIBLE);
            ga1.setVisibility(View.VISIBLE);
            info_a.setVisibility(View.GONE);
            if(a1.getText().equals(static_warga.getNama())){
                t=1;
            }else{
                t=0;
            }
        }
        if(a2.length()>0){
            interface_a2.setVisibility(View.VISIBLE);
            a2.setVisibility(View.VISIBLE);
            ga2.setVisibility(View.VISIBLE);
            if(a2.getText().equals(static_warga.getNama())){
                t=1;
            }else{
                t=0;
            }
        }
        if(a3.length()>0){
            interface_a3.setVisibility(View.VISIBLE);
            a3.setVisibility(View.VISIBLE);
            ga3.setVisibility(View.VISIBLE);
            if(a3.getText().equals(static_warga.getNama())){
                t=1;
            }else{
                t=0;
            }
        }
        if(a4.length()>0){
            interface_a4.setVisibility(View.VISIBLE);
            a4.setVisibility(View.VISIBLE);
            ga4.setVisibility(View.VISIBLE);
            if(a4.getText().equals(static_warga.getNama())){
                t=1;
            }else{
                t=0;
            }
        }
        if(a5.length()>0){
            interface_a5.setVisibility(View.VISIBLE);
            a5.setVisibility(View.VISIBLE);
            ga5.setVisibility(View.VISIBLE);
            if(a5.getText().equals(static_warga.getNama())){
                t=1;
            }else{
                t=0;
            }
        }
        if(waktu.getText().toString().equals(" : ")){
            waktu.setText("waktu belum di set");
        }
        if(a5.length()<1&&a4.length()<1&&a3.length()<1&&a2.length()<1&&a1.length()<1){
            info_a.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour = String.valueOf(hourOfDay);
        min = String.valueOf(minute);
        waktu.setText(hourOfDay+" : "+minute);
        savetime();
    }

    private void savetime(){
        db.collection("sistem")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.get("hari").equals(title.getText().toString().toLowerCase())) { //jika bukan equals tidak bisa sad
                                    db.collection("sistem").document(document.getId()).update("jam",hour);
                                    db.collection("sistem").document(document.getId()).update("menit",min);
                                }

                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast t = Toast.makeText(getApplicationContext(), "save time failed", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                t.show();
            }
        });

    }

    private void check_user(){
        if(static_warga.getNama().equals(a1.getText().toString())||static_warga.getNama().equals(a2.getText().toString())||static_warga.getNama().equals(a3.getText().toString())||static_warga.getNama().equals(a4.getText().toString())||static_warga.getNama().equals(a5.getText().toString())){
            in.setText("BATAL IKUT");
            in.setEnabled(true);
        }else if(!static_warga.getNama().equals(a1.getText().toString())||!static_warga.getNama().equals(a2.getText().toString())||!static_warga.getNama().equals(a3.getText().toString())||!static_warga.getNama().equals(a4.getText().toString())||!static_warga.getNama().equals(a5.getText().toString())){
            in.setText("IKUT");
            in.setEnabled(true);
        }
        if(t==0) {
            if (a5.length() > 0 && a4.length() > 0 && a3.length() > 0 && a2.length() > 0 && a1.length() > 0) {
                in.setText("FULL");
                in.setEnabled(false);
            }
        }
    }


    private void join(){
        if(in.getText().equals("IKUT")){
            add_user();
        }else if (in.getText().equals("BATAL IKUT")){
            remove_user();
        }
    }

    private void add_user(){
        db.collection("sistem")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.get("hari").equals(title.getText().toString().toLowerCase())) { //jika bukan equals tidak bisa sad

                                        if (a1.length() < 1) {
                                            db.collection("sistem").document(document.getId()).update("anggota 1", static_warga.getNama());
                                        } else if (a2.length() < 1) {
                                            db.collection("sistem").document(document.getId()).update("anggota 2", static_warga.getNama());
                                        } else if (a3.length() < 1) {
                                            db.collection("sistem").document(document.getId()).update("anggota 3", static_warga.getNama());
                                        } else if (a4.length() < 1) {
                                            db.collection("sistem").document(document.getId()).update("anggota 4", static_warga.getNama());
                                        } else if (a5.length() < 1) {
                                            db.collection("sistem").document(document.getId()).update("anggota 5", static_warga.getNama());
                                        }
                                }
                            }
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                data();
                Toast t = Toast.makeText(getApplicationContext(), "join success", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                t.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast t = Toast.makeText(getApplicationContext(), "join failed", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                t.show();
            }
        });
    }

    private void remove_user(){
        db.collection("sistem")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.get("hari").equals(title.getText().toString().toLowerCase())) { //jika bukan equals tidak bisa sad

                                    if(static_warga.getNama().equals(a1.getText().toString())){
                                        db.collection("sistem").document(document.getId()).update("anggota 1", "");
                                    }else if(static_warga.getNama().equals(a2.getText().toString())){
                                        db.collection("sistem").document(document.getId()).update("anggota 2", "");
                                    }else if(static_warga.getNama().equals(a3.getText().toString())){
                                        db.collection("sistem").document(document.getId()).update("anggota 3", "");
                                    }else if(static_warga.getNama().equals(a4.getText().toString())){
                                        db.collection("sistem").document(document.getId()).update("anggota 4", "");
                                    }else if(static_warga.getNama().equals(a5.getText().toString())) {
                                        db.collection("sistem").document(document.getId()).update("anggota 5", "");
                                    }
                                }

                            }
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                data();
                Toast t = Toast.makeText(getApplicationContext(), "remove success", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                t.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast t = Toast.makeText(getApplicationContext(), "remove failed", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
                t.show();
            }
        });

    }


}




