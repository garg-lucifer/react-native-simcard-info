package com.simcardinfo

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.WritableMap
import java.util.regex.Pattern

fun hasReadPhoneStatePermission(reactApplicationContext: ReactApplicationContext): Boolean {
    return (ContextCompat.checkSelfPermission(
        reactApplicationContext,
        android.Manifest.permission.READ_PHONE_STATE
    ) == PackageManager.PERMISSION_GRANTED)
}

fun hasCarrierPrivilege(reactApplicationContext: ReactApplicationContext): Boolean {
    val telephonyManager = reactApplicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return telephonyManager.hasCarrierPrivileges()
}

fun hasRequiredPermissionsOrCarrierPrivileges(reactApplicationContext: ReactApplicationContext): Boolean {
    val hasAccessNetworkState = ContextCompat.checkSelfPermission(
        reactApplicationContext, android.Manifest.permission.ACCESS_NETWORK_STATE
    ) == PackageManager.PERMISSION_GRANTED

    val hasModifyPhoneState = ContextCompat.checkSelfPermission(
        reactApplicationContext, android.Manifest.permission.MODIFY_PHONE_STATE
    ) == PackageManager.PERMISSION_GRANTED

    val hasReadBasicPhoneState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            reactApplicationContext, android.Manifest.permission.READ_BASIC_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        false
    }

    return hasAccessNetworkState || hasModifyPhoneState || hasReadBasicPhoneState || hasCarrierPrivilege(reactApplicationContext)
}

/**
 * Converts a SubscriptionInfo object into a WritableMap for use in a React Native context.
 *
 * The method parses the `toString()` representation of the SubscriptionInfo object to extract key-value pairs
 * and adds them to the WritableMap. Additionally, it retrieves the associated phone number for the subscription,
 * using different methods based on the Android API level.
 *
 * @param subscriptionInfo The SubscriptionInfo object to be converted.
 * @param reactApplicationContext The ReactApplicationContext for accessing system services.
 * @return A WritableMap containing key-value pairs from the SubscriptionInfo object and the phone number.
 */
@SuppressLint("MissingPermission")
fun convertSubscriptionInfoToWritableMap(subscriptionInfo: SubscriptionInfo, reactApplicationContext: ReactApplicationContext): WritableMap {
    val writableMap = Arguments.createMap()
    val pattern = Pattern.compile("([a-zA-Z0-9_]+)=((?:\\[[^\\]]*\\])|[^ ]+|null)")
    val matcher = pattern.matcher(subscriptionInfo.toString().dropLast(1))
    while (matcher.find()) {
        val key = matcher.group(1)
        val value = matcher.group(2)
        if (key != null && value != null) {
            writableMap.putString(key, value)
        }
    }

    try {
      writableMap.putString("iconTint", String.format("#%06X", (0xFFFFFF and subscriptionInfo.iconTint)))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
            writableMap.putString("number", subscriptionInfo.number)
        else {
            val subscriptionManager =
                reactApplicationContext.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            writableMap.putString("number", subscriptionManager.getPhoneNumber(subscriptionInfo.subscriptionId))
        }
    } catch (_:Error){}
    return writableMap
}
