package com.eyuppastirmaci.safekeys.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eyuppastirmaci.safekeys.platform.getClipboardHelper
import com.eyuppastirmaci.safekeys.viewmodel.AppViewModel

@Composable
fun MainScreenContent(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val clipboardHelper = getClipboardHelper()
    val contentWidth = 460.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainHeader(
            isDarkTheme = viewModel.isDarkTheme,
            onToggleTheme = viewModel::toggleTheme,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = contentWidth)
        )

        Spacer(modifier = Modifier.height(24.dp))

        PasswordConfigurationCard(
            lengthText = viewModel.lengthText,
            onLengthChange = viewModel::updateLengthText,
            includeUppercase = viewModel.includeUppercase,
            includeLowercase = viewModel.includeLowercase,
            includeNumbers = viewModel.includeNumbers,
            includeSymbols = viewModel.includeSymbols,
            onIncludeUppercaseChange = viewModel::updateIncludeUppercase,
            onIncludeLowercaseChange = viewModel::updateIncludeLowercase,
            onIncludeNumbersChange = viewModel::updateIncludeNumbers,
            onIncludeSymbolsChange = viewModel::updateIncludeSymbols,
            excludeAmbiguous = viewModel.excludeAmbiguous,
            onExcludeAmbiguousChange = viewModel::updateExcludeAmbiguous,
            guessesPerSecondText = viewModel.guessesPerSecondText,
            onGuessesPerSecondChange = viewModel::updateGuessesPerSecondText,
            isAdvancedOptionsVisible = viewModel.isAdvancedOptionsVisible,
            onToggleAdvancedOptions = viewModel::toggleAdvancedOptions,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = contentWidth)
        )

        Spacer(modifier = Modifier.height(24.dp))

        GenerateButton(
            onClick = viewModel::generatePassword,
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = contentWidth)
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordResultSection(
            password = viewModel.password,
            metrics = viewModel.passwordMetrics,
            errorMessage = viewModel.errorMessage,
            contentWidth = contentWidth,
            onCopyPassword = { password ->
                clipboardHelper.copyToClipboard(password)
                viewModel.showToast("Password copied!")
            }
        )
    }
}