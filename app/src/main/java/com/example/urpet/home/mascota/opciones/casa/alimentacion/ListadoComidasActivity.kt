package com.example.urpet.home.mascota.opciones.casa.alimentacion

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urpet.R
import com.example.urpet.data.local.database.UrPetDatabase
import com.example.urpet.data.local.database.entity.UltimaComidaEntity
import com.example.urpet.databinding.ActivityAnadirAlimentoBinding
import com.example.urpet.repository.UltimaComidaRepository
import com.example.urpet.viewModel.UltimaComidaViewModel
import com.example.urpet.viewModel.UltimaComidaViewModelFactory

/**
 * Created by Carlos Elliot Frias Mercado (Maku) on 08/09/2020.
 */
class ListadoComidasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirAlimentoBinding

    private lateinit var ultimaComidaViewModel: UltimaComidaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_anadir_alimento)
        val dao = UrPetDatabase.getInstance(application)!!.mUltimaComidaDao()
        val repository = UltimaComidaRepository(dao!!)
        val factory = UltimaComidaViewModelFactory(repository)
        ultimaComidaViewModel = ViewModelProvider(this, factory).get(UltimaComidaViewModel::class.java)
        binding.ultimaComidaViewModel = ultimaComidaViewModel
        binding.lifecycleOwner = this
        displayUltimaComida()
        initRecyclers()
    }


    private fun initRecyclers(){
        binding.agregarComidaActivityFab.setOnClickListener { openDialog() }
        binding.agregarComidaActivityHistorialRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        displayAllComidas()
    }

    private fun displayAllComidas(){
        ultimaComidaViewModel.comidas.observe(this, {
            if (it.isEmpty()) {
                binding.sinCompraCv.visibility = View.VISIBLE
            } else {
                binding.sinCompraCv.visibility = View.GONE
                binding.agregarComidaActivityHistorialRv.adapter = ComidaAdapter(it) { comida: UltimaComidaEntity -> addComida(comida) }
            }
        })
    }

    private fun openDialog() {
        val fragment = DialogAgregarComida.newInstance { comida: UltimaComidaEntity -> addComida(comida) }
        fragment.show(supportFragmentManager, "addComidas")
    }

    private fun displayUltimaComida(){
        ultimaComidaViewModel.ultimaComida.observe(this, {
            binding.agregarComidaActivityUltimaCompraDescriTv.text = it.descripcion ?: "-"
            binding.agregarComidaActivityUltimaCompraPresentacionTv.text = resources.getString(R.string.ultima_compra_presentacion, it.presentacion
                    ?: "-")
            binding.agregarComidaActivityUltimaCompraTituloTv.text = resources.getString(R.string.ultima_compra, it.nombre
                    ?: "-", it.fecha ?: "-")
        })
    }

    private fun addComida(comidaEntity: UltimaComidaEntity){
        ultimaComidaViewModel.insert(UltimaComidaEntity(null, comidaEntity.nombre, comidaEntity.descripcion, comidaEntity.fecha, comidaEntity.presentacion))
    }
}