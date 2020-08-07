package com.example.urpet.home.grupos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.urpet.home.MainActivity;
import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.connections.BelongGroup;
import com.example.urpet.connections.Group;

import java.text.ParseException;
import java.util.ArrayList;

public class ListadoGruposActivity extends AppCompatActivity {

    LinearLayout scroll = null;
    ArrayList<Group> allGroups = null;
    ArrayList<Group> userGroups = null;
    ArrayList<Group> belongToGroups = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);
        PersonalInfo.selectedGroup = null;
        scroll = findViewById(R.id.ListaGruposScroll);
        BelongGroup myBelongs = new BelongGroup(PersonalInfo.clickedPet.getID(), -1);
        PersonalInfo.belong = myBelongs.getFromUser();
        Group obtener = new Group();
        try {
            allGroups = obtener.readAll();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userGroups = getUserOwns();
        belongToGroups = getBelongTo();
        scroll.removeAllViews();
        buildAllGroups();
    }

    public ArrayList<Group> getUserOwns(){
        ArrayList<Group> res = new ArrayList<Group>();
        for (int i=0; i<allGroups.size(); i++){
            Group check = allGroups.get(i);
            if(check.getCreatorID() == PersonalInfo.clickedPet.getID()){
                res.add(check);
            }
        }
        return res;
    }

    public ArrayList<Group> getBelongTo(){
        ArrayList<Group> res = new ArrayList<Group>();
        if(PersonalInfo.belong != null) {
            for (int i = 0; i < allGroups.size(); i++) {
                Group check = allGroups.get(i);
                if (PersonalInfo.belong.contains(check.getID())) {
                    res.add(check);
                }
            }
        }
        return res;
    }

    public void buildUserGroups(){
        Log.println(Log.INFO, "Grou", "UserGroups");
        scroll.removeAllViews();
        for (int i=0; i<userGroups.size(); i++){
            scroll.addView(buildGroupItem(userGroups.get(i)));
            Log.println(Log.INFO, "Grou", userGroups.get(i).toString());
        }
    }

    public void buildBelongGroups(){
        Log.println(Log.INFO, "Grou", "BelongGroups");
        scroll.removeAllViews();
        for (int i=0; i<belongToGroups.size(); i++){
            scroll.addView(buildGroupItem(belongToGroups.get(i)));
            Log.println(Log.INFO, "Grou", belongToGroups.get(i).toString());
        }
    }

    public void buildAllGroups(){
        Log.println(Log.INFO, "Grou", "AllGroups");
        scroll.removeAllViews();
        for (int i=0; i<allGroups.size(); i++){
            scroll.addView(buildGroupItem(allGroups.get(i)));
            Log.println(Log.INFO, "Grou", allGroups.get(i).toString());
        }
    }

    public void btn_sig(View view){
        Intent siguiente = new Intent(ListadoGruposActivity.this, CrearGrupo.class);
        startActivity (siguiente);
        finish();
    }

    public void btn_All(View view){
        buildAllGroups();
    }

    public void btn_User(View view){
        buildUserGroups();
    }

    public void btn_Belong(View view){
        buildBelongGroups();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(ListadoGruposActivity.this, MainActivity.class);
        startActivity (siguiente);
        finish();
    }

    LinearLayout buildGroupItem(final Group groupToBuild){
        LinearLayout parentGroup = new LinearLayout(this);
        parentGroup.setPadding(0,10,0,10);
        parentGroup.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parentGroup.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout textsPet = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textsPet.setLayoutParams(params);
        textsPet.setOrientation(LinearLayout.VERTICAL);
        textsPet.setPadding(15,0,0,0);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.sf_bold);

        TextView nameOfGroup = new TextView(this);
        nameOfGroup.setText(groupToBuild.getName());
        nameOfGroup.setTextSize(17);
        nameOfGroup.setTextColor(Color.parseColor("#58672A"));
        nameOfGroup.setTypeface(typeface);

        TextView groupDetails = new TextView(this);
        groupDetails.setText("Ver detalles");
        groupDetails.setTextSize(13);
        groupDetails.setTextColor(Color.parseColor("#9958676A"));
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.sf_bold);
        groupDetails.setTypeface(typeface2);
        nameOfGroup.setTypeface(typeface);
        textsPet.addView(nameOfGroup);
        textsPet.addView(groupDetails);
        textsPet.setId(groupToBuild.getID());
        parentGroup.addView(textsPet);
        parentGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPrincipalGroup(v, groupToBuild);
            }
        });
        return parentGroup;
    }

    public void goToPrincipalGroup(View v1, Group petToEdit){
        PersonalInfo.selectedGroup = petToEdit;
        Log.println(Log.INFO, "kek2", PersonalInfo.selectedGroup.toString());
        Intent siguiente = new Intent(ListadoGruposActivity.this, DetalleGrupoActivity.class);
        startActivity (siguiente);
        finish();
    }
}
