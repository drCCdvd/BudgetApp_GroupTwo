package com.example.budgetapp_grouptwo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetapp_grouptwo.ui.utils.CurrencyVisualTransformation

@Composable
fun PillTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    textAlignCenter: Boolean,
    visualTransformation: VisualTransformation?= VisualTransformation.None,
) {
    val visualTransformation = if(visualTransformation is VisualTransformation){
        visualTransformation;
    }else{
        VisualTransformation.None;
    }
    Surface(
        modifier = Modifier.width(220.dp),
        shape = RoundedCornerShape(12.dp),
        color  = Color.hsv(0f,0f,.95f) //MaterialTheme.colorScheme.surfaceVariant
    ) {
        var textField = BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            textStyle = TextStyle(
                fontSize = 14.sp,
                textAlign = if (textAlignCenter) TextAlign.Center else TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .height(46.dp)
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            decorationBox = { inner ->
                if (value.isBlank()) {
                    Text(
                        text = placeholder,
                        fontSize = 14.sp,
                        textAlign = if (textAlignCenter) TextAlign.Center else TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                inner()
            },
            visualTransformation = visualTransformation,
        )
    }
}