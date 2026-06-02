package com.example.plantas_medicinales.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantas_medicinales.R

@Composable
fun HomeScreen(
    onPlantasClick: () -> Unit,
    onVideosClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("home") }

    Scaffold(
        containerColor = Color(0xFF000000),
        bottomBar = {
            // ── Barra navegación inferior ───────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0D1B12))
                    .navigationBarsPadding()
                    .padding(vertical = 8.dp),
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
                    onClick = { selectedTab = "home" }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
        ) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.home_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradiente oscuro encima
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x551B4332),
                                Color(0x991B4332),
                                Color(0xEE1B4332)
                            )
                        )
                    )
            )

            // Contenido centrado
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "BOTÁNICA LOCAL",
                    color = Color(0xFF95D5B2),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Plantas\nMedicinales",
                    color = Color.White,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    lineHeight = 48.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Descubre el poder curativo de la naturaleza tabasqueña y la sabiduría de la herbolaria.",
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        }
    }
}

@Composable
fun NavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val iconColor by animateColorAsState(
        targetValue = if (selected) Color(0xFF52B788) else Color(0xFF4A6358),
        animationSpec = tween(durationMillis = 300),
        label = "iconColor"
    )

    val scale by animateFloatAsState(
        targetValue = if (selected) 1.15f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .scale(scale)
                .then(
                    if (selected) Modifier.background(
                        color = Color(0xFF1B4332),
                        shape = RoundedCornerShape(12.dp)
                    ).padding(horizontal = 16.dp, vertical = 4.dp)
                    else Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = label,
            color = iconColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}