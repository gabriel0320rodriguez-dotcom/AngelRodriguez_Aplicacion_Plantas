package com.example.plantas_medicinales.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json


val supabase = createSupabaseClient(
    supabaseUrl = "https://yxyxcxwdwpolxyxsfmlw.supabase.co",
    supabaseKey = "sb_publishable_6Sd_4AlJVoRnPI4RLfwpKQ_O3e7OM6M"
) {
    install(Postgrest)
    defaultSerializer = KotlinXSerializer(Json {
        ignoreUnknownKeys = true
    })
}
