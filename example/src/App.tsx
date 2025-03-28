import { useEffect, useState } from 'react';
import { ScrollView, StyleSheet, Text, View } from 'react-native';
import {
  useAirplaneMode,
  useSIMStateChange,
  getSIMIds,
  getActiveDataSIMId,
  getActiveSIMCount,
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
  isActiveSIMId,
  isNetworkRoaming,
  isESIM,
  isMobileDataEnabled,
} from 'react-native-simcard-info';

const InfoCard = ({ title, content }: { title: string; content: string }) => (
  <View style={styles.card}>
    <Text style={styles.title}>{title}</Text>
    <Text style={styles.content}>{content}</Text>
  </View>
);

export default function App() {
  const { isAirplaneModeOn } = useAirplaneMode();
  const { simState } = useSIMStateChange();
  const [dataSimId, setDataSimId] = useState(0);

  useEffect(() => {
    setDataSimId(getActiveDataSIMId());
  }, []);

  return (
    <ScrollView style={styles.container}>
      <Text style={styles.header}>SIM Card Info</Text>
      <InfoCard
        title="Airplane Mode"
        content={isAirplaneModeOn ? 'On' : 'Off'}
      />
      <InfoCard
        title="Active Data SIM ID"
        content={String(dataSimId) || 'N/A'}
      />
      <InfoCard
        title="SIM Slots Available"
        content={String(getNoOfSIMSlotAvailable())}
      />
      <InfoCard
        title="Mobile Data Enabled"
        content={isMobileDataEnabled(1) ? 'Yes' : 'No'}
      />
      <InfoCard title="SIM State" content={JSON.stringify(simState)} />
      <InfoCard title="SIM IDs" content={JSON.stringify(getSIMIds())} />
      <InfoCard
        title="Default Data SIM ID"
        content={String(getDefaultDataSIMId())}
      />
      <InfoCard
        title="Default SMS SIM ID"
        content={String(getDefaultSMSSIMId())}
      />
      <InfoCard title="Default SIM ID" content={String(getDefaultSIMId())} />
      <InfoCard
        title="All Phone Numbers"
        content={JSON.stringify(getAllPhoneNumbers())}
      />
      <InfoCard
        title="Complete Active SIM Info List"
        content={JSON.stringify(getCompleteActiveSIMInfoList())}
      />
      <InfoCard
        title="Active SIM Count"
        content={String(getActiveSIMCount())}
      />
      <InfoCard
        title="Active SIM Info List"
        content={JSON.stringify(getActiveSIMInfoList())}
      />
      <InfoCard
        title="All SIM Info List"
        content={JSON.stringify(getAllSIMInfoList())}
      />
      <InfoCard
        title="Signal Strength (SIM ID 1)"
        content={String(getSignalStrength(1))}
      />
      <InfoCard title="Phone Number (SIM ID 1)" content={getPhoneNumber(1)} />
      <InfoCard
        title="Is Network Roaming (SIM ID 1)"
        content={isNetworkRoaming(1) ? 'Yes' : 'No'}
      />
      {simState.length && (
        <InfoCard
          title="Is eSIM (SIM ID 1)"
          content={isESIM(1) ? 'Yes' : 'No'}
        />
      )}
      <InfoCard
        title="Slot Index (SIM ID 1)"
        content={String(getSlotIndex(1))}
      />
      <InfoCard
        title="Is SIM ID 1 Active"
        content={isActiveSIMId(1) ? 'Yes' : 'No'}
      />
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#f5f5f5',
  },
  header: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  card: {
    marginBottom: 15,
    padding: 15,
    backgroundColor: 'white',
    borderRadius: 8,
    elevation: 3,
  },
  title: {
    fontSize: 18,
    fontWeight: '600',
    marginBottom: 5,
  },
  content: {
    fontSize: 16,
    color: '#333',
  },
  switchContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
});
