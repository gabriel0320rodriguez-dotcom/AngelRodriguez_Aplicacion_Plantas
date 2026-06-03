package com.example.plantas_medicinales.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.plantas_medicinales.R
import com.example.plantas_medicinales.model.Planta

@Composable
fun PlantListScreen(
    plantas: List<Planta>,
    isLoading: Boolean = false,
    onPlantaClick: (Planta) -> Unit,
    onHomeClick: () -> Unit,
    onVideosClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("plantas") }

    Scaffold(
        containerColor = Color(0xFF000000),
        bottomBar = {
            // ── Barra navegación inferior ───────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF000000))
                    .navigationBarsPadding()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavItem(
                    icon = Icons.Filled.LocalFlorist,
                    label = "Plantas",
                    selected = selectedTab == "plantas",
                    onClick = { selectedTab = "plantas" }
                )
                NavItem(
                    icon = Icons.Filled.Home,
                    label = "Inicio",
                    selected = selectedTab == "home",
                    onClick = {
                        selectedTab = "home"
                        onHomeClick()
                    }
                )
                NavItem(
                    icon = Icons.Filled.PlayCircle,
                    label = "Videos",
                    selected = selectedTab == "videos",
                    onClick = {
                        selectedTab = "videos"
                        onVideosClick()
                    }
                )
            }
        }
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF000000))
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp, bottom = 20.dp)
            ) {
                Text(
                    text = "Catálogo Herbolario",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Explora nuestra colección de plantas medicinales y descubre sus propiedades curativas tradicionales.",
                    color = Color.White.copy(alpha = 0.55f),
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }

            if (isLoading && plantas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF52B788))
                }
            } else {
                // Lista
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(plantas) { planta ->
                        PlantCard(
                            planta = planta,
                            onClick = { onPlantaClick(planta) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlantCard(
    planta: Planta,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0D0D0D)
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = Color(0xFF2A2A2A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Marco + imagen circular
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(
                        color = Color(0xFF1A1A1A),
                        shape = CircleShape
                    )
                    .padding(3.dp)
            ) {
                Log.d("DEBUG_COIL", "URL recibida para ${planta.nombre_comun}: '${planta.imagen_url}'")
                AsyncImage(
                    model = planta.imagen_url,
                    contentDescription = planta.nombre_comun,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    error = painterResource(id = when(planta.id) {
                        1 -> R.drawable.planta_valeriana
                        2 -> R.drawable.planta_manzanilla
                        3 -> R.drawable.planta_chaya
                        4 -> R.drawable.planta_epazote
                        else -> R.drawable.planta_chaya
                    })
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Texto
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = planta.nombre_comun,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = planta.nombre_cientifico,
                    color = Color(0xFF52B788),
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = planta.descripcion_uso,
                    color = Color.White.copy(alpha = 0.55f),
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }

            // Flecha
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.4f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
