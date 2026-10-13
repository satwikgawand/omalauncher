package dev.omalauncher

import android.app.admin.DeviceAdminReceiver

/**
 * The component the system knows as the device admin for [PhoneLock]. The manifest points at it, and the policy
 * file it names asks for the one right locking needs, so no other part of the launcher carries an admin.
 */
class LockAdmin : DeviceAdminReceiver()
