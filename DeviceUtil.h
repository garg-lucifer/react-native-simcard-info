#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, DeviceModel) {
    //Simulator
    DeviceModelSimulator,
    
    //iPod
    DeviceModeliPod1,
    DeviceModeliPod2,
    DeviceModeliPod3,
    DeviceModeliPod4,
    DeviceModeliPod5,
    DeviceModeliPod6,
    DeviceModeliPod7,
    
    //iPad
    DeviceModeliPad2,
    DeviceModeliPad3,
    DeviceModeliPad4,
    DeviceModeliPadAir,
    DeviceModeliPadAir2,
    DeviceModeliPadAir3,
    DeviceModeliPadAir4,
    DeviceModeliPadAir5,
    DeviceModeliPad5, //iPad 2017
    DeviceModeliPad6, //iPad 2018
    DeviceModeliPad7, //iPad 2019
    DeviceModeliPad8, //iPad 2020
    DeviceModeliPad9, //iPad 2021
    DeviceModeliPad10, //iPad 2022
    
    //iPad Mini
    DeviceModeliPadMini,
    DeviceModeliPadMini2,
    DeviceModeliPadMini3,
    DeviceModeliPadMini4,
    DeviceModeliPadMini5,
    DeviceModeliPadMini6,
    
    //iPad Pro
    DeviceModeliPadPro9_7,
    DeviceModeliPadPro10_5,
    DeviceModeliPadPro11,
    DeviceModeliPadPro2_11,
    DeviceModeliPadPro3_11,
    DeviceModeliPadPro12_9,
    DeviceModeliPadPro2_12_9,
    DeviceModeliPadPro3_12_9,
    DeviceModeliPadPro4_12_9,
    DeviceModeliPadPro5_12_9,
    
    //iPhone
    DeviceModeliPhone4,
    DeviceModeliPhone4S,
    DeviceModeliPhone5,
    DeviceModeliPhone5S,
    DeviceModeliPhone5C,
    DeviceModeliPhone6,
    DeviceModeliPhone6Plus,
    DeviceModeliPhone6S,
    DeviceModeliPhone6SPlus,
    DeviceModeliPhoneSE,
    DeviceModeliPhone7,
    DeviceModeliPhone7Plus,
    DeviceModeliPhone8,
    DeviceModeliPhone8Plus,
    DeviceModeliPhoneX,
    DeviceModeliPhoneXS,
    DeviceModeliPhoneXSMax,
    DeviceModeliPhoneXR,
    DeviceModeliPhone11,
    DeviceModeliPhone11Pro,
    DeviceModeliPhone11ProMax,
    DeviceModeliPhoneSE2,
    DeviceModeliPhone12Mini,
    DeviceModeliPhone12,
    DeviceModeliPhone12Pro,
    DeviceModeliPhone12ProMax,
    DeviceModeliPhone13Mini,
    DeviceModeliPhone13,
    DeviceModeliPhone13Pro,
    DeviceModeliPhone13ProMax,
    DeviceModeliPhoneSE3,
    DeviceModeliPhone14,
    DeviceModeliPhone14Plus,
    DeviceModeliPhone14Pro,
    DeviceModeliPhone14ProMax,
    DeviceModeliPhone15,
    DeviceModeliPhone15Plus,
    DeviceModeliPhone15Pro,
    DeviceModeliPhone15ProMax,
    DeviceModeliPhone16,
    DeviceModeliPhone16Plus,
    DeviceModeliPhone16Pro,
    DeviceModeliPhone16ProMax,
    
    // Apple Watch
    DeviceModelAppleWatch1,
    DeviceModelAppleWatchS1,
    DeviceModelAppleWatchS2,
    DeviceModelAppleWatchS3,
    DeviceModelAppleWatchS4,
    DeviceModelAppleWatchS5,
    DeviceModelAppleWatchSE,
    DeviceModelAppleWatchS6,
    DeviceModelAppleWatchS7,
    
    //Apple TV
    DeviceModelAppleTV1,
    DeviceModelAppleTV2,
    DeviceModelAppleTV3,
    DeviceModelAppleTV4,
    DeviceModelAppleTV_4K,
    DeviceModelAppleTV2_4K,
    DeviceModelAppleTV3_4K,
    
    DeviceModelUnrecognized
};

@interface DeviceUtil : NSObject

+ (BOOL)isEsimCompatible;

@end