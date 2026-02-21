package com.example.lab4.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lab4.model.UserProfileObject
import com.example.lab4.theme.Lab4DemoTheme

@Composable
fun ContactRow(label: String, value: String) {
    Lab4DemoTheme (darkTheme = UserProfileObject.darkmode) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}