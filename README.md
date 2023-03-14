# react-native-proximity-sensor

Library for React Native to work with proximity-sensor. 

## Installation

```sh
npm install react-native-proximity-sensor

cd ios && pod install
```

or

```sh
yarn add react-native-proximity-sensor

cd ios && pod install
```


## Usage

1. Subscribe on listening Proximity sensor

```js
import { ProximitySensor } from 'react-native-proximity-sensor';

// ...

ProximitySensor.start();


```

2. Listen for changes

```tsx

import { ProximitySensor } from 'react-native-proximity-sensor';
// ...

useEffect(() => {
    const subscription = ProximitySensor.onChangeListener((isNear) => {
      console.warn('isNear: ', isNear);
    });

    return () => {
      subscription.remove();
    };
  }, []);

```

3. Stop listening

```js

import { ProximitySensor } from 'react-native-proximity-sensor';


// ...

ProximitySensor.stop();

```

# Android

Android required some additional steps to work with Proximity sensor. Just add permissions to AndroidManifest.xml:

```xml

<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.hardware.sensor.proximity"/>

```

# New architecture 

## ios

Install pods with right flag:

```sh
RCT_NEW_ARCH_ENABLED=1 pod install
```

## android

Add to `android/gradle.properties`:
```gradle
newArchEnabled=true
```


## License

MIT