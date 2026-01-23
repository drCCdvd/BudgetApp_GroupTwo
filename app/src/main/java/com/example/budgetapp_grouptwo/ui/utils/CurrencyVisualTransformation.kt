package com.example.budgetapp_grouptwo.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


/** Helper for transforming a given textString to currency visual (with "." seperation and "kr." at end)
 */
class CurrencyVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {

        val digits = text.text.filter { it.isDigit() }

        val formatted = if (digits.isEmpty()) {
            ""
        } else {
            digits
                .reversed()
                .chunked(3)
                .joinToString(".")
                .reversed() + " kr."
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return formatted.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return digits.length
            }
        }

        return TransformedText(
            AnnotatedString(formatted),
            offsetMapping
        )
    }
}