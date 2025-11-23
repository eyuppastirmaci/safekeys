package com.eyuppastirmaci.safekeys.password

enum class PasswordStrength(val label: String, val progress: Float) {
    WEAK("Weak", 0.25f),
    FAIR("Fair", 0.5f),
    GOOD("Good", 0.75f),
    STRONG("Strong", 1.0f);
}
