import React from 'react';
import NativeSimcardInfo from '../NativeSimcardInfo';

const isEqualObjectArray = (
  prevState: Array<Object>,
  newState: Array<Object>
): boolean => {
  if (prevState.length !== newState.length) return false;

  for (let i = 0; i < prevState.length; i++) {
    const prevObj = prevState[i];
    const newObj = newState[i];

    if (!prevObj || !newObj) return false;

    const prevObjEntries = Object.entries(prevObj).sort();
    const newObjEntries = Object.entries(newObj).sort();

    if (JSON.stringify(prevObjEntries) !== JSON.stringify(newObjEntries))
      return false;
  }

  return true;
};

export const useSIMStateChange = () => {
  const [simState, setSimState] = React.useState<Array<Object>>([]);
  const simStateRef = React.useRef<Array<Object>>([]);

  React.useEffect(() => {
    NativeSimcardInfo.addOnSimCardStateChangeListener();

    const handleStateChange = (newSimState: Array<Object>) => {
      // Compare with the reference value instead of triggering re-renders
      if (!isEqualObjectArray(simStateRef.current, newSimState)) {
        simStateRef.current = newSimState; // Update the reference
        setSimState(newSimState); // Trigger a re-render only if needed
      }
    };

    NativeSimcardInfo.onSIMCardStateChange(handleStateChange);

    return () => {
      NativeSimcardInfo.removeOnSimCardStateChangeListener();
    };
  }, []);

  return { simState };
};
