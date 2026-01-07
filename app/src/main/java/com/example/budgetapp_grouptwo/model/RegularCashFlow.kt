@ -0,0 +1,63 @@
import android.content.Context

data class RegularCashFlow (
    var earnings: Double = 0.0,
    var expenses: Double = 0.0,
    var id: String = "",
    var createdDate: Long = System.currentTimeMillis(),
    var frequency: String = "monthly" // monthly, weekly, yearly
) {

    // i stedet for getNetCashFlow() metode:
    val netCashFlow: Double
        get() = earnings - expenses

    fun saveRegularCashFlow(context: Context): Boolean {
        return if (earnings >= 0 && expenses >= 0 && id.isNotEmpty()) {
            // StorageManager  skal bruges til at gemme som JSON lokalt
            // StorageManager.saveRegularCashFlow(context, this)
            true
        } else {
            false
        }
    }
}