import React, { useCallback, useEffect } from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { ProximitySensor } from 'react-native-proximity-sensor';

export default function App() {
  const [enabled, setEnabled] = React.useState(false);

  useEffect(() => {
    const subscription = ProximitySensor.onChangeListener((isNear) => {
      console.warn('isNear: ', isNear);
    });

    return () => {
      subscription.remove();
    };
  }, []);

  const toggle = useCallback(() => {
    if (enabled) {
      ProximitySensor.stop();
    } else {
      ProximitySensor.start();
    }
    setEnabled(!enabled);
  }, [enabled]);

  return (
    <View style={styles.container}>
      <Text>Enabled: {enabled.toString()}</Text>
      <View style={styles.box} />
      <Text onPress={toggle}>Toggle</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
