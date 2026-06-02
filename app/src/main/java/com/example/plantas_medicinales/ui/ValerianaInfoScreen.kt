package com.example.plantas_medicinales.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
fun ValerianaInfoScreen(
    planta: Planta,
    onBack: () -> Unit,
    onHomeClick: () -> Unit,
    onVideosClick: () -> Unit
) {
    val verticalScroll = rememberScrollState()
    var selectedTab by remember { mutableStateOf("plantas") }

    Scaffold(
        containerColor = Color(0xFF000000),
        bottomBar = {
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
                    onClick = { selectedTab = "plantas"; onBack() }
                )
                NavItem(
                    icon = Icons.Filled.Home,
                    label = "Inicio",
                    selected = selectedTab == "home",
                    onClick = { selectedTab = "home"; onHomeClick() }
                )
                NavItem(
                    icon = Icons.Filled.PlayCircle,
                    label = "Videos",
                    selected = selectedTab == "videos",
                    onClick = { selectedTab = "videos"; onVideosClick() }
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(verticalScroll)
        ) {

            // ── Hero: imagen + gradiente + título ──────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = planta.imagen_url,
                    contentDescription = planta.nombre_comun,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Gradiente oscuro en la parte inferior
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xCC000000),
                                    Color(0xFF000000)
                                ),
                                startY = 80f
                            )
                        )
                )

                // Botón regresar
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(8.dp)
                        .background(Color(0x80000000), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.White
                    )
                }

                // Botón guardar
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .background(Color(0x80000000), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.BookmarkBorder,
                        contentDescription = "Guardar",
                        tint = Color.White
                    )
                }

                // Título sobre la imagen (parte inferior del hero)
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = planta.nombre_comun,
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = planta.nombre_cientifico,
                        color = Color(0xFF95D5B2),
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            // ── Chips de categoría ──────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF000000))
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Sedante Natural", "Ansiolítico", "Relajante").forEach { tag ->
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF1A2E22), RoundedCornerShape(20.dp))
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = tag,
                            color = Color(0xFF52B788),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // ── Contenido principal ────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {

                // Usos Tradicionales
                InfoSectionTitle(emoji = "🌿", titulo = "Usos Tradicionales")
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = planta.descripcion_uso,
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Dosificación
                InfoCard(
                    borderColor = Color(0xFF52B788),
                    bgColor = Color(0xFF0A1F14),
                    emoji = "💊",
                    titulo = "Dosificación Recomendada",
                    tituloColor = Color(0xFF52B788)
                ) {
                    InfoBullet(
                        icono = "🍵",
                        texto = "Infusión: 2–3 g de raíz seca en agua caliente (90 °C). Reposar tapado 10 minutos. Tomar 30 min antes de dormir, hasta 2 veces al día."
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoBullet(
                        icono = "🧪",
                        texto = "Extracto estandarizado: 300–600 mg, entre 30 y 60 minutos antes de acostarse. No exceder las 6 semanas de uso continuo."
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contraindicaciones
                InfoCard(
                    borderColor = Color(0xFFB7952A),
                    bgColor = Color(0xFF1E1800),
                    emoji = "⚠️",
                    titulo = "Contraindicaciones",
                    tituloColor = Color(0xFFD4AC3A)
                ) {
                    Text(
                        text = "No se recomienda durante el embarazo ni la lactancia. Evitar en niños menores de 3 años sin supervisión médica. No usar por más de 6 semanas seguidas sin descanso. Abstenerse de conducir o manejar maquinaria pesada tras su consumo.",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Interacciones
                InfoCard(
                    borderColor = Color(0xFF8B2020),
                    bgColor = Color(0xFF1A0A0A),
                    emoji = "⚕️",
                    titulo = "Interacciones Farmacológicas",
                    tituloColor = Color(0xFFD44A4A)
                ) {
                    Text(
                        text = "Puede potenciar el efecto de sedantes, benzodiacepinas y barbitúricos, aumentando el riesgo de somnolencia excesiva. Puede interactuar con anticoagulantes orales. Se debe evitar su combinación con alcohol, ya que amplifica los efectos depresores del sistema nervioso central.",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botón regresar al catálogo
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1A2E22)
                    )
                ) {
                    Text(
                        text = "← Regresar al Catálogo",
                        color = Color(0xFF52B788),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ── Composables de apoyo ──────────────────────────────────────

@Composable
fun InfoSectionTitle(emoji: String, titulo: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = titulo,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun InfoCard(
    borderColor: Color,
    bgColor: Color,
    emoji: String,
    titulo: String,
    tituloColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(14.dp))
    ) {
        // Borde izquierdo de color
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(borderColor, RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp))
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = emoji, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = titulo,
                    color = tituloColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
fun InfoBullet(icono: String, texto: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(text = icono, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = texto,
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 13.sp,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}
