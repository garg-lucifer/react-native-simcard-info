import NativeSimcardInfo from './NativeSimcardInfo';
import type { EventSubscription } from 'react-native';

type AirplaneModeCallback = (isAirplaneModeOn: boolean) => void;
type SIMStateChangeCallback = (simState: Array<Object>) => void;

export const getSIMIds = (): Array<number> => {
  return NativeSimcardInfo.getSubscriptionIds();
};

export const getSIMId = (simSlotIndex: number): number => {
  return NativeSimcardInfo.getSubscriptionId(simSlotIndex);
};

export const getActiveDataSIMId = (): number => {
  return NativeSimcardInfo.getActiveDataSubscriptionId();
};

export const getActiveSIMInfo = (simId: number): Object => {
  return NativeSimcardInfo.getActiveSubscriptionInfo(simId);
};

export const getActiveSIMCount = (): number => {
  return NativeSimcardInfo.getActiveSubscriptionInfoCount();
};

export const getActiveSIMInfoForSimSlotIndex = (
  simSlotIndex: number
): Object => {
  return NativeSimcardInfo.getActiveSubscriptionInfoForSimSlotIndex(
    simSlotIndex
  );
};

export const getActiveSIMInfoList = (): Array<Object> => {
  return NativeSimcardInfo.getActiveSubscriptionInfoList();
};

export const getAllSIMInfoList = (): Array<Object> => {
  return NativeSimcardInfo.getAllSubscriptionInfoList();
};

export const getCompleteActiveSIMInfoList = (): Array<Object> => {
  return NativeSimcardInfo.getCompleteActiveSubscriptionInfoList();
};

export const getDefaultDataSIMId = (): number => {
  return NativeSimcardInfo.getDefaultDataSubscriptionId();
};

export const getDefaultSMSSIMId = (): number => {
  return NativeSimcardInfo.getDefaultSmsSubscriptionId();
};

export const getDefaultSIMId = (): number => {
  return NativeSimcardInfo.getDefaultSubscriptionId();
};

export const getSlotIndex = (simId: number): number => {
  return NativeSimcardInfo.getSlotIndex(simId);
};

export const getNoOfSIMSlotAvailable = (): number => {
  return NativeSimcardInfo.getNoOfSIMSlotAvailable();
};

export const getSignalStrength = (simId: number = -1): number => {
  return NativeSimcardInfo.getSignalStrength(simId);
};

export const getPhoneNumber = (simId: number): string => {
  return NativeSimcardInfo.getPhoneNumber(simId);
};

export const getAllPhoneNumbers = (): Array<string> => {
  return NativeSimcardInfo.getAllPhoneNumbers();
};

export const getSIMIdForPhoneNumber = (phoneNumber: string): number => {
  return NativeSimcardInfo.getSubscriberIdForPhoneNumber(phoneNumber);
};

export const isActiveSIMId = (simId: number): boolean => {
  return NativeSimcardInfo.isActiveSubscriptionId(simId);
};

export const isNetworkRoaming = (simId: number): boolean => {
  return NativeSimcardInfo.isNetworkRoaming(simId);
};

export const isAirplaneMode = (): boolean => {
  return NativeSimcardInfo.isAirplaneMode();
};

export const isESIM = (simId: number): boolean => {
  return NativeSimcardInfo.isESIM(simId);
};

export const isMobileDataEnabled = (simId: number = -1): boolean => {
  return NativeSimcardInfo.isMobileDataEnabled(simId);
};

export const startAirplaneListener = (): void => {
  return NativeSimcardInfo.addOnAirplaneChangeListener();
};

export const stopAirplaneListener = (): void => {
  return NativeSimcardInfo.removeOnAirplaneChangeListener();
};

export const startSIMChangeListener = (): void => {
  return NativeSimcardInfo.addOnSimCardStateChangeListener();
};

export const stopSIMChangeListener = (): void => {
  return NativeSimcardInfo.removeOnSimCardStateChangeListener();
};

export const onAirplaneModeChange = (
  fn: AirplaneModeCallback
): EventSubscription => {
  return NativeSimcardInfo.onAirplaneModeChange(fn);
};

export const onSIMCardStateChange = (
  fn: SIMStateChangeCallback
): EventSubscription => {
  return NativeSimcardInfo.onSIMCardStateChange(fn);
};
