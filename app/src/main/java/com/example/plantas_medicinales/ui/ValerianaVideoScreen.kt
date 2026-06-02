package com.example.plantas_medicinales.ui

import com.example.plantas_medicinales.model.Planta
import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

data class PuntoClave(
    val numero: Int,
    val titulo: String,
    val descripcion: String
)



@Composable
fun ValerianaVideoScreen(
    planta: Planta,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val activity = context as? Activity

    var isFullscreen by remember { mutableStateOf(false) }
    var fullscreenView by remember { mutableStateOf<View?>(null) }

    val youtubeId = "bfFu1I8ePiM"
    val categoria = "Sedante Natural"
    val tituloSeccion = planta.nombre_comun
    val descripcionPrincipal = planta.descripcion_uso

    val puntosClave = listOf(
        PuntoClave(
            1,
            "Sedante Natural",
            "Actúa directamente sobre el sistema nervioso para calmar la irritabilidad, aliviar dolores de cabeza por tensión y relajar la musculatura de todo el cuerpo."
        ),
        PuntoClave(
            2,
            "Remedio contra el Insomnio",
            "Induce un sueño profundo y de alta calidad. Es el remedio indicado para acortar el tiempo que tardas en dormir y evitar interrupciones durante la noche."
        ),
        PuntoClave(
            3,
            "Preparación y Precauciones",
            "La mejor forma de aprovecharla es mediante una infusión de su raíz. Para mantener su eficacia, no debe mezclarse con cafeína ni consumirse por más de seis semanas seguidas."
        )
    )

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

                Text(
                    text = descripcionPrincipal,
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                puntosClave.forEach { punto ->
                    PuntoClaveCard(punto = punto)
                    Spacer(modifier = Modifier.height(12.dp))
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

@Composable
fun PuntoClaveCard(punto: PuntoClave) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0D0D0D)
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = Color(0xFF1F2E24)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = Color(0xFF1B4332),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = punto.numero.toString(),
                    color = Color(0xFF95D5B2),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = punto.titulo,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = punto.descripcion,
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }
    }
}

@Composable
fun YouTubePlayerCompose(
    videoId: String,
    modifier: Modifier = Modifier,
    onEnterFullscreen: (View) -> Unit = {},
    onExitFullscreen: () -> Unit = {}
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            // Aquí está la corrección: pasamos 'ctx' al Builder
            val iFramePlayerOptions = IFramePlayerOptions.Builder(ctx)
                .controls(1)
                .fullscreen(1)
                .build()

            YouTubePlayerView(ctx).apply {
                enableAutomaticInitialization = false
                lifecycleOwner.lifecycle.addObserver(this)

                addFullscreenListener(object : FullscreenListener {
                    override fun onEnterFullscreen(
                        fullscreenView: View,
                        exitFullscreen: () -> Unit
                    ) {
                        onEnterFullscreen(fullscreenView)
                    }

                    override fun onExitFullscreen() {
                        onExitFullscreen()
                    }
                })

                initialize(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(videoId, 0f)
                        }
                    },
                    iFramePlayerOptions
                )
            }
        }
    )
}