import { useEffect } from 'react';
import {
  useAirplaneMode,
  useSIMStateChange,
  getSIMIds,
  getSIMId,
  getActiveDataSIMId,
  getActiveSIMInfo,
  getActiveSIMCount,
  getActiveSIMInfoForSimSlotIndex,
  getActiveSIMInfoList,
  getAllSIMInfoList,
  getCompleteActiveSIMInfoList,
  getDefaultDataSIMId,
  getDefaultSMSSIMId,
  getDefaultSIMId,
  getSlotIndex,
  getNoOfSIMSlotAvailable,
  getSignalStrength,
  getPhoneNumber,
  getAllPhoneNumbers,
  getSIMIdForPhoneNumber,
  isActiveSIMId,
  isNetworkRoaming,
  isAirplaneMode,
  isESIM,
  isMobileDataEnabled,
  startAirplaneListener,
  stopAirplaneListener,
  startSIMChangeListener,
  stopSIMChangeListener,
  onAirplaneModeChange,
  onSIMCardStateChange,
} from 'react-native-simcard-info';

export default function App() {
  const { isAirplaneModeOn } = useAirplaneMode();
  const { simState } = useSIMStateChange();

  console.log('AIRPLANE MODE LISTENER', isAirplaneModeOn);
  console.log('SIM CHANGE LISTENER', simState);

  console.log('SIM IDs:', getSIMIds());
  console.log('SIM ID for slot 0:', getSIMId(0));
  console.log('Active data SIM ID:', getActiveDataSIMId());
  console.log('Active SIM info for SIM ID 1:', getActiveSIMInfo(1));
  console.log('Active SIM count:', getActiveSIMCount());
  console.log(
    'Active SIM info for slot 0:',
    getActiveSIMInfoForSimSlotIndex(0)
  );
  console.log('Active SIM info list:', getActiveSIMInfoList());
  console.log('All SIM info list:', getAllSIMInfoList());
  console.log('Complete active SIM info list:', getCompleteActiveSIMInfoList());
  console.log('Default data SIM ID:', getDefaultDataSIMId());
  console.log('Default SMS SIM ID:', getDefaultSMSSIMId());
  console.log('Default SIM ID:', getDefaultSIMId());
  console.log('Slot index for SIM ID 1:', getSlotIndex(1));
  console.log('Number of SIM slots available:', getNoOfSIMSlotAvailable());
  console.log('Signal strength for SIM ID 1:', getSignalStrength(1));
  console.log('Phone number for SIM ID 1:', getPhoneNumber(1));
  console.log('All phone numbers:', getAllPhoneNumbers());
  console.log(
    'SIM ID for phone number 1234567890:',
    getSIMIdForPhoneNumber('1234567890')
  );
  console.log('Is SIM ID 1 active:', isActiveSIMId(1));
  console.log('Is network roaming for SIM ID 1:', isNetworkRoaming(1));
  console.log('Is airplane mode on:', isAirplaneMode());
  console.log('Is SIM ID 1 an eSIM:', isESIM(1));
  console.log('Is mobile data enabled for SIM ID 1:', isMobileDataEnabled(1));

  useEffect(() => {
    startSIMChangeListener();
    onSIMCardStateChange((simStateCustom) => {
      console.log('Custom Implementation SIM State', simStateCustom);
    });
    return () => stopSIMChangeListener();
  }, []);

  useEffect(() => {
    startAirplaneListener();
    onAirplaneModeChange((status) => {
      console.log('Custom Implementation Airplane Mode', status);
    });
    return () => stopAirplaneListener();
  }, []);
}
