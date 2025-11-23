package com.eyuppastirmaci.safekeys.password

import com.eyuppastirmaci.safekeys.config.PasswordConfig
import kotlin.random.Random

class PasswordGenerator {
    companion object {
        const val UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        const val LOWERCASE = "abcdefghijklmnopqrstuvwxyz"
        const val NUMBERS = "0123456789"
        const val SYMBOLS = "!@#\$%^&*()-_=+[]{};:,.?/"
        const val AMBIGUOUS = "il1Lo0O"
    }

    fun generate(
        length: Int,
        includeUppercase: Boolean,
        includeLowercase: Boolean,
        includeNumbers: Boolean,
        includeSymbols: Boolean,
        excludeAmbiguous: Boolean = false
    ): String {
        require(length in PasswordConfig.MIN_PASSWORD_LENGTH..PasswordConfig.MAX_PASSWORD_LENGTH) {
            "Password length must be between ${PasswordConfig.MIN_PASSWORD_LENGTH} and ${PasswordConfig.MAX_PASSWORD_LENGTH}"
        }

        var chars = buildString {
            if (includeUppercase) append(UPPERCASE)
            if (includeLowercase) append(LOWERCASE)
            if (includeNumbers) append(NUMBERS)
            if (includeSymbols) append(SYMBOLS)
        }

        if (excludeAmbiguous) {
            chars = chars.filter { it !in AMBIGUOUS }
        }

        if (chars.isEmpty()) {
            throw IllegalArgumentException("At least one character set must be selected")
        }

        return buildString(length) {
            repeat(length) {
                val index = Random.nextInt(chars.length)
                append(chars[index])
            }
        }
    }

    fun calculatePoolSize(
        includeUppercase: Boolean,
        includeLowercase: Boolean,
        includeNumbers: Boolean,
        includeSymbols: Boolean,
        excludeAmbiguous: Boolean = false
    ): Int {
        var chars = buildString {
            if (includeUppercase) append(UPPERCASE)
            if (includeLowercase) append(LOWERCASE)
            if (includeNumbers) append(NUMBERS)
            if (includeSymbols) append(SYMBOLS)
        }
        
        if (excludeAmbiguous) {
            chars = chars.filter { it !in AMBIGUOUS }
        }
        
        return chars.length
    }
}

