package com.eyuppastirmaci.safekeys.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eyuppastirmaci.safekeys.config.PasswordConfig
import com.eyuppastirmaci.safekeys.model.PasswordMetrics
import com.eyuppastirmaci.safekeys.password.PasswordAnalyzer
import com.eyuppastirmaci.safekeys.password.PasswordGenerator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppViewModel(
    private val passwordGenerator: PasswordGenerator = PasswordGenerator()
) : ViewModel() {

    var password by mutableStateOf<String?>(null)
        private set

    var passwordMetrics by mutableStateOf<PasswordMetrics?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var lengthText by mutableStateOf("16")
        private set

    var includeUppercase by mutableStateOf(true)
        private set
    var includeLowercase by mutableStateOf(true)
        private set
    var includeNumbers by mutableStateOf(true)
        private set
    var includeSymbols by mutableStateOf(true)
        private set

    var excludeAmbiguous by mutableStateOf(false)
        private set

    var isAdvancedOptionsVisible by mutableStateOf(false)
        private set

    var guessesPerSecondText by mutableStateOf("1000000000000")
        private set

    var toastMessage by mutableStateOf("")
        private set
    
    var isToastVisible by mutableStateOf(false)
        private set

    private var toastJob: Job? = null

    fun updateLengthText(text: String) {
        lengthText = text.filter { it.isDigit() }
    }

    fun updateIncludeUppercase(include: Boolean) {
        includeUppercase = include
    }

    fun updateIncludeLowercase(include: Boolean) {
        includeLowercase = include
    }

    fun updateIncludeNumbers(include: Boolean) {
        includeNumbers = include
    }

    fun updateIncludeSymbols(include: Boolean) {
        includeSymbols = include
    }

    fun updateExcludeAmbiguous(exclude: Boolean) {
        excludeAmbiguous = exclude
    }

    fun toggleAdvancedOptions() {
        isAdvancedOptionsVisible = !isAdvancedOptionsVisible
    }

    fun updateGuessesPerSecondText(text: String) {
        // Only allow digits
        if (text.all { it.isDigit() }) {
            guessesPerSecondText = text
        }
    }

    fun showToast(message: String) {
        toastJob?.cancel()
        toastJob = viewModelScope.launch {
            toastMessage = message
            isToastVisible = true
            delay(2000) // Show for 2 seconds
            isToastVisible = false
        }
    }

    fun generatePassword() {
        when (val length = lengthText.toIntOrNull()) {
            null -> {
                errorMessage = "Please enter a number"
                password = null
            }
            !in PasswordConfig.MIN_PASSWORD_LENGTH..PasswordConfig.MAX_PASSWORD_LENGTH -> {
                errorMessage =
                    "Please enter a length between ${PasswordConfig.MIN_PASSWORD_LENGTH} and ${PasswordConfig.MAX_PASSWORD_LENGTH}"
                password = null
            }
            else -> {
                try {
                    password = passwordGenerator.generate(
                        length,
                        includeUppercase,
                        includeLowercase,
                        includeNumbers,
                        includeSymbols,
                        excludeAmbiguous
                    )
                    
                    val poolSize = passwordGenerator.calculatePoolSize(
                        includeUppercase,
                        includeLowercase,
                        includeNumbers,
                        includeSymbols,
                        excludeAmbiguous
                    )
                    
                    val guessesPerSecond = guessesPerSecondText.toDoubleOrNull() ?: 1_000_000_000_000.0
                    passwordMetrics = PasswordAnalyzer.calculateMetrics(password!!, poolSize, guessesPerSecond)
                    
                    errorMessage = null
                } catch (e: IllegalArgumentException) {
                    errorMessage = e.message
                    password = null
                    passwordMetrics = null
                }
            }
        }
    }
}

