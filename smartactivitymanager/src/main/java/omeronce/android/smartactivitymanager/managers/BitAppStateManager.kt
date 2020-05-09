package omeronce.android.smartactivitymanager.managers

internal class BitAppStateManager {

    companion object {
        const val STATE_BACKGROUND = 0L
        const val STATE_FOREGROUND = 1L
    }

    private var size = 0
    private val bytes = mutableListOf<Long>()

    fun onActivityComesToForeground() {
        if (size % Long.SIZE_BITS == 0) {
            bytes.add(0L)
        }
        val index = size / Long.SIZE_BITS
        var state = bytes[index]
        state = (state shl 1) or 1
        bytes[index] = state
    }

    fun onActivityMovedToBackground() {
        val index = size / Long.SIZE_BITS
        var state = bytes[index]
        state = state shr 1
        if (state == STATE_BACKGROUND) {
            bytes.removeAt(index)
        }
        else {
            bytes[index] = state
        }
    }

    fun getState(): Long {
        var background = true
        for (byte in bytes) {
            if (byte != STATE_BACKGROUND) {
                background = false
                break
            }
        }
        return if (background) STATE_BACKGROUND else STATE_FOREGROUND
    }
}