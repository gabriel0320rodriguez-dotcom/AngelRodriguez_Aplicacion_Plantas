package com.example.plantas_medicinales.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantas_medicinales.model.Planta
import com.example.plantas_medicinales.network.supabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlantasViewModel : ViewModel() {
    private val _plantas = MutableStateFlow<List<Planta>>(emptyList())
    val plantas: StateFlow<List<Planta>> = _plantas

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchPlantas()
    }

    fun fetchPlantas() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Descarga estricta desde Supabase aplicando el filtro
                // para traer solo los IDs 1, 2, 3 y 4
                val result = supabase.from("plantas").select {
                    filter {
                        isIn("id", listOf(1, 2, 3, 4))
                    }
                }.decodeList<Planta>()

                _plantas.value = result
            } catch (e: Exception) {
                // Si falla, imprimimos el error real en consola y evitamos datos falsos
                e.printStackTrace()
                _plantas.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}