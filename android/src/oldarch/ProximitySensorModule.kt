package com.proximitysensor

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class ProximitySensorModule internal constructor(context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context) {
  private val proximitySensorImpl: ProximitySensorImpl = ProximitySensorImpl(context)

  override fun getName(): String {
    return NAME
  }


  @ReactMethod
  fun start(promise: Promise) {
    proximitySensorImpl.start(promise)
  }

  @ReactMethod
  fun stop(promise: Promise) {
    proximitySensorImpl.stop(promise)
  }

  @ReactMethod
  fun addListener(eventName: String) {
    proximitySensorImpl.addListener(eventName)
  }

  @ReactMethod
  fun removeListeners(count: Int) {
    proximitySensorImpl.removeListeners(count)
  }


  companion object {
    const val NAME = ProximitySensorImpl.NAME;
  }
}
