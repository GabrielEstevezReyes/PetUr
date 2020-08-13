package com.example.urpet.home.mascota.anadirMascota;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.urpet.PersonalInfo;
import com.example.urpet.home.mascota.PromoPostRegistroActivity;
import com.example.urpet.R;
import com.example.urpet.connections.Pet;
import com.example.urpet.connections.PetRace;
import com.example.urpet.connections.PetType;
import com.example.urpet.home.mascota.ListaMascotas;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegistroMascota extends AppCompatActivity {

    public EditText nameInput =  null;
    public Spinner typeInput =  null;
    public Spinner raceInput =  null;
    public EditText colorInput =  null;
    public EditText ageInput =  null;
    public EditText descriptionInput =  null;
    public Button registerButton = null;
    DatePickerDialog picker = null;
    ArrayList<PetType> tiposL = null;
    ArrayList<PetRace> razasL = null;
    int race = -1;
    int type = -1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_mascota);

        nameInput = findViewById(R.id.nombreMascotaIF);
        typeInput = findViewById(R.id.SpinnerCate);
        raceInput = findViewById(R.id.SpinnerRaza);
        colorInput =  findViewById(R.id.colorMascotaIF);
        ageInput = findViewById(R.id.edadMascotaIF);
        ageInput.setInputType(InputType.TYPE_NULL);
        ageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v);
            }
        });
        descriptionInput = findViewById(R.id.descripcionMascotaIF);
        registerButton = findViewById(R.id.botonRegistrarMascota);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RegistroMascota.this.myOnClick();
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        PetType tipos = new PetType();
        try {
            tiposL = tipos.read();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        PetRace razas = new PetRace();
        try {
            razasL = razas.read();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(tiposL!=null){
            List<String> spinnerArray =  new ArrayList<String>();
            for(int i=0; i<tiposL.size(); i++){
                spinnerArray.add(tiposL.get(i).getType());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeInput.setAdapter(adapter);
            typeInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    type = findTypeid(typeInput.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
        }

        if(razasL!=null){
            List<String> spinnerArray =  new ArrayList<String>();
            for(int i=0; i<razasL.size(); i++){
                spinnerArray.add(razasL.get(i).getType());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            raceInput.setAdapter(adapter);
            raceInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    race = findRaceid(raceInput.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
        }
    }

    int findRaceid(String racename){
        if(razasL!=null){
            for(int i=0; i<razasL.size(); i++){
                if(razasL.get(i).getType() == racename){
                    return razasL.get(i).getIDtype();
                }
            }
        }
        return -1;
    }
    int findTypeid(String racename){
        if(razasL!=null){
            for(int i=0; i<tiposL.size(); i++){
                if(tiposL.get(i).getType() == racename){
                    return tiposL.get(i).getIDtype();
                }
            }
        }
        return -1;
    }

    private Boolean allFieldsClean(){
        return !nameInput.getText().toString().isEmpty() &&
            !typeInput.getSelectedItem().toString().isEmpty() &&
            !raceInput.getSelectedItem().toString().isEmpty() &&
            !colorInput.getText().toString().isEmpty() &&
            !ageInput.getText().toString().isEmpty() &&
            !descriptionInput.getText().toString().isEmpty();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void myOnClick() throws JSONException, ParseException {
        Log.println(Log.INFO, "kek", "Voy a guardar mascotas");
        if(allFieldsClean()){
            Pet llenar = new Pet();
            llenar.create(nameInput.getText().toString(), PersonalInfo.currentUser.getID(), String.valueOf(race),
                    String.valueOf(type), colorInput.getText().toString(), descriptionInput.getText().toString(), "", ageInput.getText().toString());
            PersonalInfo.registedPetName = nameInput.getText().toString();
            Intent siguiente = new Intent(RegistroMascota.this, PromoPostRegistroActivity.class);
            startActivity(siguiente);
            finish();
            Toast toast = Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void selectDate(View v){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ageInput.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(RegistroMascota.this, ListaMascotas.class);
        startActivity (siguiente);
        finish();
    }
}
