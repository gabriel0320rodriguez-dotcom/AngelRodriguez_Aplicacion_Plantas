package com.example.plantas_medicinales.ui

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
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.plantas_medicinales.model.Planta

@Composable
fun EpazoteVideoScreen(
    planta: Planta,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val activity = context as? Activity

    var isFullscreen by remember { mutableStateOf(false) }
    var fullscreenView by remember { mutableStateOf<View?>(null) }

    val youtubeId = "ip8vuVdcrkA"
    val categoria = "Culinario Medicinal"
    val tituloSeccion = planta.nombre_comun
    val descripcionPrincipal = planta.descripcion_uso

    val puntosClave = listOf(
        PuntoClave(
            1,
            "Antiparasitario Natural",
            "Su uso más reconocido históricamente es la eliminación de parásitos intestinales como áscaris y tenias. El ascaridol, compuesto activo del epazote, actúa directamente sobre los parásitos sin dañar el organismo cuando se usa en dosis adecuadas."
        ),
        PuntoClave(
            2,
            "Digestivo y Carminativo Poderoso",
            "Alivia de forma muy efectiva los gases, la distensión abdominal, las náuseas y los cólicos estomacales. Por eso es un ingrediente tradicional en platillos con leguminosas como los frijoles: reduce directamente su efecto gasificante."
        ),
        PuntoClave(
            3,
            "Antimicrobiano y Antifúngico",
            "Estudios científicos confirman sus propiedades antibacterianas y antifúngicas. Se usa en la medicina tradicional para tratar infecciones cutáneas menores, combatir hongos y fortalecer las defensas naturales del organismo."
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
                    .navigationBarsPadding()
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
