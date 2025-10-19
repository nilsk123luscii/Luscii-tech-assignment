package com.luscii.techassignment.views.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.luscii.techassignment.ui.theme.LocalSpacing

@Composable
fun LoadingSpinner() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .testTag("loading_spinner")
            .fillMaxSize()
            .padding(LocalSpacing.current.xl)
    ) {
        CircularProgressIndicator()
    }
}
