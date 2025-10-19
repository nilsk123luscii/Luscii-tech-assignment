package com.luscii.techassignment.views.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.luscii.techassignment.ui.theme.LocalSpacing

enum class ExceptionType {
    WARNING,
    ERROR
}

@Composable
fun ExceptionView(text: String, exceptionType: ExceptionType) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("exception_view")
            .padding(LocalSpacing.current.md)
    ) {
        when (exceptionType) {
            ExceptionType.WARNING -> {
                Text(text = "⚠️ $text")
            }

            ExceptionType.ERROR -> {
                Text(text = "❌ $text")
            }
        }
    }
}
