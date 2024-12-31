import React from 'react';
import NativeSimcardInfo from '../NativeSimcardInfo';
export const useAirplaneMode = () => {
  const [isAirplaneModeOn, setIsAirplaneModeOn] =
    React.useState<boolean>(false);
  React.useEffect(() => {
    NativeSimcardInfo.addOnAirplaneChangeListener();
    NativeSimcardInfo.onAirplaneModeChange((isAirplaneMode) => {
      setIsAirplaneModeOn(isAirplaneMode);
    });
    return () => NativeSimcardInfo.removeOnAirplaneChangeListener();
  }, []);

  return { isAirplaneModeOn };
};
