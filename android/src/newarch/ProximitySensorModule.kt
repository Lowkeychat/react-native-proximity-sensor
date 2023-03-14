package com.proximitysensor

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext


class ProximitySensorModule internal constructor(context: ReactApplicationContext) :
  NativeProximitySensorSpec(context) {

  private val proximitySensorImpl: ProximitySensorImpl = ProximitySensorImpl(context)

  override fun getName(): String {
    return NAME;
  }

  override fun start(promise: Promise) {
    proximitySensorImpl.start(promise)
  }

  override fun stop(promise: Promise) {
    proximitySensorImpl.stop(promise)
  }

  override fun addListener(eventType: String) {
    proximitySensorImpl.addListener(eventType)
  }

  override fun removeListeners(count: Double) {
    proximitySensorImpl.removeListeners(count.toInt())
  }


  companion object {
    const val NAME = ProximitySensorImpl.NAME;
  }

}
