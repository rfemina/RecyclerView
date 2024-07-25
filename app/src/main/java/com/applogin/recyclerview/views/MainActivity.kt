package com.applogin.recyclerview.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.applogin.recyclerview.R
import com.applogin.recyclerview.adapter.CarroListAdapter
import com.applogin.recyclerview.data.CarroMock
import com.applogin.recyclerview.databinding.ActivityMainBinding
import com.applogin.recyclerview.model.Carro

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CarroListAdapter
    private lateinit var mock: CarroMock
    private var pos = -1

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        mock = CarroMock()
        adapter =
            CarroListAdapter(mock.listaCarros, CarroListAdapter.OnClickListener { carro ->
                pos = pesquisaPosicao(carro.id)
                binding.editModelo.setText(mock.listaCarros[pos].modelo)
            })
        binding.recyclerView.adapter = adapter

        binding.buttonInserir.setOnClickListener {
            val modelo = binding.editModelo.text.toString().toInt()
            mock.listaCarros.add(Carro(modelo, modelo.toString()))
            adapter.notifyDataSetChanged()
        }
        binding.buttonEditar.setOnClickListener {
            if (pos >= 0) {
                val modelo = binding.editModelo.text.toString()
                mock.listaCarros[pos].modelo = modelo
                mock.listaCarros[pos].id = modelo.toInt()
                pos = -1
                adapter.notifyDataSetChanged()
            }
        }
        binding.buttonExcluir.setOnClickListener {
            if (pos >= 0) {
                mock.listaCarros.removeAt(pos)
                pos = -1
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun pesquisaPosicao(id: Int): Int {
        for (i in 0..mock.listaCarros.size) {
            if (mock.listaCarros[i].id == id) {
                return i
            }
        }
        return -1
    }
}