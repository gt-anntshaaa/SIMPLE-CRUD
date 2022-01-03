package com.example.realtimedatabase6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
    private TextView detailNim, detailNama, detailProdi, detailIpk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        detailNama = (TextView) findViewById(R.id.idDetailNama);
        detailNim = (TextView) findViewById(R.id.idDetailNIM);
        detailProdi = (TextView) findViewById(R.id.idDetailProdi);
        detailIpk = (TextView) findViewById(R.id.idDetailIPK);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Mahasiswa mahasiswa = (Mahasiswa) getIntent().getSerializableExtra("value");

        detailNim.setText(mahasiswa.getNim());
        detailNama.setText(mahasiswa.getNama());
        detailProdi.setText(mahasiswa.getProdi());
        detailIpk.setText(mahasiswa.getIpk());
    }
}