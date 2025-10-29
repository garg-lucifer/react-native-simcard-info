export { useAirplaneMode } from './hooks/useAirplaneMode';
export { useSIMStateChange } from './hooks/useSIMStateChange';
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
  isDeviceEsimCompatible,
  // manage event listeners
  startAirplaneListener,
  stopAirplaneListener,
  startSIMChangeListener,
  stopSIMChangeListener,
  //event listeners
  onAirplaneModeChange,
  onSIMCardStateChange,
} from './SIMFeatureImpl';
