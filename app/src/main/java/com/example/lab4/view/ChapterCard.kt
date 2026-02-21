package com.example.lab4.view

import com.example.lab4.theme.Lab4DemoTheme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lab4.model.CardEntry
import com.example.lab4.model.UserProfileObject
import kotlin.math.log

@Composable
fun ChapterCard(cardEntry: CardEntry, listView: Boolean, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.96f else 1f)

    val infiniteTransition = rememberInfiniteTransition()
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Lab4DemoTheme(darkTheme = UserProfileObject.darkmode) {
        val surface = MaterialTheme.colorScheme.surface
        val onPrimary = MaterialTheme.colorScheme.surface
        val onSurface = MaterialTheme.colorScheme.onSurface
        val cardBackground = Color(0xFFF5F5F5)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(if (!listView) Modifier.aspectRatio(0.6767676767676767f) else Modifier)
                .scale(scale)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            pressed = true
                            tryAwaitRelease()
                            pressed = false
                        },
                        onTap = { onClick() }
                    )
                }
                .shadow(8.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(onPrimary, onPrimary)
                    )
                )
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .offset(y = if (!listView) floatOffset.dp else 0.dp),
                contentAlignment = Alignment.Center
            ) {
                CardEntryImage(cardEntry)
            }
            CardEntryName(cardEntry)
        }
    }
}


@Composable
private fun CardEntryName(cardEntry: CardEntry) {
    Text(
        text = cardEntry.name,
        fontSize = (16 - log(cardEntry.name.length.toDouble(), 12.0).toInt()).sp,
        fontWeight = FontWeight.SemiBold,
        maxLines = 8,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun CardEntryImage(cardEntry: CardEntry) {
    Row(
        modifier = Modifier.fillMaxWidth(1f)
            .aspectRatio(1f)
    ) {
        AsyncImage(
            model = cardEntry.imageReference,
            contentDescription = "Image with url ${cardEntry.imageReference}",
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth(1f)
        )
    }
}

@Composable
private fun CardEntryInfo(cardEntry: CardEntry, height: Dp) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
    }
}