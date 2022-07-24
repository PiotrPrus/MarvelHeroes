package com.piotrprus.marvelheroes.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

@Composable
fun BoxPlaceholder(modifier: Modifier) {
    Box(
        modifier = modifier.placeholder(
            true,
            highlight = PlaceholderHighlight.shimmer(),
            color = MaterialTheme.colors.onSurface
        )
    )
}