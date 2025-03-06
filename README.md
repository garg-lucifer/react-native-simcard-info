# react-native-simcard-info

This library provides an easy-to-integrate solution for managing SIM card information, detecting SIM state changes in real-time, and controlling Airplane mode, all while fully supporting the new React Native architecture.

## Why choose this library?

- **Comprehensive SIM Management**: Get detailed SIM card information such as SIM IDs, carrier names, signal strength, roaming status, and phone numbers, all at your fingertips.
- **Track State Changes**: Effortlessly manage SIM state changes such as SIM removal/insertion using hooks.
- **Optimized for New React Native Architecture**: Specifically designed to support the latest React Native architecture, ensuring smoother integration and better performance.

# Installation

```sh
npm install react-native-simcard-info
```

## SIM Card Information

This section provides an example of the output you can expect when retrieving SIM information using the API.

### Example Output

```json
{
  "areUiccApplicationsEnabled": "true",
  "cardId": "1",
  "carrierConfigAccessRules": "null",
  "carrierId": "2018",
  "carrierName": "Jio",
  "countryIso": "in",
  "dataRoaming": "0",
  "displayName": "SIM",
  "displayNameSource": "CARRIER_ID",
  "ehplmns": "[]",
  "groupUuid": "null",
  "hplmns": "[405872]",
  "iconTint": "#00796B",
  "id": "2",
  "isEmbedded": "false",
  "isGroupDisabled": "false",
  "isOpportunistic": "false",
  "mType": "LOCAL_SIM",
  "mcc": "405",
  "mnc": "872",
  "nativeAccessRules": "null",
  "number": "+911234567890",
  "portIndex": "-1",
  "profileClass": "-1",
  "simSlotIndex": "0",
  "usageSetting": "DEFAULT"
}
```

## Functions

### `getSIMIds`

- **Description**: Retrieves the subscription IDs of all active SIM(s) in the device. This function pulls the active SIM IDs, which are used to identify SIM cards on Android devices.
- **Usage**:

  ```javascript
  import { getSIMIds } from 'react-native-simcard-info';
  console.log('SIM IDs:', getSIMIds());
  ```

- **Return Type**: `Array<number>`

### `getSIMId`

- **Description**: Retrieves the subscription ID for the SIM card at the given slot index. If no SIM card is present in the specified slot, it returns -1.

- **Usage**:

  ```javascript
  import { getSIMId } from 'react-native-simcard-info';
  console.log('SIM ID for slot 0:', getSIMId(0));
  ```

- **Return Type**: `number`

### `getActiveDataSIMId`

- **Description**: Retrieves the subscription ID for the SIM card currently chosen to provide cellular internet connection to the user. If no SIM is selected for data, it returns -1.

- **Usage**:

  ```javascript
  import { getActiveDataSIMId } from 'react-native-simcard-info';
  console.log('Active data SIM ID:', getActiveDataSIMId());
  ```

- **Return Type**: `number`

### `getActiveSIMInfo`

- **Description**: Retrieves the subscription information for the SIM associated with the given subscription ID. This function returns detailed info about the SIM, such as carrier, country, phone number, and subscription type.

- **Usage**:

  ```javascript
  import { getActiveSIMInfo } from 'react-native-simcard-info';
  console.log('Active SIM info for SIM ID 1:', getActiveSIMInfo(1));
  ```

- **Return Type**: `Object`

### `getActiveSIMCount`

- **Description**: Retrieves the current number of active SIM(s) in the device. This function provides the count of SIMs that are currently active and in use for cellular services.

- **Usage**:

  ```javascript
  import { getActiveSIMCount } from 'react-native-simcard-info';
  console.log('Active SIM count:', getActiveSIMCount());
  ```

- **Return Type**: `number`

### `getActiveSIMInfoForSimSlotIndex`

- **Description**: Retrieves the active SIM information associated with the specified slot index. This function provides detailed information about the SIM card in a specific slot, such as carrier, country, and subscription type.

- **Usage**:

  ```javascript
  import { getActiveSIMInfoForSimSlotIndex } from 'react-native-simcard-info';
  console.log(
    'Active SIM info for slot 0:',
    getActiveSIMInfoForSimSlotIndex(0)
  );
  ```

- **Return Type**: `number`

### `getActiveSIMInfoList`

- **Description**: Retrieves a list of active SIM(s) in the device. This function provides subscription information for all active SIM cards currently in use.

- **Usage**:

  ```javascript
  import { getActiveSIMInfoList } from 'react-native-simcard-info';
  console.log('Active SIM info list:', getActiveSIMInfoList());
  ```

- **Return Type**: `Array<Object>`

