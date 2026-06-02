package com.example.plantas_medicinales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantas_medicinales.model.Planta
import com.example.plantas_medicinales.ui.ChayaInfoScreen
import com.example.plantas_medicinales.ui.ChayaVideoScreen
import com.example.plantas_medicinales.ui.EpazoteInfoScreen
import com.example.plantas_medicinales.ui.EpazoteVideoScreen
import com.example.plantas_medicinales.ui.HomeScreen
import com.example.plantas_medicinales.ui.ManzanillaInfoScreen
import com.example.plantas_medicinales.ui.ManzanillaVideoScreen
import com.example.plantas_medicinales.ui.PlantListScreen
import com.example.plantas_medicinales.ui.ValerianaInfoScreen
import com.example.plantas_medicinales.ui.ValerianaVideoScreen
import com.example.plantas_medicinales.ui.VideoListScreen
import com.example.plantas_medicinales.viewmodel.PlantasViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val plantasViewModel: PlantasViewModel = viewModel()
                    val plantas by plantasViewModel.plantas.collectAsState()
                    val isLoading by plantasViewModel.isLoading.collectAsState()
                    var currentScreen by remember { mutableStateOf("home") }
                    var selectedPlanta by remember { mutableStateOf<Planta?>(null) }

                    when (currentScreen) {
                        "home" -> HomeScreen(
                            onPlantasClick = { currentScreen = "plantas" },
                            onVideosClick = { currentScreen = "videos" }
                        )
                        "plantas" -> PlantListScreen(
                            plantas = plantas,
                            isLoading = isLoading,
                            onPlantaClick = { planta ->
                                selectedPlanta = planta
                                currentScreen = when (planta.id) {
                                    1 -> "info_valeriana"
                                    2 -> "info_manzanilla"
                                    3 -> "info_chaya"
                                    4 -> "info_epazote"
                                    else -> "plantas"
                                }
                            },
                            onHomeClick = { currentScreen = "home" },
                            onVideosClick = { currentScreen = "videos" }
                        )
                        "videos" -> VideoListScreen(
                            plantas = plantas,
                            isLoading = isLoading,
                            onVideoClick = { planta ->
                                selectedPlanta = planta
                                currentScreen = when (planta.id) {
                                    1 -> "video_valeriana"
                                    2 -> "video_manzanilla"
                                    3 -> "video_chaya"
                                    4 -> "video_epazote"
                                    else -> "videos"
                                }
                            },
                            onHomeClick = { currentScreen = "home" },
                            onPlantasClick = { currentScreen = "plantas" }
                        )
                        "info_valeriana" -> {
                            selectedPlanta?.let { planta ->
                                ValerianaInfoScreen(
                                    planta = planta,
                                    onBack = { currentScreen = "plantas" },
                                    onHomeClick = { currentScreen = "home" },
                                    onVideosClick = { currentScreen = "videos" }
                                )
                            }
                        }
                        "info_manzanilla" -> {
                            selectedPlanta?.let { planta ->
                                ManzanillaInfoScreen(
                                    planta = planta,
                                    onBack = { currentScreen = "plantas" },
                                    onHomeClick = { currentScreen = "home" },
                                    onVideosClick = { currentScreen = "videos" }
                                )
                            }
                        }
                        "info_chaya" -> {
                            selectedPlanta?.let { planta ->
                                ChayaInfoScreen(
                                    planta = planta,
                                    onBack = { currentScreen = "plantas" },
                                    onHomeClick = { currentScreen = "home" },
                                    onVideosClick = { currentScreen = "videos" }
                                )
                            }
                        }
                        "info_epazote" -> {
                            selectedPlanta?.let { planta ->
                                EpazoteInfoScreen(
                                    planta = planta,
                                    onBack = { currentScreen = "plantas" },
                                    onHomeClick = { currentScreen = "home" },
                                    onVideosClick = { currentScreen = "videos" }
                                )
                            }
                        }
                        "video_valeriana" -> {
                            selectedPlanta?.let { planta ->
                                ValerianaVideoScreen(
                                    planta = planta,
                                    onBack = { currentScreen = "videos" }
                                )
                            }
                        }
                        "video_manzanilla" -> {
                            selectedPlanta?.let { planta ->
                                ManzanillaVideoScreen(
                                    planta = planta,
                                    onBackClick = { currentScreen = "videos" }
                                )
                            }
                        }
                        "video_chaya" -> {
                            selectedPlanta?.let { planta ->
                                ChayaVideoScreen(
                                    planta = planta,
                                    onBack = { currentScreen = "videos" }
                                )
                            }
                        }
                        "video_epazote" -> {
                            selectedPlanta?.let { planta ->
                                EpazoteVideoScreen(
                                    planta = planta,
                                    onBack = { currentScreen = "videos" }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}