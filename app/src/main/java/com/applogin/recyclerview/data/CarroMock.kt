package com.applogin.recyclerview.data

import com.applogin.recyclerview.model.Carro

class CarroMock {
    var listaCarros = ArrayList<Carro>()

    init {
        for (i in 0..5) {
            listaCarros.add(Carro(i, i.toString()))
        }
    }
}