### `getAllSIMInfoList`

- **Description**: Retrieves the list of all subscription info records from SIMs that are currently inserted or were previously inserted in the device. This function returns detailed subscription information for all SIM cards, whether they are active or inactive.

- **Usage**:

  ```javascript
  import { getAllSIMInfoList } from 'react-native-simcard-info';
  console.log('All SIM info list:', getAllSIMInfoList());
  ```

- **Return Type**: `Array<Object>`

### `getCompleteActiveSIMInfoList`

- **Description**: Retrieves the list of both hidden and visible `SubscriptionInfo` for the currently active SIM(s). This function provides comprehensive information about all active SIMs, including both hidden and visible subscription details.

- **Usage**:

  ```javascript
  import { getCompleteActiveSIMInfoList } from 'react-native-simcard-info';
  console.log('Complete active SIM info list:', getCompleteActiveSIMInfoList());
  ```

- **Return Type**: `Array<Object>`

### `getDefaultDataSIMId`

- **Description**: Retrieves the system's default SIM card data subscription ID. This function identifies the SIM card currently selected to provide data services.

- **Usage**:

  ```javascript
  import { getDefaultDataSIMId } from 'react-native-simcard-info';
  console.log('Default data SIM ID:', getDefaultDataSIMId());
  ```

- **Return Type**: `number`

### `getDefaultSMSSIMId`

- **Description**: Retrieves the system's default SIM card SMS subscription ID. This function identifies the SIM card that is currently used for sending and receiving SMS messages.

- **Usage**:

  ```javascript
  import { getDefaultSMSSIMId } from 'react-native-simcard-info';
  console.log('Default SMS SIM ID:', getDefaultSMSSIMId());
  ```

- **Return Type**: `number`

### `getDefaultSIMId`

- **Description**: Retrieves the system's default SIM card subscription ID. This function determines which SIM card is currently designated as the default for both voice and data services.

- **Usage**:

  ```javascript
  import { getDefaultSIMId } from 'react-native-simcard-info';
  console.log('Default SIM ID:', getDefaultSIMId());
  ```

- **Return Type**: `number`

### `getSlotIndex`

- **Description**: Retrieves the slot index associated with a given subscription ID. The slot index indicates the physical SIM slot corresponding to the specified subscription.

- **Usage**:

  ```javascript
  import { getSlotIndex } from 'react-native-simcard-info';
  console.log('Slot index for SIM ID 1:', getSlotIndex(1));
  ```

- **Return Type**: `number`

### `getNoOfSIMSlotAvailable`

- **Description**: Retrieves the number of SIM slots available on the device. This function provides information about the total physical SIM slots supported by the device, regardless of whether they are active or inactive.

- **Usage**:

  ```javascript
  import { getNoOfSIMSlotAvailable } from 'react-native-simcard-info';
  console.log('Number of SIM slots available:', getNoOfSIMSlotAvailable());
  ```

- **Return Type**: `number`

### `getSignalStrength`

- **Description**: Retrieves the signal strength for the specified SIM ID. This function is useful for monitoring network strength..

- **Usage**:

  ```javascript
  import { getSignalStrength } from 'react-native-simcard-info';
  console.log('Signal strength for SIM ID 1:', getSignalStrength(1));
  // 0 represents very poor signal strength while 4 represents a very strong signal strength.
  ```

- **Return Type**: `number`

### `getPhoneNumber`

- **Description**: Retrieves the phone number associated with the SIM corresponding to the given subscriber ID. This function helps in identifying the phone number linked to a specific SIM card.

- **Usage**:

  ```javascript
  import { getPhoneNumber } from 'react-native-simcard-info';
  console.log('Phone number for SIM ID 1:', getPhoneNumber(1));
  ```

- **Return Type**: `String`

### `getAllPhoneNumbers`

- **Description**: Retrieves all the phone numbers associated with the active SIM cards present in the device. This function is useful for devices with multiple SIM cards to fetch phone numbers linked to each active SIM.

- **Usage**:

  ```javascript
  import { getAllPhoneNumbers } from 'react-native-simcard-info';
  console.log('All phone numbers:', getAllPhoneNumbers());
  ```

- **Return Type**: `Array<String>`

### `getSIMIdForPhoneNumber`

- **Description**: Retrieves the subscriber ID (SIM ID) associated with the provided phone number. This function is useful for identifying which SIM card corresponds to a specific phone number in devices with multiple active SIM cards.

- **Usage**:

  ```javascript
  import { getSIMIdForPhoneNumber } from 'react-native-simcard-info';

  console.log(
    'SIM ID for phone number 1234567890:',
    getSIMIdForPhoneNumber('1234567890')
  );
  ```

