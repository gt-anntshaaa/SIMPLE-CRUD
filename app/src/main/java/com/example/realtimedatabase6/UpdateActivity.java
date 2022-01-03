package com.example.realtimedatabase6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editNama, editNim, editProdi, editIpk;
    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editNim = (EditText) findViewById(R.id.idUpdateNim);
        editNama = (EditText) findViewById(R.id.idUpdateNama);
        editProdi = (EditText) findViewById(R.id.idUpdateProdi);
        editIpk = (EditText) findViewById(R.id.idUpdateIpk);
        buttonUpdate = (Button) findViewById(R.id.idBtnUpdate);


        buttonUpdate.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Mahasiswa mahasiswa = (Mahasiswa) getIntent().getSerializableExtra("value");
        editNim.setText(mahasiswa.getNim());
        editNama.setText(mahasiswa.getNama());
        editProdi.setText(mahasiswa.getProdi());
        editIpk.setText(mahasiswa.getIpk());
    }

    @Override
    public void onClick(View v) {
        String NIM = editNim.getText().toString();
        String NAMA = editNama.getText().toString();
        String PRODI = editProdi.getText().toString();
        String IPK = editIpk.getText().toString();

        Mahasiswa mahasiswa = (Mahasiswa) getIntent().getSerializableExtra("value");


        if (TextUtils.isEmpty(NIM)){
            editNim.setError("NIM Tidak Valid !");
        }else if (TextUtils.isEmpty(NAMA)){
            editNama.setError("Nama Tidak Valid !");
        }else if (TextUtils.isEmpty(PRODI)){
            editProdi.setError("Prodi Tidak Valid");
        }else if (TextUtils.isEmpty(IPK)){
            editIpk.setError("IPK Tidak Valid !");
        }else{
            updateDataFromRealtimeDatabase(mahasiswa, NIM, NAMA, PRODI, IPK);
        }
    }

    private void updateDataFromRealtimeDatabase(Mahasiswa mahasiswa, String nim, String nama, String prodi, String ipk) {
        HashMap mapMahasiswa = new HashMap();
        mapMahasiswa.put("nim", nim);
        mapMahasiswa.put("nama", nama);
        mapMahasiswa.put("prodi", prodi);
        mapMahasiswa.put("ipk", ipk);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student Of MIT");

        reference.child(mahasiswa.getKey()).updateChildren(mapMahasiswa).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "sukses terupdate", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}