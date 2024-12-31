package com.simcardinfo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * @class AirplaneModeChangeBR
 * @brief A BroadcastReceiver that listens for changes in Airplane Mode status.
 *
 * This class receives broadcasts when the Airplane Mode state changes and
 * notifies the provided callback interface with the updated status.
 *
 * @param callbackInterface An implementation of CallbackInterface to handle the Airplane Mode status updates.
 */
class AirplaneModeChangeBR(
    private val callbackInterface: CallbackInterface
) :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneModeOn = intent!!.getBooleanExtra("state", false)
        callbackInterface.onAirplaneModeChanged(isAirplaneModeOn)
    }
}