- **Return Type**: `number`

### `isActiveSIMId`

**Description**: Checks if the given subscriber ID corresponds to an active subscription.

**Usage**:

```javascript
import { isActiveSIMId } from 'react-native-simcard-info';
console.log('Is SIM ID 1 active:', isActiveSIMId(1));
```

### `isNetworkRoaming`

**Description**: Retrieves the roaming status for the given subscriber ID.

**Usage**:

```javascript
import { isNetworkRoaming } from 'react-native-simcard-info';
console.log('Is network roaming for SIM ID 1:', isNetworkRoaming(1));
```

### `isAirplaneMode`

**Description**: Checks whether Airplane Mode is currently enabled on the device.

**Usage**:

```javascript
import { isAirplaneMode } from 'react-native-simcard-info';
console.log('Is airplane mode on:', isAirplaneMode());
```

### `isESIM`

**Description**: Checks if the SIM associated with the given subscription ID is an eSIM.

**Usage**:

```javascript
import { isESIM } from 'react-native-simcard-info';
console.log('Is SIM ID 1 an eSIM:', isESIM(1));
```

### `isMobileDataEnabled`

**Description**: Checks if mobile data is enabled for the given subscription ID based on user settings.

**Usage**:

```javascript
import { isMobileDataEnabled } from 'react-native-simcard-info';
console.log('Is mobile data enabled for SIM ID 1:', isMobileDataEnabled(1));
```

## Hooks

### `useAirplaneMode`

**Description**: Listens for changes in airplane mode.

**Usage**:

```javascript
import { useAirplaneMode } from 'react-native-simcard-info';
const { isAirplaneModeOn } = useAirplaneMode();
console.log('AIRPLANE MODE LISTENER', isAirplaneModeOn);
```

- **Return Type**: `boolean`

### `useSIMStateChange`

**Description**: Listens for changes in the SIM state (e.g., inserted, removed, or changed).

**Usage**:

```javascript
import { useSIMStateChange } from 'react-native-simcard-info';
const { simState } = useSIMStateChange();
console.log('SIM CHANGE LISTENER', simState);
```

- **Return Type**: `Array<Object>`

### Custom Usage: `onAirplaneModeChange`

**Description**: This example demonstrates how to use the `startAirplaneListener`, `stopAirplaneListener`, and `onAirplaneModeChange` functions to listen for changes in airplane mode and implement custom behavior when the airplane mode status changes.

**Usage**:

```javascript
import { useEffect } from 'react';
import {
  startAirplaneListener,
  stopAirplaneListener,
  onAirplaneModeChange,
} from 'react-native-simcard-info';

useEffect(() => {
  // Start listening for airplane mode changes
  startAirplaneListener();

  // Custom implementation for handling airplane mode changes
  const airplaneModeCallback = (status) => {
    console.log('Custom Implementation Airplane Mode', status);
  };

  // Set the callback to handle airplane mode changes
  onAirplaneModeChange(airplaneModeCallback);

  // Clean up the listener when the component unmounts
  return () => stopAirplaneListener();
}, []);
```

### Custom Usage: `onSIMCardStateChange`

**Description**: This example demonstrates how to use the `startSIMChangeListener`, `stopSIMChangeListener`, and `onSIMCardStateChange` functions to listen for SIM card state changes and implement custom behavior when the SIM state changes.

**Usage**:

```javascript
import { useEffect } from 'react';
import {
  startSIMChangeListener,
  stopSIMChangeListener,
  onSIMCardStateChange,
} from 'react-native-simcard-info';

useEffect(() => {
  // Start listening for SIM card state changes
  startSIMChangeListener();

  // Custom implementation for handling SIM card state changes
  const simStateCallback = (simState) => {
    console.log('Custom Implementation SIM State', simState);
  };

  // Set the callback to handle SIM state changes
  onSIMCardStateChange(simStateCallback);

  // Clean up the listener when the component unmounts
  return () => stopSIMChangeListener();
}, []);
```

## Note

- Certain methods mentioned above require specific permissions to function correctly, while others may necessitate a minimum calling SDK version. For detailed information, please refer to [here](https://github.com/garg-lucifer/react-native-simcard-info/blob/main/android/src/main/java/com/simcardinfo/SimcardInfoModule.kt)
- If the required permissions are not granted or the calling SDK version is below the minimum requirement, an appropriate error will be thrown, clearly indicating the issue.
- The term "SIM ID" used here refers to the subscriber ID, a unique numeric identifier assigned to each active mobile subscription (SIM card) on a device. It is always represented as a positive integer.

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
