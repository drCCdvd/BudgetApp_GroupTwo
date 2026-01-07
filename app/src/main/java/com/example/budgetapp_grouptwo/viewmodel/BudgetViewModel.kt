import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.budgetapp_grouptwo.model.RegularCashFlow


class BudgetViewModel: ViewModel() {
    // Instans til klassen altså en 'state', for at Compose ved hvornår der skal ske noget
    var cashFlow by mutableStateOf(RegularCashFlow(id = "1"))
        private set

    fun updateEarnings(newAmount:String) {
        val amount = newAmount.toDoubleOrNull() ?: 0.0
        cashFlow = cashFlow.copy(earnings = amount)
    }

    fun updateExpenses(newAmount:String) {
        val amount = newAmount.toDoubleOrNull() ?: 0.0
        cashFlow = cashFlow.copy(expenses = amount)
    }
}