package com.eyuppastirmaci.safekeys.model

import com.eyuppastirmaci.safekeys.password.PasswordStrength

data class PasswordMetrics(
    val strength: PasswordStrength,
    val crackTimeDisplay: String
)

