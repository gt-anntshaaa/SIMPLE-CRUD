package com.example.realtimedatabase6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class mAdapter extends RecyclerView.Adapter<mAdapter.mViewHolder> {
    public class mViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private TextView vNama, vProdi;
        private ImageButton imgBtnEdit, imgBtnDelete;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.idCardView);
            vNama = (TextView) itemView.findViewById(R.id.idViewNama);
            vProdi = (TextView) itemView.findViewById(R.id.idViewProdi);
            imgBtnEdit = (ImageButton) itemView.findViewById(R.id.idImgBtnEdit);
            imgBtnDelete = (ImageButton) itemView.findViewById(R.id.idImgBtnDelete);

        }
    }

    private Context context;
    private ArrayList<Mahasiswa> listStd;
    private OnClickListener onClickListener;

    public mAdapter(Context context, ArrayList listStd) {
        this.context = context;
        this.listStd = listStd;
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public mAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new mViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull mAdapter.mViewHolder holder, int position) {
        final int aPosition = holder.getAdapterPosition();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student Of MIT");
        Mahasiswa mahasiswa = listStd.get(position);

        holder.vNama.setText(listStd.get(position).getNama());
        holder.vProdi.setText(listStd.get(position).getProdi());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(aPosition);
            }
        });

        holder.imgBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClickEdit(aPosition);
            }
        });

        holder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClickDelete(aPosition, mahasiswa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listStd.size();
    }
}
