package com.eyuppastirmaci.safekeys.platform

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class JvmClipboardHelper : ClipboardHelper {
    override fun copyToClipboard(text: String) {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val selection = StringSelection(text)
        clipboard.setContents(selection, selection)
    }
}

actual fun getClipboardHelper(): ClipboardHelper = JvmClipboardHelper()