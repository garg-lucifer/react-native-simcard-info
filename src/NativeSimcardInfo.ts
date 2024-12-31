import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import type { EventEmitter } from 'react-native/Libraries/Types/CodegenTypes';

export interface Spec extends TurboModule {
  getSubscriptionIds(): Array<number>;
  getSubscriptionId(simSlotIndex: number): number;
  getActiveDataSubscriptionId(): number;
  getActiveSubscriptionInfo(subscriberId: number): Object;
  getActiveSubscriptionInfoCount(): number;
  getActiveSubscriptionInfoForSimSlotIndex(simSlotIndex: number): Object;
  getActiveSubscriptionInfoList(): Array<Object>;
  getAllSubscriptionInfoList(): Array<Object>;
  getCompleteActiveSubscriptionInfoList(): Array<Object>;
  getDefaultDataSubscriptionId(): number;
  getDefaultSmsSubscriptionId(): number;
  getDefaultSubscriptionId(): number;
  getSlotIndex(subscriberId: number): number;
  getSimInfo(subscriberId: number): Object; // delete it
  getNoOfSIMSlotAvailable(): number;
  getSignalStrength(subscriberId: number): number;
  getPhoneNumber(subscriberId: number): string;
  getAllPhoneNumbers(): Array<string>;
  getSubscriberIdForPhoneNumber(phoneNumber: string): number;

  isActiveSubscriptionId(subscriberId: number): boolean;
  isNetworkRoaming(subscriberId: number): boolean;
  isAirplaneMode(): boolean;
  isESIM(subscriberId: number): boolean;
  isMobileDataEnabled(subscriberId: number): boolean;

  addOnAirplaneChangeListener(): void;
  addOnSimCardStateChangeListener(): void;

  removeOnAirplaneChangeListener(): void;
  removeOnSimCardStateChangeListener(): void;

  readonly onAirplaneModeChange: EventEmitter<boolean>;
  readonly onSIMCardStateChange: EventEmitter<Array<Object>>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('SimcardInfo');
