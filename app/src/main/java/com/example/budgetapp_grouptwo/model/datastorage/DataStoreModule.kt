package com.example.budgetapp_grouptwo.model.datastorage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.budgetapp_grouptwo.model.datastorage.CashflowDataSettings

//Enable datastore to access the data file
val Context.budgetAppUserData: DataStore<CashflowDataSettings> by dataStore(
    fileName = "BudgetAppUserDetails.json",
    serializer = CashflowDataSettingsSerializer,
)