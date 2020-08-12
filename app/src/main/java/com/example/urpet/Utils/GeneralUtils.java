package com.example.urpet.Utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.fragment.app.FragmentManager;

import com.example.urpet.Utils.alert.SpinnerFechasDialogFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

public class GeneralUtils {

    public static void getBitmapFromURL(String src, final CircularImageView circ, FirebaseStorage storage, Context context) {
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(src);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(b -> {
            Drawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(b, 0, b.length));
            circ.setImageDrawable(image);
        }).addOnFailureListener(exception -> {
            // Handle any errors
        });
    }

    public static void abrirCalendarioFecha(FragmentManager manager, SpinnerFechasDialogFragment.onDateSelected listener, String tag){
        SpinnerFechasDialogFragment fechas = SpinnerFechasDialogFragment.newInstance();
        fechas.setmListener(listener);
        fechas.show(manager, tag);
    }
}
