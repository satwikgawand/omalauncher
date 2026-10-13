package dev.omalauncher

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.widget.Toast

/**
 * Locking the phone, for the double-tap gesture. [DevicePolicyManager.lockNow] needs the app to be a device admin,
 * so the first lock without that right opens the system's grant screen instead; the next one goes through. A device
 * right, not a permission: the user grants it once in a screen they can reopen to withdraw it.
 */
class PhoneLock(context: Context) {
    private val app = context.applicationContext
    private val dpm = app.getSystemService(DevicePolicyManager::class.java)
    private val admin = ComponentName(app, LockAdmin::class.java)

    /** Locks now, or asks for the right to. */
    fun lock() {
        if (dpm != null && dpm.isAdminActive(admin)) {
            dpm.lockNow()
        } else {
            Toast.makeText(app, "grant the device-admin right to lock, then double tap again", Toast.LENGTH_SHORT).show()
            app.startActivity(grant().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    /**
     * Where the right gets granted: the system's own activation screen, or the security settings when a skin hides
     * that screen (OnePlus and others refuse the old intent, and a tap that opens nothing looks broken).
     */
    private fun grant(): Intent {
        val activation = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin)
        return if (app.packageManager.resolveActivity(activation, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            activation
        } else {
            Intent(Settings.ACTION_SECURITY_SETTINGS)
        }
    }
}
