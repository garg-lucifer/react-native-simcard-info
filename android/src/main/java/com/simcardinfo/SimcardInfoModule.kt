package com.simcardinfo

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.telephony.euicc.EuiccManager
import android.util.Log
import androidx.activity.ComponentActivity
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.WritableArray
import com.facebook.react.bridge.WritableMap
import com.facebook.react.module.annotations.ReactModule


@ReactModule(name = SimcardInfoModule.NAME)
class SimcardInfoModule(reactContext: ReactApplicationContext) :
  NativeSimcardInfoSpec(reactContext), CallbackInterface {

  private var subscriptionManager: SubscriptionManager? = null
  private var telephonyManager : TelephonyManager? = null
  private var airplaneModeListenerCount = 0
  private var subscriptionManagerListenerCount = 0
  private var airplaneModeChangeBR : AirplaneModeChangeBR? = null
  private var simStateChangeListener: SubscriptionManager.OnSubscriptionsChangedListener? = null

  init {
    subscriptionManager =
      reactApplicationContext.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
    telephonyManager = reactApplicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    airplaneModeChangeBR = AirplaneModeChangeBR(this)
    simStateChangeListener = object : SubscriptionManager.OnSubscriptionsChangedListener(){
      override fun onSubscriptionsChanged() {
        Log.d("simStateChangeListener", "Changed");
        emitOnSIMCardStateChange(activeSubscriptionInfoList)
      }
    }
  }

  override fun getName() = NAME

  /**
   * Retrieves the subscription IDs of all active SIM(s).
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getActiveSubscriptionInfoList()
   * https://developer.android.com/reference/android/telephony/SubscriptionInfo#getSubscriptionId()
   *
   * @return A WritableArray containing the subscription IDs of active subscriptions.
   * @throws Error If the required permissions are not granted or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun getSubscriptionIds(): WritableArray {
    try {
      if (!hasReadPhoneStatePermission(reactApplicationContext) && !hasCarrierPrivilege(reactApplicationContext))
        throw Error(READ_PHONE_STATE_OR_CARRIER_PERMISSION_REQUIRED)
      val activeSubscriptions = subscriptionManager?.activeSubscriptionInfoList
      val subscriptionIds: WritableArray = Arguments.createArray()
      if (activeSubscriptions != null) {
        for (subscriptionInfo in activeSubscriptions) {
          subscriptionIds.pushInt(subscriptionInfo.subscriptionId)
        }
      }
      return subscriptionIds
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the subscription ID for SIM at given slot index.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getSubscriptionId(int)
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#INVALID_SUBSCRIPTION_ID
   *
   * @return The subscription id. INVALID_SUBSCRIPTION_ID if SIM is absent.
   * @throws Error If the calling SDK < 34.
   */
  override fun getSubscriptionId(simSlotIndex: Double): Double {
    try {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) throw Error(MIN_VERSION_UPSIDE_DOWN)
      return SubscriptionManager.getSubscriptionId(simSlotIndex.toInt()).toDouble()
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the subscription ID for SIM currently chosen to provide cellular internet connection to the user.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getActiveDataSubscriptionId()
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#INVALID_SUBSCRIPTION_ID
   *
   * @return Active data subscription id if any is chosen, or INVALID_SUBSCRIPTION_ID if not.
   * @throws Error If the calling SDK < 30.
   */
  override fun getActiveDataSubscriptionId(): Double {
    try {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) throw Error(MIN_VERSION_R)
      return SubscriptionManager.getActiveDataSubscriptionId().toDouble()
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the subscription info of SIM associated with given subscription ID.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getActiveSubscriptionInfo(int)
   *
   * @return A WritableMap containing the subscription info of given subscription.
   * @throws Error If the required permissions are not granted or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun getActiveSubscriptionInfo(subscriberId: Double): WritableMap {
    try {
      if (!hasReadPhoneStatePermission(reactApplicationContext) && !hasCarrierPrivilege(reactApplicationContext))
        throw Error(READ_PHONE_STATE_OR_CARRIER_PERMISSION_REQUIRED)
      val subInfo : SubscriptionInfo = subscriptionManager?.getActiveSubscriptionInfo(subscriberId.toInt())
        ?: return Arguments.createMap()
      return convertSubscriptionInfoToWritableMap(subInfo, reactApplicationContext)
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the current number of active SIM(s).
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getActiveSubscriptionInfoCount()
   *
   * @return The current number of active subscriptions.
   * @throws Error If the required permissions are not granted or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun getActiveSubscriptionInfoCount(): Double {
    try {
      if (!hasReadPhoneStatePermission(reactApplicationContext)) throw Error(READ_PHONE_STATE_PERMISSION_REQUIRED)
      return subscriptionManager?.activeSubscriptionInfoCount!!.toDouble()
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the active SIM Info associated with the slotIndex.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getActiveSubscriptionInfoForSimSlotIndex(int)
   *
   * @return A WritableMap containing the subscription info for given slot index.
   * @throws Error If the required permissions are not granted or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun getActiveSubscriptionInfoForSimSlotIndex(simSlotIndex: Double): WritableMap {
    try {
      if (!hasReadPhoneStatePermission(reactApplicationContext) && !hasCarrierPrivilege(reactApplicationContext))
        throw Error(READ_PHONE_STATE_OR_CARRIER_PERMISSION_REQUIRED)
      val subInfo : SubscriptionInfo = subscriptionManager?.getActiveSubscriptionInfoForSimSlotIndex(simSlotIndex.toInt())
        ?: return Arguments.createMap()
      return convertSubscriptionInfoToWritableMap(subInfo, reactApplicationContext)
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the list of active SIM(s).
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getActiveSubscriptionInfoList()
   *
   * @return A WritableArray containing the subscription info for all active SIM(s).
   * @throws Error If the required permissions are not granted or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun getActiveSubscriptionInfoList(): WritableArray {
    try {
      if (!hasReadPhoneStatePermission(reactApplicationContext) && !hasCarrierPrivilege(reactApplicationContext))
        throw Error(READ_PHONE_STATE_OR_CARRIER_PERMISSION_REQUIRED)
      val activeSubInfoArray: WritableArray = Arguments.createArray()
      val activeSubInfoList = subscriptionManager?.getActiveSubscriptionInfoList() ?: return activeSubInfoArray
      for (subscriptionInfo in activeSubInfoList) {
        val writableMap = convertSubscriptionInfoToWritableMap(subscriptionInfo, reactApplicationContext)
        activeSubInfoArray.pushMap(writableMap)
      }
      return activeSubInfoArray
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the list of all subscription info records from SIMs that are inserted now or previously inserted.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getAllSubscriptionInfoList()
   *
   * @return A WritableArray containing the subscription info for all SIM(s) inserted now or in past.
   * @throws Error If the required permissions are not granted, API level is not supported, or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun getAllSubscriptionInfoList(): WritableArray {
    try {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) throw Error(
        MIN_VERSION_UPSIDE_DOWN)
      if (!hasReadPhoneStatePermission(reactApplicationContext) && !hasCarrierPrivilege(reactApplicationContext))
        throw Error(READ_PHONE_STATE_OR_CARRIER_PERMISSION_REQUIRED)
      val allSubInfoArray: WritableArray = Arguments.createArray()
      val allSubInfoList = subscriptionManager?.getAllSubscriptionInfoList() ?: return allSubInfoArray
      for (subscriptionInfo in allSubInfoList) {
        val writableMap = convertSubscriptionInfoToWritableMap(subscriptionInfo, reactApplicationContext)
        allSubInfoArray.pushMap(writableMap)
      }
      return allSubInfoArray
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the list of both hidden and visible SubscriptionInfo(s) of the currently active SIM(s).
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getCompleteActiveSubscriptionInfoList()
   *
   * @return A WritableArray containing the subscription info for hidden and visible SubscriptionInfo(s) of the currently active SIM(s).
   * @throws Error If the required permissions are not granted, API level is not supported, or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun getCompleteActiveSubscriptionInfoList(): WritableArray {
    try {
      if (!hasReadPhoneStatePermission(reactApplicationContext) && !hasCarrierPrivilege(reactApplicationContext))
        throw Error(READ_PHONE_STATE_OR_CARRIER_PERMISSION_REQUIRED)
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) throw Error(
        MIN_VERSION_R)
      val completeSubInfoArray: WritableArray = Arguments.createArray()
      val allSubInfoList = subscriptionManager?.completeActiveSubscriptionInfoList ?: return completeSubInfoArray
      for (subscriptionInfo in allSubInfoList) {
        val writableMap = convertSubscriptionInfoToWritableMap(subscriptionInfo, reactApplicationContext)
        completeSubInfoArray.pushMap(writableMap)
      }
      return completeSubInfoArray
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the system's default SIM card data subscription id.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getDefaultDataSubscriptionId()
   *
   * @return The default data subscription id or INVALID_SUBSCRIPTION_ID if on a voice only device.
   * @throws Error If an unknown error occurs.
   */
  override fun getDefaultDataSubscriptionId(): Double {
    try {
      return SubscriptionManager.getDefaultDataSubscriptionId().toDouble()
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the system's default SIM card SMS subscription id.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getDefaultSmsSubscriptionId()
   *
   * @return The default data subscription id or INVALID_SUBSCRIPTION_ID if on a data only device.
   * @throws Error If an unknown error occurs.
   */
  override fun getDefaultSmsSubscriptionId(): Double {
    try {
      return SubscriptionManager.getDefaultSmsSubscriptionId().toDouble()
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the system's default SIM card subscription id.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getDefaultSubscriptionId()
   *
   * @return Returns the system's default subscription id. For a voice capable device, it will return
   * getDefaultVoiceSubscriptionId. For a data only device, it will return the getDefaultDataSubscriptionId.
   * May return an INVALID_SUBSCRIPTION_ID on error.
   * @throws Error If an unknown error occurs.
   */
  override fun getDefaultSubscriptionId(): Double {
    try {
      return SubscriptionManager.getDefaultSubscriptionId().toDouble()
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the slot index associated with given subscription id.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getSlotIndex(int)
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#INVALID_SIM_SLOT_INDEX
   *
   * @return The slotIndex as a positive integer or INVALID_SIM_SLOT_INDEX if the supplied subscriptionId
   * doesn't have an associated slot index..
   * @throws Error If an unknown error occurs.
   */
  override fun getSlotIndex(subscriberId: Double): Double {
    try {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) throw Error(MIN_VERSION_Q)
      return SubscriptionManager.getSlotIndex(subscriberId.toInt()).toDouble()
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the no of sim slot index in device.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/TelephonyManager#getActiveModemCount()
   *
   * @return The number of logical modems currently configured to be activated. Value ranges from 0-3.
   * @throws Error If the calling SDK < 30.
   */
  override fun getNoOfSIMSlotAvailable(): Double {
    try {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) throw Error(MIN_VERSION_R)
      return telephonyManager!!.activeModemCount.toDouble()
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the most recently available signal strength information for the given SIM.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/TelephonyManager#getSignalStrength()
   *
   * @return Value describing the signal strength.
   * @throws Error If the calling SDK < 28 or another error occurs.
   */
  override fun getSignalStrength(subscriberId: Double): Double {
    try {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) throw Error(MIN_VERSION_P)
      val currentTelephonyManager = if (subscriberId > 0) telephonyManager!!.createForSubscriptionId(subscriberId.toInt()) else telephonyManager
      return currentTelephonyManager!!.signalStrength?.level?.toDouble() ?: throw Error(SIGNAL_STRENGTH_ERROR)
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the phone number associated for the SIM associated with the given subscriber id.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionInfo#getNumber()
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getPhoneNumber(int)
   *
   * @return A String containing the phone number if found otherwise empty string.
   * @throws Error If the required permissions are not granted, API level is not supported, or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun getPhoneNumber(subscriberId: Double): String {
    try {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        val activeSubscriptions = subscriptionManager?.activeSubscriptionInfoList
        if (activeSubscriptions != null) {
          for (subscriptionInfo in activeSubscriptions) {
            if (subscriptionInfo.subscriptionId == subscriberId.toInt()) return subscriptionInfo.number
          }
        }
      } else {
        return subscriptionManager!!.getPhoneNumber(subscriberId.toInt())
      }
    } catch (e: Exception) {
      throw e
    }
    return ""
  }

  /**
   * Retrieves all the phone numbers associated with the active SIM card present in device.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionInfo#getNumber()
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getPhoneNumber(int)
   *
   * @return An array of phone numbers.
   * @throws Error If the required permissions are not granted, API level is not supported, or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun getAllPhoneNumbers(): WritableArray {
    val phoneNumbersArray: WritableArray = Arguments.createArray()
    try {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        val activeSubscriptions = subscriptionManager?.activeSubscriptionInfoList
        if (activeSubscriptions != null) {
          for (subscriptionInfo in activeSubscriptions) {
            phoneNumbersArray.pushString(subscriptionInfo.number)
          }
        }
      } else {
        val subIds = subscriptionIds
        for (i in 0 until subIds.size()) {
          phoneNumbersArray.pushString(subscriptionManager!!.getPhoneNumber(subIds.getInt(i)))
        }
      }
    } catch (e: Exception) {
      throw e
    }
    return phoneNumbersArray
  }

  /**
   * Checks if the given subscriber id is active or not.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#isActiveSubscriptionId(int)
   *
   * @return True if the supplied subscription ID corresponds to an active subscription, false otherwise.
   * @throws Error If the required permissions are not granted, API level is not supported, or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun isActiveSubscriptionId(subscriberId: Double): Boolean {
    try {
      if (!hasReadPhoneStatePermission(reactApplicationContext)) throw Error(READ_PHONE_STATE_PERMISSION_REQUIRED)
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) throw Error(MIN_VERSION_Q)
      return subscriptionManager?.isActiveSubscriptionId(subscriberId.toInt()) == true
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Retrieves the subscriber id for the given phone number.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionInfo#getNumber()
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#getPhoneNumber(int)
   *
   * @return Subscriber id if found, 0 otherwise.
   * @throws Error If the required permissions are not granted, API level is not supported, or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun getSubscriberIdForPhoneNumber(phoneNumber: String?): Double {
    if (phoneNumber == null) throw Error(NULL_PHONE_NUMBER)
    try {
      val activeSubscriptions = subscriptionManager?.activeSubscriptionInfoList
      if (activeSubscriptions != null) {
        for (subscriptionInfo in activeSubscriptions) {
          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (subscriptionInfo.number.contains(phoneNumber)) return subscriptionInfo.subscriptionId.toDouble()
          } else {
            if (subscriptionManager!!.getPhoneNumber(subscriptionInfo.subscriptionId).contains(phoneNumber)) return subscriptionInfo.subscriptionId.toDouble()
          }
        }
      }
    } catch (e: Exception) {
      throw e
    }
    return 0.0
  }

  /**
   * Retrieves the roaming status for the given subscriber id.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionManager#isNetworkRoaming(int)
   *
   * @return Returns true if the device is considered roaming on the current network for a subscription..
   * @throws Error If an unknown error occurs.
   */
  override fun isNetworkRoaming(subscriberId: Double): Boolean {
    try {
      return subscriptionManager?.isNetworkRoaming(subscriberId.toInt()) == true
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Checks whether Airplane Mode is currently enabled on the device.
   *
   * This method accesses the system's global settings to determine the Airplane Mode status.
   * It retrieves the value of the AIRPLANE_MODE_ON setting and checks if it is not equal to 0
   * (0 indicates that Airplane Mode is off, and any non-zero value indicates it is on).
   *
   * @return `true` if Airplane Mode is enabled, `false` otherwise.
   */
  override fun isAirplaneMode(): Boolean {
    return Settings.Global.getInt(
      reactApplicationContext.contentResolver,
      Settings.Global.AIRPLANE_MODE_ON, 0
    ) != 0
  }

  /**
   * Checks if the SIM associated with the given subscription id is an ESIM or not.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/SubscriptionInfo#isEmbedded()
   *
   * @return Boolean value representing status of whether the sim is embedded or not
   * @throws Error If the required permissions are not granted, API level is not supported, SIM not found, or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun isESIM(subscriberId: Double): Boolean {
    try {
      if (!hasReadPhoneStatePermission(reactApplicationContext) && !hasCarrierPrivilege(reactApplicationContext))
        throw Error(READ_PHONE_STATE_OR_CARRIER_PERMISSION_REQUIRED)
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) throw Error(MIN_VERSION_P)
      val activeSubscriptions = subscriptionManager?.activeSubscriptionInfoList ?: throw Error(GIVEN_SIM_DOES_NOT_EXIST)
      for (subscription in activeSubscriptions) {
        if (subscription.subscriptionId != subscriberId.toInt()) continue
        return subscription.isEmbedded
      }
      throw Error(GIVEN_SIM_DOES_NOT_EXIST)
    } catch (e:Error) {
      throw e
    }
  }

  /**
   * Checks if the mobile data is enabled or not per user setting.
   *
   * For more details refer to the official documentation:
   * https://developer.android.com/reference/android/telephony/TelephonyManager#isDataEnabled()
   *
   * @return Boolean value representing if mobile data is enabled.
   * @throws Error If the required permissions are not granted, API level is not supported, or another error occurs.
   */
  @SuppressLint("MissingPermission")
  override fun isMobileDataEnabled(subscriberId: Double): Boolean {
    try {
      if (!hasRequiredPermissionsOrCarrierPrivileges(reactApplicationContext)) throw Error(REQUIRE_PERMISSION)
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) throw Error(MIN_VERSION_O)
      val currentTelephonyManager = if (subscriberId > 0) telephonyManager!!.createForSubscriptionId(subscriberId.toInt()) else telephonyManager
      return currentTelephonyManager!!.isDataEnabled
    } catch (e:Error) {
      throw e
    }
  }

  override fun isDeviceEsimCompatible(): Boolean {
    try {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) return false
      val euiccManager =
        reactApplicationContext.getSystemService(Context.EUICC_SERVICE) as EuiccManager?

      return euiccManager != null && euiccManager.isEnabled
    } catch (e: Error) {
      throw e
    }
  }

  override fun addOnAirplaneChangeListener() {
    if (airplaneModeListenerCount++ == 0) {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
        reactApplicationContext.registerReceiver(airplaneModeChangeBR, IntentFilter("android.intent.action.AIRPLANE_MODE"))
      } else {
        reactApplicationContext.registerReceiver(airplaneModeChangeBR, IntentFilter("android.intent.action.AIRPLANE_MODE"), ComponentActivity.RECEIVER_EXPORTED)
      }
    }
  }

  override fun addOnSimCardStateChangeListener() {
    Log.d("simStateChangeListener", "Added")
    if (subscriptionManagerListenerCount++ == 0) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        simStateChangeListener?.let {
          subscriptionManager?.addOnSubscriptionsChangedListener(
            reactApplicationContext.mainExecutor,
            it
          )
        }
      } else {
        simStateChangeListener?.let {
          subscriptionManager?.addOnSubscriptionsChangedListener(it)
        }
      }
    }
  }

  override fun removeOnAirplaneChangeListener() {
    airplaneModeListenerCount--
    if (airplaneModeListenerCount == 0) {
      reactApplicationContext.unregisterReceiver(airplaneModeChangeBR)
    }
  }

  override fun removeOnSimCardStateChangeListener() {
    Log.d("simStateChangeListener", "Removed")
    subscriptionManagerListenerCount--
    if (subscriptionManagerListenerCount == 0) {
      subscriptionManager?.removeOnSubscriptionsChangedListener(simStateChangeListener)
    }
  }

  override fun onAirplaneModeChanged(isAirplaneModeOn: Boolean) {
    emitOnAirplaneModeChange(isAirplaneModeOn)
  }

  companion object {
    const val NAME = "SimcardInfo"
  }
}
