package informatika.terapan.app;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class menu extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button login,guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        loadFragment(new FragmentMenu());

    }

    private boolean loadFragment(Fragment fragment){
        if (fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("null").commit();
            return true;
        }
        return false;
    }

    public void Login(){
        loadFragment(new FragmentLogin());
        //Intent intent = new Intent(menu.this,login.class);
        //startActivity(intent);
    }

    public void SuccessLogin(){
        Toast t = Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_LONG);
        t.setGravity(Gravity.TOP | Gravity.CENTER, 0,0);
        t.show();
    }

    public void FailedLogin(){
        Toast t = Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_LONG);
        t.setGravity(Gravity.TOP | Gravity.CENTER, 0,0);
        t.show();
    }

    public void Register(){
        loadFragment(new FragmentRegister());
    }

    public void RegisterSuccess(){
        Toast t = Toast.makeText(getApplicationContext(), "register success", Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
        t.show();
        loadFragment(new FragmentLogin());
    }

    public void RegisterFailed(){
        Toast t = Toast.makeText(getApplicationContext(), "register failed", Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER | Gravity.CENTER, 0,0);
        t.show();
    }

    public void Guest(){
        static_warga.setNama("Guest");
        static_warga.setRole("3");
        Intent intent = new Intent(menu.this,Jadwal_ronda.class);
        startActivity(intent);
    }

    public void Pengumuman(){
        Intent intent = new Intent(menu.this,Jadwal_ronda.class);
        startActivity(intent);
    }

    public void Splash(){
        Intent intent = new Intent(menu.this, MainActivity.class);
        startActivity(intent);
    }

    public void Menu(){
        loadFragment(new FragmentMenu());
    }

}
