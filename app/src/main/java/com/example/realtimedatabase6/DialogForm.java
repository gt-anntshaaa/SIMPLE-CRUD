package com.example.realtimedatabase6;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogForm extends DialogFragment implements View.OnClickListener{
    private static final String TAG = "Message From DForm: ";

    public DialogForm(){}

    private EditText inputNim, inputNama, inputProdi, inputIpk;
    private Button buttonSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_form_input, container, false);

        inputNim = (EditText) view.findViewById(R.id.idInputNim);
        inputNama = (EditText) view.findViewById(R.id.idInputNama);
        inputProdi = (EditText) view.findViewById(R.id.idInputProdi);
        inputIpk = (EditText) view.findViewById(R.id.idInputIpk);
        buttonSave = (Button) view.findViewById(R.id.idBtnSave);

        buttonSave.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();

        if (dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onClick(View v) {
        String NIM = inputNim.getText().toString();
        String NAMA = inputNama.getText().toString();
        String PRODI = inputProdi.getText().toString();
        String IPK = inputIpk.getText().toString();

        if (TextUtils.isEmpty(NIM)){
            inputNim.setError("NIM Tidak Valid !");
        }else if (TextUtils.isEmpty(NAMA)){
            inputNama.setError("Nama Tidak Valid !");
        }else if (TextUtils.isEmpty(PRODI)){
            inputProdi.setError("Prodi Tidak Valid");
        }else if (TextUtils.isEmpty(IPK)){
            inputIpk.setError("IPK Tidak Valid !");
        }else{
            addDataToRealtimeDatabase(NIM, NAMA, PRODI, IPK);
        }

    }

    private void addDataToRealtimeDatabase(String nim, String nama, String prodi, String ipk) {
        Mahasiswa mahasiswa = new Mahasiswa(nim, nama, prodi, ipk);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student Of MIT");
        reference.push().setValue(mahasiswa)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Data sukses ditambahkan...", Toast.LENGTH_SHORT).show();
                                clearData(inputNim, inputNama, inputProdi, inputIpk);
                            }
                        }
                ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(getContext(), "Terjadi kesalahan !!!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void clearData(EditText nim, EditText nama, EditText prodi, EditText ipk){
        nim.getText().clear();
        nama.getText().clear();
        prodi.getText().clear();
        ipk.getText().clear();
    }
}
