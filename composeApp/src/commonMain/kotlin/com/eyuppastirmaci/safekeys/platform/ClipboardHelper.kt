package com.eyuppastirmaci.safekeys.platform

interface ClipboardHelper {
    fun copyToClipboard(text: String)
}

expect fun getClipboardHelper(): ClipboardHelper