package com.example.urpet.Utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Carlos Elliot Frias Mercado (Maku) on 09/09/2020.
 */
class Utils {

    companion object{
        fun getFechaHoy(): String {
            val date = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
            return date.format(Calendar.getInstance().time).toString()
        }
    }

}