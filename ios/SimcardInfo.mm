#import "SimcardInfo.h"
#import "DeviceUtil.h"

@implementation SimcardInfo
RCT_EXPORT_MODULE()

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeSimcardInfoSpecJSI>(params);
}

- (NSNumber *)isDeviceEsimCompatible { 
  return @([DeviceUtil isEsimCompatible]);
}

@end
