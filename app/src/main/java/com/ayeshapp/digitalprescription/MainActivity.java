package com.ayeshapp.digitalprescription;


import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    DatabaseAccess databaseAccess;
    List<String> brands, generics,dosages,strengths;
    AutoCompleteTextView brandName, genericName, dosage, strength, frequency, duration, notes;

    RadioGroup rg;
    RadioButton selectedbtn;

    ArrayList<Medicine> list;
    RecyclerView recyclerView;
    MedicineAdapter medicineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseAccess = DatabaseAccess.getInstance(this);

        brands = new ArrayList<>();
        generics = new ArrayList<>();
        dosages = new ArrayList<>();
        strengths = new ArrayList<>();

        list = new ArrayList<>();
        medicineAdapter = new MedicineAdapter(list);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(medicineAdapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }

    public void showDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.medicine_form);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        brandName = dialog.findViewById(R.id.brand_name);
        genericName = dialog.findViewById(R.id.generic_name);
        genericName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    genericName.showDropDown();
                }
            }
        });
        dosage = dialog.findViewById(R.id.dosage);
        dosage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dosage.showDropDown();
                }
            }
        });
        strength = dialog.findViewById(R.id.strength);
        strength.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    strength.showDropDown();
                }
            }
        });
        frequency = dialog.findViewById(R.id.frequency);
        duration = dialog.findViewById(R.id.duration);
        notes = dialog.findViewById(R.id.notes);
        rg = dialog.findViewById(R.id.radio_group);

        brandName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 3) {
                    databaseAccess.open();
                    brands = databaseAccess.getQuotes(s.toString());
                    databaseAccess.close();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (MainActivity.this,android.R.layout.select_dialog_item, brands);
                    brandName.setAdapter(adapter);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        brandName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                databaseAccess.open();
                generics = databaseAccess.getGenericNamebyBarnd(brandName.getText().toString());
                if(generics.size() == 1) {
                    genericName.setText(generics.get(0));
                } else {
                    genericName.setText("");
                }
                ArrayAdapter<String> adapterg = new ArrayAdapter<String>
                        (MainActivity.this,android.R.layout.select_dialog_item, generics);
                genericName.setAdapter(adapterg);

                dosages = databaseAccess.getDosagebyBarnd(brandName.getText().toString());
                if(dosages.size() == 1) {
                    dosage.setText(dosages.get(0));
                } else {
                    dosage.setText("");
                }
                ArrayAdapter<String> adapterd = new ArrayAdapter<String>
                        (MainActivity.this,android.R.layout.select_dialog_item, dosages);
                dosage.setAdapter(adapterd);

                strengths = databaseAccess.getStrengthbyBarnd(brandName.getText().toString());
                if(strengths.size() == 1) {
                    strength.setText(strengths.get(0));
                } else {
                    strength.setText("");
                }

                ArrayAdapter<String> adapters = new ArrayAdapter<String>
                        (MainActivity.this,android.R.layout.select_dialog_item, strengths);
                strength.setAdapter(adapters);

                databaseAccess.close();

            }
        });

        genericName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                databaseAccess.open();

                dosages = databaseAccess.getDosagebyBAndG(brandName.getText().toString(), genericName.getText().toString());
                if(dosages.size() == 1) {
                    dosage.setText(dosages.get(0));
                } else {
                    dosage.setText("");
                }
                ArrayAdapter<String> adapterd = new ArrayAdapter<String>
                        (MainActivity.this,android.R.layout.select_dialog_item, dosages);
                dosage.setAdapter(adapterd);

                strengths = databaseAccess.getStrengthbyBAndG(brandName.getText().toString(), genericName.getText().toString());
                if(strengths.size() == 1) {
                    strength.setText(strengths.get(0));
                } else {
                    strength.setText("");
                }

                ArrayAdapter<String> adapters = new ArrayAdapter<String>
                        (MainActivity.this,android.R.layout.select_dialog_item, strengths);
                strength.setAdapter(adapters);

                databaseAccess.close();

            }
        });

        dosage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                databaseAccess.open();

                strengths = databaseAccess.getStrengthbyBAndGAndD(brandName.getText().toString(),
                        genericName.getText().toString(), dosage.getText().toString());
                if(strengths.size() == 1) {
                    strength.setText(strengths.get(0));
                } else {
                    strength.setText("");
                }

                ArrayAdapter<String> adapters = new ArrayAdapter<String>
                        (MainActivity.this,android.R.layout.select_dialog_item, strengths);
                strength.setAdapter(adapters);

                databaseAccess.close();

            }
        });

        genericName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 3) {
                    if(brandName.getText().toString().trim().equalsIgnoreCase("")) {
                        databaseAccess.open();
                        generics = databaseAccess.getGenerics(s.toString());
                        databaseAccess.close();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (MainActivity.this,android.R.layout.select_dialog_item, generics);
                        genericName.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Button add = dialog.findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicine m = new Medicine();
                m.setBrandName(brandName.getText().toString());
                m.setGenericName(genericName.getText().toString());
                m.setDosage(dosage.getText().toString());
                m.setStrength(strength.getText().toString());

                int sId = rg.getCheckedRadioButtonId();
                if (sId == -1) {
                    m.setMealTime("");
                } else {
                    RadioButton rb = dialog.findViewById(sId);
                    m.setMealTime(rb.getText().toString());
                }

                m.setFrequency(frequency.getText().toString());
                m.setDuration(duration.getText().toString());
                m.setNotes(notes.getText().toString());

                list.add(m);
                medicineAdapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        Button cancel = dialog.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}