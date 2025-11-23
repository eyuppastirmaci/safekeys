package com.eyuppastirmaci.safekeys.platform

import platform.UIKit.UIPasteboard

class IosClipboardHelper : ClipboardHelper {
    override fun copyToClipboard(text: String) {
        UIPasteboard.generalPasteboard.string = text
    }
}

actual fun getClipboardHelper(): ClipboardHelper = IosClipboardHelper()