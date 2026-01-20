package com.example.budgetapp_grouptwo.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetapp_grouptwo.ui.theme.Typography


@Preview
@Composable
fun labelTitlePreview(){
    LabelTitle(title = "hello", imageVector = Icons.Filled.Edit)
}

@Composable
fun LabelTitle(
    title: String,
    imageVector: ImageVector? = null,
){
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = title,
            style = Typography.titleSmall,
        )
        if(imageVector is ImageVector){
            androidx.compose.material3.Icon(
                imageVector = imageVector,
                "",
                modifier = Modifier.absolutePadding(5.dp,0.dp,0.dp,0.dp).width(14.dp),
                tint = Color.Black,
            )
        }
    }
}