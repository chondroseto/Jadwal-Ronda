package informatika.terapan.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class menu extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button login,guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        login =  findViewById(R.id.login_btn);
        guest =  findViewById(R.id.guest_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu.this,login.class);
                startActivity(intent);

            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                static_warga.setNama("Guest");
                static_warga.setRole("3");
                Intent intent = new Intent(menu.this,pengumuman.class);
                startActivity(intent);

            }
        });

    }

}
