@ -0,0 +1,63 @@
import android.content.Context

class RegularCashFlowClass {
    private var earnings: Double = 0.0
    private var expenses: Double = 0.0
    private var id: String = ""
    private var createdDate: Long = 0L
    private var frequency: String = "monthly" // monthly, weekly, yearly

    fun getRegularEarnings(): Double {
        return earnings
    }

    fun setRegularEarnings(amount: Double) {
        this.earnings = amount
    }

    fun getRegularExpenses(): Double {
        return expenses
    }
    
    fun setRegularExpenses(amount: Double) {
        this.expenses = amount
    }

    fun getId(): String {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getFrequency(): String {
        return frequency
    }

    fun setFrequency(frequency: String) {
        this.frequency = frequency
    }
    
    fun getCreatedDate(): Long {
        return createdDate
    }
    
    fun setCreatedDate(date: Long) {
        this.createdDate = date
    }
    
    fun saveRegularCashFlow(context: Context): Boolean {
        return if (earnings >= 0 && expenses >= 0 && id.isNotEmpty()) {
            //  StorageManager  skal bruges til at gemme som JSON lokalt
            // StorageManager.saveRegularCashFlow(context, this)
            true
        } else {
            false
        }
    }
    
    fun getNetCashFlow(): Double {
        return earnings - expenses
    }
}