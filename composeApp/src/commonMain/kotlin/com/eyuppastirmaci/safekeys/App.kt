package com.eyuppastirmaci.safekeys

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eyuppastirmaci.safekeys.components.MainScreenContent
import com.eyuppastirmaci.safekeys.components.ToastNotification
import com.eyuppastirmaci.safekeys.theme.getAppTypography
import com.eyuppastirmaci.safekeys.viewmodel.AppViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val viewModel: AppViewModel = viewModel { AppViewModel() }
    val appTypography = getAppTypography()

    MaterialTheme(
        typography = appTypography
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MainScreenContent(
                viewModel = viewModel,
                modifier = Modifier.align(Alignment.Center)
            )

            ToastNotification(
                isVisible = viewModel.isToastVisible,
                message = viewModel.toastMessage,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp)
            )
        }
    }
}
