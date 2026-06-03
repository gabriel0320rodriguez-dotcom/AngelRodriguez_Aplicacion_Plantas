package com.example.plantas_medicinales.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.example.plantas_medicinales.model.Planta

@Composable
fun ChayaVideoScreen(
    planta: Planta,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val activity = context as? Activity

    var isFullscreen by remember { mutableStateOf(false) }
    var fullscreenView by remember { mutableStateOf<View?>(null) }

    // ── Video ID Restaurado ───────────────────────────────────
    val youtubeId = "UZDKgJWRwNY"
    val categoria = "Nutrición"
    val tituloSeccion = planta.nombre_comun
    val descripcionPrincipal = planta.descripcion_uso

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF000000))
            ) {
                // El componente ahora se jala de CommonComponents.kt
                YouTubePlayerCompose(
                    videoId = youtubeId,
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding(),
                    onEnterFullscreen = { view ->
                        fullscreenView = view
                        isFullscreen = true
                        activity?.requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                    },
                    onExitFullscreen = {
                        fullscreenView = null
                        isFullscreen = false
                        activity?.requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }
                )

                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(8.dp)
                        .background(
                            color = Color(0x80000000),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .background(
                            color = Color(0x80000000),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.BookmarkBorder,
                        contentDescription = "Guardar",
                        tint = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp, bottom = 40.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF1A2E22),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocalFlorist,
                        contentDescription = null,
                        tint = Color(0xFF52B788),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = categoria.uppercase(),
                        color = Color(0xFF52B788),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = tituloSeccion,
                    color = Color(0xFF95D5B2),
                    fontSize = 28.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 34.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0D0D0D), RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFF1F2E24), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = descripcionPrincipal,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A2E22))
                ) {
                    Text(
                        text = "← Volver a la Lista",
                        color = Color(0xFF52B788),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        if (isFullscreen && fullscreenView != null) {
            AndroidView(
                factory = {
                    val parent = fullscreenView?.parent as? ViewGroup
                    parent?.removeView(fullscreenView)
                    fullscreenView!!
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .zIndex(10f)
            )
        }
    }
}
