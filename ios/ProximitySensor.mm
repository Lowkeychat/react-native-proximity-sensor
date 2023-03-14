#import "ProximitySensor.h"


@implementation ProximitySensor
{
  bool hasListeners;
  bool currentState;
}
RCT_EXPORT_MODULE(ProximitySensor)


-(void)startObserving {
    hasListeners = YES;
}

-(void)stopObserving {
    hasListeners = NO;
}


RCT_REMAP_METHOD(start,
                 withResolverStart:(RCTPromiseResolveBlock)resolve
                 withRejecterStart:(RCTPromiseRejectBlock)reject)
{
    [self start:resolve reject:reject];
}

RCT_REMAP_METHOD(stop,
                 withResolverStop:(RCTPromiseResolveBlock)resolve
                 withRejecterStop:(RCTPromiseRejectBlock)reject)
{
    [self stop:resolve reject:reject];
}


- (void)sensorStateMonitor:(NSNotificationCenter *)notification
{
    if (hasListeners) {// Only send events if anyone is listening
        BOOL isNear = [[UIDevice currentDevice] proximityState];
        if(currentState != isNear) {
            NSMutableDictionary* body = [[NSMutableDictionary alloc] init];
            currentState = isNear;
            body[@"isNear"] = @(isNear);
            
            [self sendEventWithName:@"Proximity" body: body];
        }
    }
}

- (void)stop:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    dispatch_async(dispatch_get_main_queue(), ^{
        [UIDevice.currentDevice setProximityMonitoringEnabled:NO];
        [[NSNotificationCenter defaultCenter] removeObserver:self];
        resolve(nil);
    });
    
    
    if(hasListeners  && currentState != NO) {
        NSMutableDictionary* body = [[NSMutableDictionary alloc] init];
        body[@"isNear"] = @(NO);
        
        currentState = NO;
        
        
        [self sendEventWithName:@"Proximity" body: body];
    }
}

- (void)start:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
    dispatch_async(dispatch_get_main_queue(), ^{
        [[UIDevice currentDevice] setProximityMonitoringEnabled:YES];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(sensorStateMonitor:) name:@"UIDeviceProximityStateDidChangeNotification" object:nil];
        resolve(nil);
    });
}


- (NSArray<NSString *> *) supportedEvents {
    return @[@"Proximity"];

}

// Don't compile this code when we build for the old architecture.
#ifdef RCT_NEW_ARCH_ENABLED
- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeProximitySensorSpecJSI>(params);
}
#endif




@end
