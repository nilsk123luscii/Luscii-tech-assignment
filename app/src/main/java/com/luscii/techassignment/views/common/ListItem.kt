package com.luscii.techassignment.views.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.luscii.techassignment.ui.theme.LocalSpacing

@Composable
fun ListItem(
    content: @Composable () -> Unit,
    onClickItem: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = LocalSpacing.current.md,
                vertical = LocalSpacing.current.sm
            ) // small outer spacing
            .clickable { onClickItem() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = LocalSpacing.current.sm
        ),
        shape = RoundedCornerShape(LocalSpacing.current.md)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = LocalSpacing.current.md,
                    vertical = LocalSpacing.current.md
                ), // inner padding
            contentAlignment = Alignment.CenterStart
        ) {
            content()
        }
    }
}
