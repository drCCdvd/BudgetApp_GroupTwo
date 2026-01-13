package com.example.budgetapp_grouptwo.model.datastorage

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.Goal
import kotlinx.serialization.SerializationException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okio.ByteString.Companion.readByteString
import java.io.InputStream
import java.io.OutputStream

object CashflowDataSettingsSerializer : Serializer<CashflowDataSettings> {

    override val defaultValue: CashflowDataSettings = CashflowDataSettings(
        regularEarnings = 0.0,
        regularExpenses = 0.0,
        cashFlows = listOf<Cash>(),
        goals = listOf<Goal>(),
    )

    override suspend fun readFrom(input: InputStream): CashflowDataSettings {
        try {
            return Json.decodeFromString<CashflowDataSettings>(CashflowDataSettings.serializer(),
                input.readBytes().decodeToString()
            )
        }catch (e: SerializationException){
            throw CorruptionException("Unable to read settings", e);
        }
    }

    override suspend fun writeTo(t: CashflowDataSettings, output: OutputStream){
        output.write(
            Json.encodeToString(
                CashflowDataSettings.serializer(),
                t)
                .encodeToByteArray()
        )
    }
}