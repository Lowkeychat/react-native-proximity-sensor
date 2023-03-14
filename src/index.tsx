import {
  NativeModules,
  Platform,
  NativeEventEmitter,
  EmitterSubscription,
} from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-proximity-sensor' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;

const ProximitySensorModule = isTurboModuleEnabled
  ? require('./NativeProximitySensor').default
  : NativeModules.ProximitySensor;

const ProximitySensorCls = ProximitySensorModule
  ? ProximitySensorModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const eventEmitter = new NativeEventEmitter(ProximitySensorModule);

export const ProximitySensor = {
  start: ProximitySensorCls.start,
  stop: ProximitySensorCls.stop,
  onChangeListener: (
    onChangeCallback: (isNear: boolean) => void
  ): EmitterSubscription => {
    return eventEmitter.addListener('Proximity', (e) => {
      onChangeCallback(e.isNear);
    });
  },
};
