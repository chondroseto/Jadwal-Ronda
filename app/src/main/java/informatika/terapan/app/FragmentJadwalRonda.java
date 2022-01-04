package informatika.terapan.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentJadwalRonda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentJadwalRonda extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    Button senin,selasa,rabu,kamis,jumat,sabtu,minggu;

    static String day;

    public FragmentJadwalRonda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentJadwalRonda.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentJadwalRonda newInstance(String param1, String param2) {
        FragmentJadwalRonda fragment = new FragmentJadwalRonda();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jadwal_ronda, container, false);

        senin =  view.findViewById(R.id.btn_senin);
        selasa =  view.findViewById(R.id.btn_selasa);
        rabu =  view.findViewById(R.id.btn_rabu);
        kamis =  view.findViewById(R.id.btn_kamis);
        jumat =  view.findViewById(R.id.btn_jumat);
        sabtu =  view.findViewById(R.id.btn_sabtu);
        minggu =  view.findViewById(R.id.btn_minggu);

        //content
        senin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=senin.getText().toString();
                ((Jadwal_ronda)getActivity()).lihatjr();
            }
        });

        selasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=selasa.getText().toString();
                ((Jadwal_ronda)getActivity()).lihatjr();
            }
        });

        rabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=rabu.getText().toString();
                ((Jadwal_ronda)getActivity()).lihatjr();
            }
        });

        kamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=kamis.getText().toString();
                ((Jadwal_ronda)getActivity()).lihatjr();
            }
        });

        jumat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=jumat.getText().toString();
                ((Jadwal_ronda)getActivity()).lihatjr();
            }
        });

        sabtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=sabtu.getText().toString();
                ((Jadwal_ronda)getActivity()).lihatjr();
            }
        });

        minggu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=minggu.getText().toString();
                ((Jadwal_ronda)getActivity()).lihatjr();
            }
        });

        return view;
    }

    public static String getDay(){
        return day;
    }


}