package com.example.urpet.home.mascota.opciones.casa.alimentacion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.urpet.R
import com.example.urpet.Utils.UrPetApplication
import com.example.urpet.database.entity.UltimaComidaEntity
import com.example.urpet.databinding.ItemComidaRecienteBinding

/**
 * Created by Carlos Elliot Frias Mercado (Maku) on 08/09/2020.
 */
class ComidaAdapter(private var comidas: List<UltimaComidaEntity>,
                    private val clickListener:(UltimaComidaEntity) -> Unit) : RecyclerView.Adapter<ComidaAdapter.ComidaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemComidaRecienteBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_comida_reciente, parent, false)
        return ComidaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComidaViewHolder, position: Int) {
        holder.bind(comidas[position], clickListener)
    }

    override fun getItemCount(): Int {
        return comidas.size
    }

    class ComidaViewHolder(private val binding: ItemComidaRecienteBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(comida :UltimaComidaEntity, clickListener:(UltimaComidaEntity) -> Unit){
            binding.itemComidaDescripcion.text = comida.descripcion
            binding.itemComidaFecha.text = UrPetApplication.getApplication().getString(R.string.adquirido, comida.fecha)
            binding.itemComidaTamano.text = UrPetApplication.getApplication().getString(R.string.edicion_de, comida.presentacion)
            binding.itemComidaTitulo.text = comida.nombre
            binding.itemComidaAddElemento.setOnClickListener { clickListener(comida) }
        }
    }
}