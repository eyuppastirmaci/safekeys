package com.eyuppastirmaci.safekeys.password

enum class PasswordStrength(val label: String, val color: Long, val progress: Float) {
    WEAK("Weak", 0xFFD32F2F, 0.25f),       // Darker Red
    FAIR("Fair", 0xFFF57C00, 0.5f),        // Darker Orange
    GOOD("Good", 0xFFFBC02D, 0.75f),       // Darker Yellow
    STRONG("Strong", 0xFF388E3C, 1.0f);    // Darker Green
}
