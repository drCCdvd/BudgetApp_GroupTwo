package com.example.budgetapp_grouptwo.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.CashFlow
import com.example.budgetapp_grouptwo.model.ExpenseType
import java.time.LocalDate
import androidx.compose.runtime.State
import com.example.budgetapp_grouptwo.utils.FilterType


class RecentViewModel : ViewModel() {

    private val cashFlow = CashFlow()

    // ERROR PREVENTION: UI observerer en stabil liste
    val recentItems: SnapshotStateList<Cash> = cashFlow.cashFlows

    // ERROR PREVENTION: UI-state til fejlmeddelelser

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        loadDummyDataSafely()
    }

    // ERROR PREVENTION: Dummy-data indlæses i try/catch
    private fun loadDummyDataSafely() {
        try {
            cashFlow.addNewIncome("Løn", 15000.0, LocalDate.now().minusDays(1))
            cashFlow.addNewExpense("Mad", 120.0, ExpenseType.FOOD, LocalDate.now().minusDays(2))
            cashFlow.addNewExpense("Transport", 50.0, ExpenseType.TRANSPORT, LocalDate.now().minusDays(3))
            cashFlow.addNewIncome("Freelance", 2000.0, LocalDate.now().minusDays(4))
            cashFlow.addNewIncome("Freelance", 1000.0, LocalDate.now().minusDays(5))
        } catch (e: Exception) {
            _errorMessage.value = "Kunne ikke indlæse dummy-data"
        }
    }

    fun removeItem(id: Int) {
        // ERROR PREVENTION: Tjek om id findes før vi fjerner
        val exists = recentItems.any { it.id == id }
        if (!exists) {
            _errorMessage.value = "Posten findes ikke længere"
            return
        }

        // ERROR PREVENTION: Beskyt mod fejl i domain-laget
        try {
            cashFlow.removeItem(id)
        } catch (e: Exception) {
            _errorMessage.value = "Kunne ikke fjerne posten"
        }
    }

    private val _filter = mutableStateOf(FilterType.ALL)
    val filter: State<FilterType> = _filter

    fun setFilter(newFilter: FilterType) {
        _filter.value = newFilter
    }

    val filteredItems: List<Cash>
        get() = when (_filter.value) {
            FilterType.ALL -> recentItems
            FilterType.FIXED -> recentItems.filter { it.regularCashFlow }
            FilterType.VARIABLE -> recentItems.filter { !it.regularCashFlow }
        }

}

