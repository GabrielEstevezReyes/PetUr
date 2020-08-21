package com.example.urpet;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.example.urpet.connections.social.Group;
import com.example.urpet.connections.Pet;
import com.example.urpet.connections.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class PersonalInfo {

    public static String userTableName = "up_usuario";
    public static String petTableName = "up_mascota";
    public static String groupsTableName = "up_grupos";
    public static String postsTableName = "up_post_grupo";
    public static String commentsTableName = "comment";
    public static String marketingTableName = "up_publicidad";
    public static String belongsTableName = "up_relacion_grupos";
    public static String requestGroupTableName = "up_solc_grupo";


    public static List<Integer> belong = null;

    public static String registeredName = "";

    public static String registedPetName = "";
    public static String registedPetImage = "";

    public static User currentUser;
    public static Pet clickedPet = null;
    public static Group selectedGroup = null;

    public static Drawable decodeDrawable(Context context, String base64) {
        Drawable ret = null;
        if (!base64.equals("")) {
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(base64.getBytes(), Base64.DEFAULT));
            ret = Drawable.createFromResourceStream(context.getResources(),
                    null, bais, null, null);
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }
}
