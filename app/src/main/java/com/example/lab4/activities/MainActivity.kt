package com.example.lab4.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.example.lab4.R
import com.example.lab4.model.UserProfileObject
import com.example.lab4.model.UserProfileObject.darkmode
import com.example.lab4.theme.Lab4DemoTheme
import com.example.lab4.view.BottomTabBar
import com.example.lab4.view.ChaptersScreen
import com.example.lab4.view.ModuleInfoScreen
import com.example.lab4.view.PlannerScreen
import com.example.lab4.view.TeacherScreen
import androidx.compose.ui.graphics.graphicsLayer

var debugOnboardingOn = false; //TODO: just for debugging; TURN OFF

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val scope = rememberCoroutineScope()
            val context = LocalContext.current

            //darkmode stuff
            var darkmodeOn by remember { mutableStateOf(UserProfileObject.darkmode) }

            //show onboarding
            val prefs = getSharedPreferences("lab4_prefs", MODE_PRIVATE)
            if (debugOnboardingOn) {
                prefs.edit().putBoolean("first_use", true).apply()
                debugOnboardingOn = false
            }
            if (prefs.getBoolean("first_use", true)) {
                val onboardingIntent = Intent(context, OnboardingActivity::class.java)
                context.startActivity(onboardingIntent)

                prefs.edit { putBoolean("first_use", false) }
            }
            Lab4DemoTheme(darkTheme = darkmodeOn) {
                MainScreen(onToggleDarkMode = {
                    UserProfileObject.darkmode = !UserProfileObject.darkmode
                    darkmodeOn = UserProfileObject.darkmode
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onToggleDarkMode: () -> Unit) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(0) }
    var listView by remember { mutableStateOf(false) }

    //scrolling collapse topappbar part

    Lab4DemoTheme (darkTheme = darkmode) {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),

            topBar = {
                var expanded by remember { mutableStateOf(false) }

                val collapsedFraction = scrollBehavior.state.collapsedFraction

                Box {
                    Image(
                        painter = painterResource(R.drawable.background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp * (1f - collapsedFraction))
                            .alpha(1f - collapsedFraction)
                    )

                    LargeTopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.app_name),
                                color = Color.White
                            )
                        },
                        scrollBehavior = scrollBehavior,
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = MaterialTheme.colorScheme.primary
                        ),
                        actions = {
                            Box {
                                IconButton(
                                    onClick = { expanded = true }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        tint = Color.White,
                                        contentDescription = "Menu"
                                    )
                                }

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("Toggle Dark Mode") },
                                        onClick = {
                                            onToggleDarkMode()
                                            expanded = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Toggle List/Grid Layout") },
                                        onClick = {
                                            listView = !listView
                                            expanded = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Settings") },
                                        onClick = {
                                            Toast.makeText(
                                                context,
                                                "Settings clicked",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    )
                }
            },
            bottomBar = {
                BottomTabBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }

        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedTab) {
                    0 -> ChaptersScreen(listView = listView)
                    1 -> ModuleInfoScreen()
                    2 -> TeacherScreen()
                    3 -> PlannerScreen()
                }
            }
        }
    }
}
