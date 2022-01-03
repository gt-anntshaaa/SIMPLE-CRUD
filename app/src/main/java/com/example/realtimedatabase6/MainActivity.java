package com.example.realtimedatabase6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton fabAdd;
    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private mAdapter adapter;
    private ArrayList<Mahasiswa> listStd = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAdd = (FloatingActionButton) findViewById(R.id.idFabAdd);
        progressBar = (ProgressBar) findViewById(R.id.idProgressBar);

        recyclerView = findViewById(R.id.idRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));

        adapter = new mAdapter(MainActivity.this, listStd);
        recyclerView.setAdapter(adapter);


        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Mahasiswa mahasiswa = listStd.get(position);

                intent.putExtra("value", mahasiswa);
                startActivity(intent);
            }

            @Override
            public void onClickEdit(int position) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                Mahasiswa mahasiswa = listStd.get(position);

                intent.putExtra("value", mahasiswa);
                startActivity(intent);
            }

            @Override
            public void onClickDelete(int position, Mahasiswa item) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student Of MIT");

                reference.child(item.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "delete succes", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        fabAdd.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Student Of MIT").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listStd.clear();
                progressBar.setVisibility(View.GONE);
                for (DataSnapshot ds:snapshot.getChildren()){
                    Mahasiswa mhs = ds.getValue(Mahasiswa.class);
                    mhs.setKey(ds.getKey());

                    listStd.add(mhs);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idFabAdd:
                DialogForm dialogForm = new DialogForm();
                dialogForm.show(getSupportFragmentManager(), "form");
                break;
        }
    }
}