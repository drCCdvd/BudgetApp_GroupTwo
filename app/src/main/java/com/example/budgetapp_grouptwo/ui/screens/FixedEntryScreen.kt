package com.example.budgetapp_grouptwo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bugetapp_grouptwo.CashFlowStorage
import com.example.bugetapp_grouptwo.RegularCashFlow


@Composable
fun FixedEntryScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    // ---- State ----
    var incomeText by remember { mutableStateOf("") }
    var expenseText by remember { mutableStateOf("") }

    // ---- Load saved values once ----
    LaunchedEffect(Unit) {
        val earnings = CashFlowStorage.loadRegularEarnings(context)
        val expenses = CashFlowStorage.loadRegularExpenses(context)

        RegularCashFlow.setRegularEarnings(earnings)
        RegularCashFlow.setRegularExpense(expenses)

        incomeText = if (earnings > 0.0) earnings.toString() else ""
        expenseText = if (expenses > 0.0) expenses.toString() else ""
    }

    // ---- Colors (giống style cũ) ----
    val bg = Color(0xFFF7F7FB)
    val fieldBg = Color(0xFFEFEFF3)
    val primary = Color(0xFFBFD3FF)
    val textGrey = Color(0xFF777777)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ---- Header: back + title ----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = "Faste",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.weight(1f))
            Spacer(Modifier.size(22.dp))
        }

        Spacer(Modifier.height(18.dp))

        // ---- Line 2: Indtægt ----
        Text("Indtægt", color = textGrey, modifier = Modifier.width(260.dp))
        Spacer(Modifier.height(8.dp))

        // ---- Line 3: field Indtægt ----
        OutlinedTextField(
            value = incomeText,
            onValueChange = { incomeText = it },
            placeholder = { Text("0 kr.") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .width(260.dp)
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = fieldBg,
                unfocusedContainerColor = fieldBg,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(18.dp))

        // ---- Line 4: Udgift ----
        Text("Udgift", color = textGrey, modifier = Modifier.width(260.dp))
        Spacer(Modifier.height(8.dp))

        // ---- Line 5: field Udgift ----
        OutlinedTextField(
            value = expenseText,
            onValueChange = { expenseText = it },
            placeholder = { Text("0 kr.") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .width(260.dp)
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = fieldBg,
                unfocusedContainerColor = fieldBg,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(26.dp))

        // ---- Line 6: Gem ----
        Button(
            onClick = {
                val income = incomeText.replace(",", ".").trim().toDoubleOrNull()
                val expense = expenseText.replace(",", ".").trim().toDoubleOrNull()

                // cho phép để trống (coi như 0)
                val incomeFinal = income ?: if (incomeText.trim().isEmpty()) 0.0 else return@Button Toast
                    .makeText(context, "Indtægt không hợp lệ", Toast.LENGTH_SHORT).show()

                val expenseFinal = expense ?: if (expenseText.trim().isEmpty()) 0.0 else return@Button Toast
                    .makeText(context, "Udgift không hợp lệ", Toast.LENGTH_SHORT).show()

                // Save to in-memory + SharedPreferences
                RegularCashFlow.setRegularEarnings(incomeFinal)
                CashFlowStorage.saveRegularEarnings(context, incomeFinal)

                RegularCashFlow.setRegularExpense(expenseFinal)
                CashFlowStorage.saveRegularExpenses(context, expenseFinal)

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

// bang doi thu 3
/*package com.example.budgetapp_grouptwo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bugetapp_grouptwo.CashFlowStorage
import com.example.bugetapp_grouptwo.RegularCashFlow


@Composable
fun FixedEntryScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    // ---- State ----
    var isIncome by remember { mutableStateOf(true) }
    var amountText by remember { mutableStateOf("") }

    // ---- Load saved values once ----
    LaunchedEffect(Unit) {
        val earnings = CashFlowStorage.loadRegularEarnings(context)
        val expenses = CashFlowStorage.loadRegularExpenses(context)

        RegularCashFlow.setRegularEarnings(earnings)
        RegularCashFlow.setRegularExpense(expenses)

        // show current type value in field
        amountText = if (earnings > 0.0) earnings.toString() else ""
    }

    // ---- When user switches Income/Expense, update field ----
    LaunchedEffect(isIncome) {
        val current = if (isIncome) {
            RegularCashFlow.getRegularEarnings()
        } else {
            RegularCashFlow.getRegularExpenses()
        }
        amountText = if (current > 0.0) current.toString() else ""
    }

    // ---- Colors like mockup ----
    val bg = Color(0xFFF7F7FB)
    val fieldBg = Color(0xFFEFEFF3)
    val primary = Color(0xFFBFD3FF)
    val textGrey = Color(0xFF777777)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ---- Top bar: back + centered title "Faste" ----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = "Faste",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.weight(1f))

            // giữ layout cân đối với icon trái
            Spacer(Modifier.size(22.dp))
        }

        Spacer(Modifier.height(18.dp))

        // ---- Beløb label ----
        Text("Beløb", color = textGrey)
        Spacer(Modifier.height(8.dp))

        // ---- Amount field ----
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

        Spacer(Modifier.height(18.dp))

        // ---- Kategori label ----
        Text("Kategori", color = textGrey)
        Spacer(Modifier.height(8.dp))

        // ---- Pills Indtægt / Udgift (mockup requirement) ----
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

        Spacer(Modifier.height(26.dp))

        // ---- Save button (big, rounded) ----
        Button(
            onClick = {
                val parsed = amountText.replace(",", ".").trim().toDoubleOrNull()
                if (parsed == null) {
                    Toast.makeText(context, "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (isIncome) {
                    RegularCashFlow.setRegularEarnings(parsed)
                    CashFlowStorage.saveRegularEarnings(context, parsed)
                    Toast.makeText(context, "Đã lưu Indtægt!", Toast.LENGTH_SHORT).show()
                } else {
                    RegularCashFlow.setRegularExpense(parsed)
                    CashFlowStorage.saveRegularExpenses(context, parsed)
                    Toast.makeText(context, "Đã lưu Udgift!", Toast.LENGTH_SHORT).show()
                }
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
}*/

//bang thao 2
/*package com.example.budgetapp_grouptwo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedEntryScreen(onBack: () -> Unit) {
    // --- LOGIK: States til data ---
    var navn by remember { mutableStateOf("") }          // Tên chi phí
    var beloeb by remember { mutableStateOf("") }        // Số tiền
    var kategori by remember { mutableStateOf("") }      // Danh mục
    var frekvens by remember { mutableStateOf("Månedlig") } // Tần suất

    var expanded by remember { mutableStateOf(false) }
    val frekvensOptions = listOf("Ugentlig", "Månedlig", "Kvartalsvis", "Årlig")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // --- TIÊU ĐỀ TRANG ---
        Text(
            text = "Fast",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Indtastningsfelter (Các trường nhập liệu)
        Text(
            text = "Indtast eller rediger din faste udgift",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.secondary
        )

        // 1. Navn på udgift
        OutlinedTextField(
            value = navn,
            onValueChange = { navn = it },
            label = { Text("Navn") },
            placeholder = { Text("f.eks. Husleje") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 2. Beløb
        OutlinedTextField(
            value = beloeb,
            onValueChange = { if (it.all { char -> char.isDigit() }) beloeb = it },
            label = { Text("Beløb (kr.)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 3. Frekvens (Dropdown)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = frekvens,
                onValueChange = {},
                readOnly = true,
                label = { Text("Frekvens") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                frekvensOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            frekvens = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 4. Kategori
        OutlinedTextField(
            value = kategori,
            onValueChange = { kategori = it },
            label = { Text("Kategori") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.weight(1f))

        // --- KNAPPER (Nút bấm) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { /* Logic til at annullere */ },
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text("Annuller")
            }

            Button(
                onClick = {
                    // Logic til at gemme (Lưu)
                },
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text("Gem")
            }
        }
    }
}
*/
// bang sua thu 2
/*package com.example.budgetapp_grouptwo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Import các file model của bạn (Đảm bảo dòng này không bị đỏ)
import com.example.bugetapp_grouptwo.CashFlowStorage

import com.example.bugetapp_grouptwo.RegularCashFlow

@Composable
fun FixedEntryScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    // --- 1. KHAI BÁO CÁC BIẾN TRẠNG THÁI (STATE) ---
    var isIncome by remember { mutableStateOf(true) }
    var amountText by remember { mutableStateOf("") }

    // Thêm biến cho Mô tả
    var descriptionText by remember { mutableStateOf("") }

    // Thêm biến cho Ngày (Mặc định lấy ngày hôm nay)
    val currentDate = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }
    var dateText by remember { mutableStateOf(currentDate) }

    var repeatMonthly by remember { mutableStateOf(false) }

    // --- 2. LOGIC LOAD DỮ LIỆU CŨ (GIỮ NGUYÊN) ---
    LaunchedEffect(Unit) {
        val earnings = CashFlowStorage.loadRegularEarnings(context)
        val expenses = CashFlowStorage.loadRegularExpenses(context)
        RegularCashFlow.setRegularEarnings(earnings)
        RegularCashFlow.setRegularExpense(expenses)

        // Khởi tạo giá trị (Chỉ load số tiền, vì logic cũ chưa lưu mô tả)
        val initialValue = if (earnings > 0) earnings.toString() else ""
        amountText = initialValue
    }

    LaunchedEffect(isIncome) {
        val currentValue = if (isIncome) RegularCashFlow.getRegularEarnings()
        else RegularCashFlow.getRegularExpenses()
        amountText = if (currentValue > 0.0) currentValue.toString() else ""
    }

    // --- 3. GIAO DIỆN (UI) ---
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
        // --- Header (Nút Back + Tiêu đề) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.weight(1f))
            Text(text = "Faste ", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.weight(1f))
            Spacer(Modifier.size(22.dp))
        }

        // --- 1. BELØB (Số tiền) ---
        InputSection(
            label = "Beløb",
            value = amountText,
            onValueChange = { amountText = it },
            placeholder = "0 kr.",
            keyboardType = KeyboardType.Number,
            fieldBg = fieldBg
        )

        Spacer(Modifier.height(14.dp))

        // --- 2. BESKRIVELSE (Mô tả - MỚI) ---
        InputSection(
            label = "Beskrivelse",
            value = descriptionText,
            onValueChange = { descriptionText = it },
            placeholder = "F.eks. Husleje", // Ví dụ: Tiền thuê nhà
            keyboardType = KeyboardType.Text,
            fieldBg = fieldBg
        )

        Spacer(Modifier.height(14.dp))

        // --- 3. DATO (Ngày - MỚI) ---
        InputSection(
            label = "Dato",
            value = dateText,
            onValueChange = { dateText = it },
            placeholder = "dd/mm/yyyy",
            keyboardType = KeyboardType.Text, // Cho phép sửa ngày dạng text
            fieldBg = fieldBg
        )

        Spacer(Modifier.height(14.dp))

        // --- 4. KATEGORI (Danh mục) ---
        Text("Kategori", color = Color(0xFF777777), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Nút Indtægt
            Button(
                onClick = { isIncome = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isIncome) primary else fieldBg
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text("Indtægt", color = Color.Black)
            }
            // Nút Udgift
            Button(
                onClick = { isIncome = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isIncome) primary else fieldBg
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text("Udgift", color = Color.Black)
            }
        }

        Spacer(Modifier.height(20.dp))

        // --- 5. GENTAG HVER MÅNED (Lặp lại) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = repeatMonthly,
                onCheckedChange = { repeatMonthly = it }
            )
            Spacer(Modifier.width(6.dp))
            Text("Gentag hver måned", color = Color(0xFF777777))
        }

        Spacer(Modifier.weight(1f)) // Đẩy nút Save xuống dưới cùng (hoặc bỏ dòng này nếu muốn nó nằm ngay dưới)

        // --- 6. NÚT GEM (Lưu) ---
        Button(
            onClick = {
                val parsed = amountText.replace(",", ".").trim().toDoubleOrNull()
                if (parsed == null) {
                    Toast.makeText(context, "Vui lòng nhập số tiền hợp lệ", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                // LƯU Ý: Hiện tại code lưu trữ (CashFlowStorage) của bạn
                // chỉ hỗ trợ lưu SỐ TIỀN. Các trường "Mô tả" và "Ngày"
                // hiện tại chỉ hiển thị trên UI chứ chưa lưu vào máy.
                // Bạn cần cập nhật CashFlowStorage sau nếu muốn lưu cả 2 cái này.

                if (isIncome) {
                    RegularCashFlow.setRegularEarnings(parsed)
                    CashFlowStorage.saveRegularEarnings(context, parsed)
                } else {
                    RegularCashFlow.setRegularExpense(parsed)
                    CashFlowStorage.saveRegularExpenses(context, parsed)
                }

                Toast.makeText(context, "Đã lưu số tiền!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primary)
        ) {
            Text("Gem", color = Color(0xFF4B4B4B), fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(30.dp))
    }
}

// Hàm phụ để vẽ các ô nhập liệu cho gọn code
@Composable
fun InputSection(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    fieldBg: Color
) {
    Column {
        Text(label, color = Color(0xFF777777))
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = fieldBg,
                unfocusedContainerColor = fieldBg,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
    }
}

*/