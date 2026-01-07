package com.example.budgetapp_grouptwo.ui.screens


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bugetapp_grouptwo.CashFlowStorage

import com.example.bugetapp_grouptwo.RegularCashFlow


@Composable
fun FixedEntryScreen(onBack:()-> Unit) {
    val context = LocalContext.current
    // 1. Khai báo state
    var isIncome by remember { mutableStateOf(true) }
    // Dùng state này để quản lý text nhập vào
    var amountText by remember { mutableStateOf("") }

    // 2. Chỉ load dữ liệu TỪ BỘ NHỚ một lần duy nhất khi màn hình mở lên
    LaunchedEffect(Unit) {
        // Load từ SharedPreferences vào Object lưu trữ tạm
        val earnings = CashFlowStorage.loadRegularEarnings(context)
        val expenses = CashFlowStorage.loadRegularExpenses(context)
        RegularCashFlow.setRegularEarnings(earnings)
        RegularCashFlow.setRegularExpense(expenses)

        // Khởi tạo giá trị ban đầu cho ô nhập liệu (mặc định là Income)
        val initialValue = if (earnings > 0) earnings.toString() else ""
        amountText = initialValue
    }

    // 3. Khi người dùng bấm chuyển đổi Indtægt/Udgift
    // Chúng ta cần cập nhật ô nhập liệu theo loại mới
    LaunchedEffect(isIncome) {
        val currentValue = if (isIncome) RegularCashFlow.getRegularEarnings()
        else RegularCashFlow.getRegularExpenses()

        // Chỉ hiện số nếu > 0, ngược lại để trống để người dùng dễ nhập
        amountText = if (currentValue > 0.0) currentValue.toString() else ""
    }


    val bg = Color(0xFFF7F7FB)
    val fieldBg = Color(0xFFEFEFF3)
    val primary = Color(0xFFBFD3FF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Top bar đơn giản (back icon + title)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Bắt đầu sửa từ đây
            IconButton(onClick = onBack) { // Thêm dòng này
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(22.dp)
                )
            } // Đóng ngoặc nhọn ở đây
            // Kết thúc sửa


            Spacer(Modifier.weight(1f))
            Text(
                text = "Faste",
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.weight(1f))
            Spacer(Modifier.size(22.dp))
        }

        Spacer(Modifier.height(12.dp))

        // Label: Beløb
        Text("Beløb", color = Color(0xFF777777))
        Spacer(Modifier.height(8.dp))

        // Amount field (giống mockup: nền xám nhạt, bo tròn)
        OutlinedTextField(
            value = amountText,
            onValueChange = { amountText = it },
            placeholder = { Text("0 kr.") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .width(240.dp)
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = fieldBg,
                unfocusedContainerColor = fieldBg,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(14.dp))

        // Kategori label
        Text("Kategori", color = Color(0xFF777777))
        Spacer(Modifier.height(8.dp))

        // 2 pill buttons Indtægt / Udgift
        Row(
            modifier = Modifier.width(260.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SegmentedPill(
                text = "Indtægt",
                selected = isIncome,
                selectedColor = primary,
                onClick = { isIncome = true }
            )
            SegmentedPill(
                text = "Udgift",
                selected = !isIncome,
                selectedColor = primary,
                onClick = { isIncome = false }
            )
        }

        Spacer(Modifier.height(20.dp))

        // Checkbox: Gentag hver måned (UI thôi)
        var repeatMonthly by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier.width(260.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = repeatMonthly,
                onCheckedChange = { repeatMonthly = it }
            )
            Spacer(Modifier.width(6.dp))
            Text("Gentag hver måned", color = Color(0xFF777777))
        }

        Spacer(Modifier.height(22.dp))

        // Save button giống mockup
        Button(
            onClick = {
                val parsed = amountText.replace(",", ".").trim().toDoubleOrNull()

                if (parsed == null) {
                    Toast.makeText(context, "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                // ✅ Ticket logic:
                if (isIncome) {
                    RegularCashFlow.setRegularEarnings(parsed)
                    CashFlowStorage.saveRegularEarnings(context, parsed)
                } else {
                    RegularCashFlow.setRegularExpense(parsed)
                    CashFlowStorage.saveRegularExpenses(context, parsed)
                }

                Toast.makeText(context, "Đã lưu!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .width(260.dp)
                .height(54.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primary)
        ) {
            Text("Gem", color = Color(0xFF4B4B4B))
        }
    }
}

@Composable
private fun RowScope.SegmentedPill(
    text: String,
    selected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit
) {
    val bg = if (selected) selectedColor else Color(0xFFEFEFF3)
    val fg = if (selected) Color.White else Color(0xFF333333)

    Box(
        modifier = Modifier
            .height(40.dp)
            .weight(1f)
            .background(bg, RoundedCornerShape(20.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = fg, style = MaterialTheme.typography.labelLarge)
    }
}

