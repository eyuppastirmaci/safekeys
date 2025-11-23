package com.eyuppastirmaci.safekeys.password

import com.eyuppastirmaci.safekeys.config.PasswordConfig
import java.security.SecureRandom

class PasswordGenerator(
    private val secureRandom: SecureRandom = SecureRandom()
) {
    private val chars: String =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789" +
                "!@#\$%^&*()-_=+[]{};:,.?/"

    fun generate(length: Int): String {
        require(length in PasswordConfig.MIN_PASSWORD_LENGTH..PasswordConfig.MAX_PASSWORD_LENGTH) {
            "Password length must be between ${PasswordConfig.MIN_PASSWORD_LENGTH} and ${PasswordConfig.MAX_PASSWORD_LENGTH}"
        }

        return buildString(length) {
            repeat(length) {
                val index = secureRandom.nextInt(chars.length)
                append(chars[index])
            }
        }
    }
}
