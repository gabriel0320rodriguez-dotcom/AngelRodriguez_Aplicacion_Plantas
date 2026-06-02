package com.example.plantas_medicinales.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantas_medicinales.model.Planta
import com.example.plantas_medicinales.network.supabase
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlantasViewModel : ViewModel() {
    private val _plantas = MutableStateFlow<List<Planta>>(emptyList())
    val plantas: StateFlow<List<Planta>> = _plantas

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val plantasRespaldo = listOf(
        Planta(1, "Valeriana", "Valeriana officinalis", "Reconocida por sus potentes propiedades sedantes y relajantes.", "https://images.unsplash.com/photo-1596702675204-da32a353683a"),
        Planta(2, "Manzanilla", "Matricaria chamomilla", "Una infusión clásica con propiedades digestivas y calmantes.", "https://images.unsplash.com/photo-1515544832961-29244a5d47a6"),
        Planta(3, "Chaya", "Cnidoscolus aconitifolius", "El árbol espinaca Maya, excepcionalmente rico en nutrientes.", "https://images.unsplash.com/photo-1628155930542-3c7a64e2c833"),
        Planta(4, "Epazote", "Dysphania ambrosioides", "Hierba aromática de sabor profundo, fundamental en la medicina tradicional.", "https://images.unsplash.com/photo-1596702675204-da32a353683a")
    )

    init {
        fetchPlantas()
    }

    fun fetchPlantas() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = supabase.from("plantas")
                    .select()
                    .decodeList<Planta>()
                
                if (result.isNotEmpty()) {
                    _plantas.value = result
                } else {
                    _plantas.value = plantasRespaldo
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _plantas.value = plantasRespaldo
            } finally {
                _isLoading.value = false
            }
        }
    }
}
