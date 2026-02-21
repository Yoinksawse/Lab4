package com.example.lab4.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lab4.model.UserProfileObject
import com.example.lab4.theme.Lab4DemoTheme

@Composable
fun BottomTabBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(
        Pair("Chapters", Icons.Default.Book),
        Pair("Module Info", Icons.Default.Info),
        Pair("Teacher", Icons.Default.Person),
        Pair("Planner", Icons.Default.DateRange)
    )

    Lab4DemoTheme (darkTheme = UserProfileObject.darkmode) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            tonalElevation = 8.dp
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = selectedTab == index

                NavigationBarItem( //for pill animation
                    selected = isSelected,
                    onClick = { onTabSelected(index) },
                    label = {
                        Text(
                            text = tab.first,
                            fontWeight =
                                if (isSelected) FontWeight.Bold //if click, older. for aesthetic animation only
                                else FontWeight.Medium,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = tab.second,
                            contentDescription = null
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}