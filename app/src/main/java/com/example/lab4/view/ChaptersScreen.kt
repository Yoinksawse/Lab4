package com.example.lab4.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.lab4.model.CardEntryDatabase
import com.example.lab4.model.UserProfileObject
import com.example.lab4.theme.Lab4DemoTheme

@Composable
fun ChaptersScreen(listView: Boolean) {
    Lab4DemoTheme (darkTheme = UserProfileObject.darkmode) {
        CardEntryContainer(
            listView,
            CardEntryDatabase.topics
        ) {}
    }
}
