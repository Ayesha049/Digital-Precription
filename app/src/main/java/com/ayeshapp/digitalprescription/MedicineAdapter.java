package com.ayeshapp.digitalprescription;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.viewHolder> {
    ArrayList<Medicine> list;
    //OnSpendItemClick onSpendItemClick;

    public MedicineAdapter(ArrayList<Medicine> list /*OnSpendItemClick onSpendItemClick*/) {
        this.list = list;
        //this.onSpendItemClick = onSpendItemClick;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.medicine_item, viewGroup, false);
        return new viewHolder(contactView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Medicine model = list.get(i);
        String sl = Integer.toString(i+1);
        viewHolder.slNo.setText(sl);
        String med = "";
        if (!model.getBrandName().equalsIgnoreCase("")) {
            med+=model.getBrandName() + " ";
        }
        if (!model.getGenericName().equalsIgnoreCase("")) {
            if(model.getBrandName().equalsIgnoreCase("")) {
                med+=model.getGenericName() + " ";
            } else {
                med+="(" + model.getGenericName() + ") ";
            }
        }
        if (!model.getDosage().equalsIgnoreCase("")) {
            med+=model.getDosage() + " ";
        }
        if (!model.getStrength().equalsIgnoreCase("")) {
            med+=model.getStrength() + " ";
        }

        viewHolder.medicine.setText(med);
        med = "";

        if (!model.getFrequency().equalsIgnoreCase("")) {
            med+=model.getFrequency() + " ";
        }
        if (!model.getMealTime().equalsIgnoreCase("")) {
            med+=model.getMealTime() + " ";
        }
        if (!model.getDuration().equalsIgnoreCase("")) {
            med+=model.getDuration() + " ";
        }

        viewHolder.duration.setText(med);

        if (!model.getNotes().equalsIgnoreCase("")) {
            viewHolder.note.setText(model.getNotes());
        } else {
            viewHolder.noteLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        //OnSpendItemClick onSpendItemClick;

        TextView medicine, duration, note, slNo;
        LinearLayout noteLayout;

        public viewHolder(@NonNull View itemView /*, final OnSpendItemClick onSpendItemClick*/) {
            super(itemView);
            //medication = itemView.findViewById(R.id.full_medication);
            slNo = itemView.findViewById(R.id.sl_no);
            medicine = itemView.findViewById(R.id.medicine_name);
            duration = itemView.findViewById(R.id.taking_time);
            note = itemView.findViewById(R.id.notes);
            noteLayout = itemView.findViewById(R.id.note_layout);
        }
    }
}