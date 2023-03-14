package com.proximitysensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.util.Log
import android.view.WindowManager
import com.facebook.react.ReactActivity
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter


class ProximitySensorImpl internal constructor(private val context: ReactApplicationContext) :
  SensorEventListener {
  private var listenerCount = 0
  private val proximitySensor: Sensor?
  private val sessionManager: SensorManager =
    context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
  private var wakeLock: PowerManager.WakeLock? = null
  private val powerManager: PowerManager =
    context.getSystemService(Context.POWER_SERVICE) as PowerManager

  init {
    proximitySensor = sessionManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
  }

  fun start(promise: Promise) {
    if (proximitySensor !== null) {
      sessionManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
      promise.resolve(null)
    }
  }

  fun stop(promise: Promise) {
    if (proximitySensor !== null) {
      sessionManager.unregisterListener(this)
      promise.resolve(null)
    }
  }

  companion object {
    const val NAME = "ProximitySensor"
  }

  override fun onSensorChanged(event: SensorEvent?) {
    val isNear = event?.values?.get(0) == 0f
    val params = Arguments.createMap()
    params.putBoolean("isNear", isNear)
    sendEvent(context, "Proximity", params)

    if (isNear) {
      screenOff()
    } else {
      screenOn()
    }
  }

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    Log.d("ProximitySensor", "Accuracy: $accuracy")
  }


  private fun sendEvent(
    reactContext: ReactContext,
    eventName: String,
    params: WritableMap?
  ) {
    reactContext
      .getJSModule(RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }

  /**
   * Disable screen
   */
  private fun screenOff() {
    wakeLock =
      powerManager.newWakeLock(
        PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,
        "ProximitySensor:screenOff"
      )

    wakeLock!!.acquire()

  }

  /**
   * Enable screen
   */
  private fun screenOn() {
    wakeLock?.release();
  }


  fun addListener(eventName: String?) {
    if (listenerCount == 0) {
      // Set up any upstream listeners or background tasks as necessary
    }
    listenerCount += 1
  }

  @ReactMethod
  fun removeListeners(count: Int) {
    listenerCount -= count
    if (listenerCount == 0) {
      // Remove upstream listeners, stop unnecessary background tasks
    }
  }
}
