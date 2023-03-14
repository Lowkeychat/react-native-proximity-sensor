
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNProximitySensorSpec.h"
#import <React/RCTEventEmitter.h>

@interface ProximitySensor : RCTEventEmitter <NativeProximitySensorSpec>
#else
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface ProximitySensor : RCTEventEmitter <RCTBridgeModule>
#endif

@end
