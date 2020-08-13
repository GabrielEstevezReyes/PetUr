package com.example.urpet.home.social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.connections.BelongGroup;
import com.example.urpet.connections.PairM;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class EditarGrupoActivity extends AppCompatActivity {

    public EditText name;
    public EditText description;
    public CheckBox isClosed;
    public TextView number;
    public ScrollView requests;
    List<Integer> reqs = new ArrayList<Integer>();
    List<PairM> reqsP = new ArrayList<PairM>();

    FirebaseStorage storage = FirebaseStorage.getInstance();

    public void getBitmapFromURL(String src, final ImageView circ) {
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(src);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] b) {
                Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(b, 0, b.length));
                circ.setImageDrawable(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_grupo);
        name = findViewById(R.id.nameEditGroup);
        description = findViewById(R.id.descriptionEditGroup);
        isClosed = findViewById(R.id.closedGroupCreate);
        requests = findViewById(R.id.scrollEditGroup);
        name.setText(PersonalInfo.selectedGroup.getName());
        number = findViewById(R.id.numberRequests);
        description.setText(PersonalInfo.selectedGroup.getDescription());
        isClosed.setChecked(PersonalInfo.selectedGroup.getIsClosed() == 0);
        BelongGroup getB = new BelongGroup(-1, PersonalInfo.selectedGroup.getID());
        reqs = getB.getRequests();
        Log.d("belongs", "id: " +reqs.size());
        for(int i=0; i<reqs.size(); i++){
            Log.d("belongs", "id: " +reqs.get(i));
            reqsP.add(PersonalInfo.currentUser.getFromID(reqs.get(i)));
        }
        builder();
        number.setText("(" + reqsP.size() + ")");
    }

    public void updateGroup(View view) throws JSONException {
        PersonalInfo.selectedGroup.setName(name.getText().toString());
        PersonalInfo.selectedGroup.update();
        Intent siguiente = new Intent(EditarGrupoActivity.this, ListadoGruposActivity.class);
        startActivity (siguiente);
        finish();
    }

    private void builder() {
        requests.removeAllViews();
        for(int i = 0; i<reqsP.size(); i++){
            requests.addView(buildUser(reqsP.get(i)));
        }
    }

    LinearLayout buildUser(final PairM userInfo){
        LinearLayout parentLayout = new LinearLayout(this);
        parentLayout.setPadding(0,20,0,25);
        parentLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parentLayout.setOrientation(LinearLayout.HORIZONTAL);

        CircularImageView userI = new CircularImageView(this);
        if(!userInfo.getImage().isEmpty()) {
            getBitmapFromURL(userInfo.getImage(), userI);
            //userI.setImageDrawable(PersonalInfo.decodeDrawable(this, userInfo.getImage()));
        }
        else{
            userI.setImageResource(R.drawable.ic_user);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(125, 125);
        userI.setLayoutParams(params);
        parentLayout.addView(userI);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.poppins_semi_bold);

        TextView userNameL = new TextView(this);
        userNameL.setText(userInfo.getName());
        userNameL.setTextSize(14);
        userNameL.setTextColor(Color.parseColor("#FF193247"));
        userNameL.setTypeface(typeface);
        parentLayout.addView(userNameL);

        CircularImageView accept = new CircularImageView(this);
        if(!userInfo.getImage().isEmpty()) {
            accept.setImageDrawable(PersonalInfo.decodeDrawable(this, userInfo.getImage()));
        }
        else{
            accept.setImageResource(R.drawable.ic_check_group);
        }
        accept.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                acceptUser(v, userInfo);
            }
        });
        accept.setLayoutParams(params);
        parentLayout.addView(accept);

        CircularImageView negate = new CircularImageView(this);
        if(!userInfo.getImage().isEmpty()) {
            negate.setImageDrawable(PersonalInfo.decodeDrawable(this, userInfo.getImage()));
        }
        else{
            negate.setImageResource(R.drawable.ic_cancel_group);
        }
        negate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                negateUser(v, userInfo);
            }
        });
        negate.setLayoutParams(params);
        parentLayout.addView(negate);

        return parentLayout;
    }

    private void deleteRequest(int a){
        BelongGroup toDel = new BelongGroup(a, PersonalInfo.selectedGroup.getID());
        toDel.deleteJoin();
    }

    private void acceptUser(View v, PairM b){
        deleteRequest(b.getID());
        reqsP.remove(b);
        builder();
    }

    private void negateUser(View v, PairM b){
        deleteRequest(b.getID());
        reqsP.remove(b);
        builder();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(EditarGrupoActivity.this, DetalleGrupoActivity.class);
        startActivity (siguiente);
        finish();
    }
}
