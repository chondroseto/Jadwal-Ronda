package informatika.terapan.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class set_time extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button time;
    EditText h;
    EditText m;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        time = (Button) findViewById(R.id.ok);
        h = (EditText) findViewById(R.id.hour);
        m = (EditText) findViewById(R.id.min);
        back = (ImageButton) findViewById(R.id.img_back);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                Intent intent = new Intent(set_time.this,lihat_jr.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(set_time.this,lihat_jr.class);
                startActivity(intent);
            }
        });
    }

    public void update(){
        //menyimpan jam


    }

    public void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this); //ngambil class dialog

        alertDialogBuilder.setTitle("Log Out");

        alertDialogBuilder.setMessage("ingin log out")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setNeutralButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();//membuat alert dialog

        alertDialog.show(); //menampilkan alert dialog
    }


}
