package com.example.urpet.home.mascota.opciones.casa.alimentacion

import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.urpet.R
import com.example.urpet.Utils.Utils
import com.example.urpet.data.local.database.entity.UltimaComidaEntity
import com.example.urpet.databinding.DialogFragmentRegistrarComidaBinding

/**
 * Created by Carlos Elliot Frias Mercado (Maku) on 09/09/2020.
 */
class DialogAgregarComida(clickListener: (UltimaComidaEntity) -> Unit):DialogFragment() {

    private lateinit var binding: DialogFragmentRegistrarComidaBinding

    private val listener = clickListener

    companion object{
        fun newInstance(clickListener: (UltimaComidaEntity) -> Unit):DialogAgregarComida {
            val args = Bundle()

            val fragment = DialogAgregarComida(clickListener)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_registrar_comida, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindviews()
    }

    /**
     *
     * Permite redimensionar correctamente el fragment del cuadro de dialogo.
     */
    override fun onStart() {
        super.onStart()
        val window = dialog!!.window!!
        val size = Point()
        val display = window.windowManager.defaultDisplay
        display.getSize(size)
        window.setLayout(size.x, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
    }

    private fun bindviews(){
        binding.fragmentComidaCerrarIv.setOnClickListener { dismiss() }
        binding.fragmentComidaAceptarBtn.setOnClickListener { sendData(); dismiss() }
    }

    private fun sendData(){
        if(!binding.fragmentComidaPresentacionEt.text.isEmpty() &&
                !binding.fragmentNuevaComidaDescrEt.text.isEmpty() &&
                        !binding.fragmentNuevaComidaNombreEt.text.isEmpty()){
            listener(UltimaComidaEntity(null, binding.fragmentNuevaComidaDescrEt.text.toString(),
                    binding.fragmentNuevaComidaNombreEt.text.toString(), Utils.getFechaHoy(), binding.fragmentComidaPresentacionEt.text.toString()))
        }
    }
}