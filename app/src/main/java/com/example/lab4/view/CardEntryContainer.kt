package com.example.lab4.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab4.model.CardEntry
import com.example.lab4.model.UserProfileObject
import com.example.lab4.theme.Lab4DemoTheme

@Composable
fun CardEntryContainer(
    listView: Boolean,
    cardEntries: List<CardEntry>,
    onClick: (Int) -> Unit
) {
    Lab4DemoTheme (darkTheme = UserProfileObject.darkmode) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (listView) 1 else 2),
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            cardEntries.forEach { cardEntry ->
                item {
                    ChapterCard(cardEntry, listView) {
                        onClick
                    }
                }
            }
        }
    }
}