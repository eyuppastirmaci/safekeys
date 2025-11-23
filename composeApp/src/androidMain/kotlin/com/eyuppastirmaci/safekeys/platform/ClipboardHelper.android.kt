package com.eyuppastirmaci.safekeys.platform

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class AndroidClipboardHelper(private val context: Context) : ClipboardHelper {
    override fun copyToClipboard(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("password", text)
        clipboard.setPrimaryClip(clip)
    }
}

private var helper: ClipboardHelper? = null

fun initClipboardHelper(context: Context) {
    helper = AndroidClipboardHelper(context)
}

actual fun getClipboardHelper(): ClipboardHelper {
    return helper ?: throw IllegalStateException("ClipboardHelper not initialized")
}