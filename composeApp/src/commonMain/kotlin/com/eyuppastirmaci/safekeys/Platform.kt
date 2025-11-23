package com.eyuppastirmaci.safekeys

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform