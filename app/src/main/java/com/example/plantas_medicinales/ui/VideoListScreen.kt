package com.example.plantas_medicinales.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

// ── Pantalla principal ────────────────────────────────────────
@Composable
fun VideoListScreen(
    plantas: List<Planta>,
    isLoading: Boolean = false,
    onVideoClick: (Planta) -> Unit,
    onHomeClick: () -> Unit,
    onPlantasClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("videos") }

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
                    onClick = {
                        selectedTab = "plantas"
                        onPlantasClick()
                    }
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
                    onClick = { selectedTab = "videos" }
                )
            }
        }
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
        ) {
            // ── Top Bar (menú + título + búsqueda) ──────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF000000))
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* abrir menú lateral más adelante */ }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menú",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "Raíz Verde",
                    color = Color(0xFF95D5B2),
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.5.sp
                )

                IconButton(onClick = { /* abrir búsqueda más adelante */ }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            // ── Línea separadora sutil ──────────────────────────
            HorizontalDivider(
                color = Color(0xFF1F1F1F),
                thickness = 1.dp
            )

            // ── Header (Videoteca / título / descripción) ───────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF000000))
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 20.dp)
            ) {
                Text(
                    text = "VIDEOTECA",
                    color = Color(0xFF52B788),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Exploración Botánica",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Descubre las propiedades, usos y el cultivo de plantas medicinales a través de nuestra colección de documentales cortos.",
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
                // ── Lista de videos ─────────────────────────────────
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(plantas) { planta ->
                        VideoCard(
                            planta = planta,
                            onClick = { onVideoClick(planta) }
                        )
                    }
                }
            }
        }
    }
}

// ── Card individual ───────────────────────────────────────────
@Composable
fun VideoCard(
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
        Column {
            // Thumbnail con play
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = planta.imagen_url,
                    contentDescription = planta.nombre_comun,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    error = painterResource(id = when(planta.id) {
                        1 -> R.drawable.thumb_valeriana
                        2 -> R.drawable.thumb_manzanilla
                        3 -> R.drawable.thumb_chaya
                        4 -> R.drawable.thumb_epazote
                        else -> R.drawable.thumb_chaya
                    })
                )

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            color = Color(0x9952B788),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Reproducir",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF1A2E22),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = when(planta.id) {
                            1 -> "Sedante Natural"
                            2 -> "Digestivo"
                            3 -> "Nutrición"
                            4 -> "Culinario Medicinal"
                            else -> "Herbolaria"
                        },
                        color = Color(0xFF52B788),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = planta.nombre_comun,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = planta.descripcion_uso,
                    color = Color.White.copy(alpha = 0.55f),
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }
    }
}
