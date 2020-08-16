package com.example.urpet.Utils.alert;

import androidx.fragment.app.FragmentManager;

public class AlertManager {

    public static void muestraMensaje(FragmentManager fr, String tag, AlertFragment.EnumTipoMensaje tipo,
                                      String titulo, String desc, boolean soloCerrar,
                                      AlertFragment.onAceptarClick listener){
        AlertFragment fragment = AlertFragment.newInstance(tipo, titulo, desc, soloCerrar);
        fragment.setmListener(listener);
        fragment.show(fr,tag);
    }

}
