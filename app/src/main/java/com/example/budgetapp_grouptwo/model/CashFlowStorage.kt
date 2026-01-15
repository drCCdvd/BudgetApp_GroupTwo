package com.example.budgetapp_grouptwo

import android.content.Context
import com.example.budgetapp_grouptwo.model.Cash
import com.example.budgetapp_grouptwo.model.Expense
import com.example.budgetapp_grouptwo.model.ExpenseType
import com.example.budgetapp_grouptwo.model.Income
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
// day la data-lag
// object dung SharedPreferences +JSON
//da co save (gemme)
object CashFlowStorage {
    private const val PREF_NAME = "budget_prefs"
    private const val KEY_EARNINGS = "regular_earnings"
    private const val KEY_EXPENSES = "regular_expenses"
    private const val KEY_CASHFLOWS = "variable_cashflows"

    // ---------------------------
    // Regular earnings/expenses
    // ---------------------------
    // gemme  funtion til earnings and exoense
    // voi SharedPreferencesla la "update" saveRegularEarnings /saveRegularExpenses
    fun saveRegularEarnings(context: Context, amount: Double) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_EARNINGS, amount.toString()).apply()
    }

    fun saveRegularExpenses(context: Context, amount: Double) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_EXPENSES, amount.toString()).apply()
    }
    // hente (load  it is not ask i tak but i still make more task
    // load forRegularEarnings and loadRegularExpenses
    fun loadRegularEarnings(context: Context): Double {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        // Nếu trước đây bạn lưu Float/Double thì getString sẽ crash -> dùng all[key]
        val raw = prefs.all[KEY_EARNINGS] ?: return 0.0

        val value = when (raw) {
            is String -> raw.toDoubleOrNull() ?: 0.0
            is Float -> raw.toDouble()
            is Double -> raw
            is Int -> raw.toDouble()
            is Long -> raw.toDouble()
            else -> 0.0
        }
        // migrate: lưu lại thành String để sau này đọc getString cũng không crash
        if (raw !is String) {
            prefs.edit().putString(KEY_EARNINGS, value.toString()).apply()
        }
        return value
    }

    fun loadRegularExpenses(context: Context): Double {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val raw = prefs.all[KEY_EXPENSES] ?: return 0.0

        val value = when (raw) {
            is String -> raw.toDoubleOrNull() ?: 0.0
            is Float -> raw.toDouble()
            is Double -> raw
            is Int -> raw.toDouble()
            is Long -> raw.toDouble()
            else -> 0.0
        }
        // migrate: lưu lại thành String để sau này đọc getString cũng không crash
        if (raw !is String) {
            prefs.edit().putString(KEY_EXPENSES, value.toString()).apply()
        }
        return value
    }

// slette(delete) for regularRegularEarnings and RegularExpense
    fun deleteRegularEarnings(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_EARNINGS).apply()
    }

    fun deleteRegularExpenses(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_EXPENSES).apply()
    }

    fun clearAllPrefs(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    // ---------------------------
    // Variable cashflows list (JSON)
    // ---------------------------

    fun saveNewCashFlow(context: Context, cash: Cash) {
        val current = loadCashFlow(context).toMutableList()
        current.add(cash)
        saveCashFlowList(context, current)
    }
// had implemented opret loadCashFlow
    fun loadCashFlow(context: Context): List<Cash> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val jsonString = prefs.getString(KEY_CASHFLOWS, null) ?: return emptyList()

        return try {
            val arr = JSONArray(jsonString)
            buildList {
                for (i in 0 until arr.length()) {
                    val obj = arr.getJSONObject(i)

                    val kind = obj.getString("kind") // "INCOME" or "EXPENSE"
                    val id = obj.getInt("id")
                    val name = obj.getString("name")
                    val amount = obj.getDouble("amount")
                    val date = LocalDate.parse(obj.getString("date"))
                    when (kind) {
                        "INCOME" -> {
                            add(Income(id, name, amount, date))
                        }
                        "EXPENSE" -> {
                            // 1. Lấy chuỗi tên loại chi phí từ JSON
                            val typeString = obj.getString("expenseType")

                            // 2. Chuyển đổi sang ExpenseType một cách an toàn
                            val expenseType = try {
                                ExpenseType.valueOf(typeString)
                            } catch (e: Exception) {
                                // Nếu dữ liệu cũ bị lỗi hoặc không khớp, mặc định dùng FIXED
                                ExpenseType.FIXED
                            }

                            // 3. Gọi đúng Constructor của lớp Expense (id, name, amount, date, type)
                            add(Expense(
                                id = id,
                                name = name,
                                amount = amount,
                                date = date,
                                type = expenseType
                            ))
                        }
                    }
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }
// nhiem vu da lam  . opret saveCashFlowList
    private fun saveCashFlowList(context: Context, list: List<Cash>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val arr = JSONArray()

        for (cash in list) {
            val obj = JSONObject().apply {
                put("id", cash.id)
                put("name", cash.name)
                put("amount", cash.amount)
                put("date", cash.dateAdded.toString())

                when (cash) {
                    is Income -> put("kind", "INCOME")
                    is Expense -> {
                        put("kind", "EXPENSE")
                        put("expenseType", cash.type.name)
                    }
                }
            }
            arr.put(obj)
        }

        prefs.edit().putString(KEY_CASHFLOWS, arr.toString()).apply()
    }
// variable
    fun deleteCashFlow(context: Context, id: Int) {
        val updated = loadCashFlow(context).filterNot { it.id == id }
        saveCashFlowList(context, updated)
    }
//variable
    fun updateCashFlow(context: Context, updated: Cash) {
        val list = loadCashFlow(context).toMutableList()
        val idx = list.indexOfFirst { it.id == updated.id }
        if (idx != -1) {
            list[idx] = updated
            saveCashFlowList(context, list)
        }
    }
//variable
    fun clearCashFlows(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_CASHFLOWS).apply()
    }
    //add more update
    fun updateRegularCashFlow(context: Context, earnings: Double, expenses: Double) {
        saveRegularEarnings(context, earnings)
        saveRegularExpenses(context, expenses)
    }
    fun nextCashFlowId(context: Context): Int {
        val list = loadCashFlow(context)
        return (list.maxOfOrNull { it.id } ?: 0) + 1
    }

}
