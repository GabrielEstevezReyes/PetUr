package com.example.urpet.home.social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urpet.PersonalInfo;
import com.example.urpet.R;
import com.example.urpet.connections.BelongGroup;
import com.example.urpet.connections.Post;
import com.example.urpet.connections.User;
import com.example.urpet.home.social.post.RealizarPublicacionActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class DetalleGrupoActivity extends AppCompatActivity {

    public TextView title;
    public TextView description;
    public TextView isClosed;
    private List<Post> posts = null;
    public ScrollView scrol;
    public Button whereTo;
    public ImageView imageGroup;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    public void getBitmapFromURL(String src, final ImageView circ) {
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(src);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(b -> {
            Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(b, 0, b.length));
            circ.setImageDrawable(image);
        }).addOnFailureListener(exception -> {
            // Handle any errors
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_principal);
        whereTo = findViewById(R.id.whereToGoGroup);
        title = findViewById(R.id.nameOfGroupTextPrincipal);
        description = findViewById(R.id.descriptionPrincipalGroup);
        isClosed = findViewById(R.id.isClosedText);
        scrol = findViewById(R.id.scrollPostss);
        title.setText(PersonalInfo.selectedGroup.getName());
        description.setText(PersonalInfo.selectedGroup.getDescription());
        imageGroup = findViewById(R.id.imagePrincipalGroup);
        Log.d("GROUP", "ESTA: " + PersonalInfo.selectedGroup.getIsClosed());
        isClosed.setText(PersonalInfo.selectedGroup.getIsClosed() == 0 ? "Grupo abierto" : "Grupo cerrado");
        PersonalInfo.selectedGroup.getImageSafe();
        if(!PersonalInfo.selectedGroup.getImage().isEmpty()) {

            getBitmapFromURL(PersonalInfo.selectedGroup.getImage(), imageGroup);
        }
        else{
            imageGroup.setVisibility(View.GONE);
        }
        if(PersonalInfo.selectedGroup.getCreatorID() == PersonalInfo.clickedPet.getID()){
            whereTo.setText("Editar");
            whereTo.setOnClickListener(v -> goToEditGroup(v));
        }
        else{
            if(PersonalInfo.belong.contains(PersonalInfo.selectedGroup.getID())){
                whereTo.setVisibility(View.GONE);
            }
            else{
                whereTo.setText("Unirse");
                if(PersonalInfo.selectedGroup.getIsClosed() == 0) {
                    whereTo.setOnClickListener(v -> joinGroup());
                }
                else{
                    whereTo.setOnClickListener(v -> requestJoinGroup());
                }
            }
        }
        Post curr = new Post(PersonalInfo.selectedGroup.getID());
        posts = curr.readFromGroup();
        Log.d("posts", "Posts downloaded: " + posts.size());
        for(int i=0; i<posts.size(); i++){
            scrol.addView(buildPost(posts.get(i)));
        }
    }

    LinearLayout buildPost(final Post selectedPost){
        LinearLayout parentPost = new LinearLayout(this);
        parentPost.setPadding(25, 25, 25, 25);
        parentPost.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parentPost.setOrientation(LinearLayout.VERTICAL);
        parentPost.setElevation(16);

        LinearLayout innerL = new LinearLayout(this);
        innerL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        innerL.setOrientation(LinearLayout.VERTICAL);

        LinearLayout titleInfo = new LinearLayout(this);
        titleInfo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        CircularImageView PosterI = new CircularImageView(this);
        User toShow = new User(selectedPost.getPoster());
        toShow.getImageSafe();
        String toShowImage = toShow.getBase64Image();
        if(toShowImage!=null) {
            if (!toShowImage.isEmpty()) {
                PosterI.setImageDrawable(PersonalInfo.decodeDrawable(this, toShowImage));
            } else {
                PosterI.setImageResource(R.drawable.ic_mascotas_1);
            }
            titleInfo.addView(PosterI);
        }

        Typeface typeface = ResourcesCompat.getFont(this, R.font.poppins_semi_bold);

        TextView titlePost = new TextView(this);
        titlePost.setText(selectedPost.getName());
        titlePost.setTextSize(14);
        titlePost.setTextColor(Color.parseColor("#FF193247"));
        titlePost.setTypeface(typeface);
        titleInfo.addView(titlePost);

        LinearLayout commentsButton = new LinearLayout(this);
        commentsButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        commentsButton.setGravity(Gravity.RIGHT);

        CircularImageView msgI = new CircularImageView(this);
        msgI.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        msgI.setImageResource(R.drawable.ic_send_message_2);
        commentsButton.addView(msgI);

        titleInfo.addView(commentsButton);

        TextView postcontent = new TextView(this);
        postcontent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        postcontent.setText(selectedPost.getDescription());
        postcontent.setTextSize(10);
        postcontent.setTypeface(typeface);
        postcontent.setTextColor(Color.parseColor("#FF193247"));

        innerL.addView(titleInfo);
        innerL.addView(postcontent);

        parentPost.addView(innerL);

        return parentPost;
    }

    public void goToEditGroup(View v1){
        Intent siguiente = new Intent(DetalleGrupoActivity.this, EditarGrupoActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void onRealizarPublicacion(View v1){
        Intent siguiente = new Intent(this, RealizarPublicacionActivity.class);
        startActivity (siguiente);
        finish();
    }

    public void joinGroup(){
        BelongGroup toJoin = new BelongGroup(PersonalInfo.currentUser.getID(), PersonalInfo.selectedGroup.getID());
        toJoin.create();
        Toast.makeText(DetalleGrupoActivity.this, "Te has unido a este grupo.",
                Toast.LENGTH_SHORT).show();
        reload();
    }

    private void reload(){
        BelongGroup myBelongs = new BelongGroup(PersonalInfo.currentUser.getID(), -1);
        PersonalInfo.belong = myBelongs.getFromUser();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void requestJoinGroup(){
        BelongGroup toJoin = new BelongGroup(PersonalInfo.currentUser.getID(), PersonalInfo.selectedGroup.getID());
        toJoin.requestJoin();
        Toast.makeText(DetalleGrupoActivity.this, "Tu solicitud para unirte ha sido enviada.",
                Toast.LENGTH_SHORT).show();
        reload();
    }

    @Override
    public void onBackPressed() {
        Intent siguiente = new Intent(DetalleGrupoActivity.this, ListadoGruposActivity.class);
        startActivity (siguiente);
        finish();
    }
}