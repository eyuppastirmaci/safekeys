package com.eyuppastirmaci.safekeys.password

import com.eyuppastirmaci.safekeys.model.PasswordMetrics
import kotlin.math.log2
import kotlin.math.pow

object PasswordAnalyzer {
    fun calculateMetrics(password: String, poolSize: Int): PasswordMetrics {
        if (poolSize == 0 || password.isEmpty()) {
            return PasswordMetrics(PasswordStrength.WEAK, "Instantly")
        }

        val length = password.length
        
        // Penalize repeating characters
        val repeatingCharCount = password.length - password.toSet().size
        val repeatingPenalty = repeatingCharCount * 2

        // Penalize sequential characters
        var sequences = 0
        for (i in 0 until length - 2) {
            val c1 = password[i]
            val c2 = password[i+1]
            val c3 = password[i+2]
            
            if (c2.code == c1.code + 1 && c3.code == c2.code + 1) sequences++
            if (c2.code == c1.code - 1 && c3.code == c2.code - 1) sequences++
        }
        val sequencePenalty = sequences * 5
        
        // Refine entropy based on used character sets in the *actual* password
        var usedPoolSize = 0
        if (password.any { it.isUpperCase() }) usedPoolSize += 26
        if (password.any { it.isLowerCase() }) usedPoolSize += 26
        if (password.any { it.isDigit() }) usedPoolSize += 10
        if (password.any { !it.isLetterOrDigit() }) usedPoolSize += 30 // approx symbols
        
        val actualEntropy = length * log2(usedPoolSize.toDouble().coerceAtLeast(1.0)) - repeatingPenalty - sequencePenalty

        val strength = when {
            actualEntropy < 40 -> PasswordStrength.WEAK
            actualEntropy < 60 -> PasswordStrength.FAIR
            actualEntropy < 90 -> PasswordStrength.GOOD
            else -> PasswordStrength.STRONG
        }
        
        val crackTime = calculateCrackTime(actualEntropy)
        return PasswordMetrics(strength, crackTime)
    }

    private fun calculateCrackTime(entropy: Double): String {
        // Assume 1 trillion guesses per second (powerful brute force)
        val guessesPerSecond = 1_000_000_000_000.0
        val seconds = 2.0.pow(entropy) / guessesPerSecond
        
        return when {
            seconds < 1 -> "Instantly"
            seconds < 60 -> "${seconds.toInt()} seconds"
            seconds < 3600 -> "${(seconds / 60).toInt()} minutes"
            seconds < 86400 -> "${(seconds / 3600).toInt()} hours"
            seconds < 31536000 -> "${(seconds / 86400).toInt()} days"
            else -> formatYears(seconds / 31536000)
        }
    }

    private fun formatYears(years: Double): String {
        return when {
            years < 1000 -> "${years.toInt()} years"
            years < 1_000_000 -> "${(years / 1000).toInt()} thousand years"
            years < 1_000_000_000 -> "${(years / 1_000_000).toInt()} million years"
            years < 1_000_000_000_000 -> "${(years / 1_000_000_000).toInt()} billion years"
            years < 1_000_000_000_000_000 -> "${(years / 1_000_000_000_000).toInt()} trillion years"
            years < 1_000_000_000_000_000_000 -> "${(years / 1_000_000_000_000_000).toInt()} quadrillion years"
            else -> "More than a quadrillion years"
        }
    }
}

