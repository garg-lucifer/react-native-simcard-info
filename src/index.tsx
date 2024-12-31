export { useAirplaneMode } from '../src/hooks/useAirplaneMode';
export { useSIMStateChange } from '../src/hooks/useSIMStateChange';
export {
  //getters
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
  //booleans
  isActiveSIMId,
  isNetworkRoaming,
  isAirplaneMode,
  isESIM,
  isMobileDataEnabled,
  // manage event listeners
  startAirplaneListener,
  stopAirplaneListener,
  startSIMChangeListener,
  stopSIMChangeListener,
  //event listeners
  onAirplaneModeChange,
  onSIMCardStateChange,
} from '../src/SIMFeatureImpl';
