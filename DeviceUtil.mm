#import "DeviceUtil.h"
#import <sys/utsname.h>

@implementation DeviceUtil

+ (DeviceModel)currentDeviceModel {
    struct utsname systemInfo;
    uname(&systemInfo);
    NSString *modelCode = [NSString stringWithCString:systemInfo.machine encoding:NSUTF8StringEncoding];

    static NSDictionary<NSString *, NSNumber *> *modelMap = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        modelMap = @{
            // Simulator
            @"i386": @(DeviceModelSimulator),
            @"x86_64": @(DeviceModelSimulator),
            @"arm64": @(DeviceModelSimulator),

            // iPod
            @"iPod1,1": @(DeviceModeliPod1),
            @"iPod2,1": @(DeviceModeliPod2),
            @"iPod3,1": @(DeviceModeliPod3),
            @"iPod4,1": @(DeviceModeliPod4),
            @"iPod5,1": @(DeviceModeliPod5),
            @"iPod7,1": @(DeviceModeliPod6),
            @"iPod9,1": @(DeviceModeliPod7),

            // iPad
            @"iPad2,1": @(DeviceModeliPad2),
            @"iPad2,2": @(DeviceModeliPad2),
            @"iPad2,3": @(DeviceModeliPad2),
            @"iPad2,4": @(DeviceModeliPad2),
            @"iPad3,1": @(DeviceModeliPad3),
            @"iPad3,2": @(DeviceModeliPad3),
            @"iPad3,3": @(DeviceModeliPad3),
            @"iPad3,4": @(DeviceModeliPad4),
            @"iPad3,5": @(DeviceModeliPad4),
            @"iPad3,6": @(DeviceModeliPad4),
            @"iPad6,11": @(DeviceModeliPad5), // iPad 2017
            @"iPad6,12": @(DeviceModeliPad5),
            @"iPad7,5": @(DeviceModeliPad6), // iPad 2018
            @"iPad7,6": @(DeviceModeliPad6),
            @"iPad7,11": @(DeviceModeliPad7), // iPad 2019
            @"iPad7,12": @(DeviceModeliPad7),
            @"iPad11,6": @(DeviceModeliPad8), // iPad 2020
            @"iPad11,7": @(DeviceModeliPad8),
            @"iPad12,1": @(DeviceModeliPad9), // iPad 2021
            @"iPad12,2": @(DeviceModeliPad9),
            @"iPad13,18": @(DeviceModeliPad10),
            @"iPad13,19": @(DeviceModeliPad10),

            // iPad Mini
            @"iPad2,5": @(DeviceModeliPadMini),
            @"iPad2,6": @(DeviceModeliPadMini),
            @"iPad2,7": @(DeviceModeliPadMini),
            @"iPad4,4": @(DeviceModeliPadMini2),
            @"iPad4,5": @(DeviceModeliPadMini2),
            @"iPad4,6": @(DeviceModeliPadMini2),
            @"iPad4,7": @(DeviceModeliPadMini3),
            @"iPad4,8": @(DeviceModeliPadMini3),
            @"iPad4,9": @(DeviceModeliPadMini3),
            @"iPad5,1": @(DeviceModeliPadMini4),
            @"iPad5,2": @(DeviceModeliPadMini4),
            @"iPad11,1": @(DeviceModeliPadMini5),
            @"iPad11,2": @(DeviceModeliPadMini5),
            @"iPad14,1": @(DeviceModeliPadMini6),
            @"iPad14,2": @(DeviceModeliPadMini6),

            // iPad Pro
            @"iPad6,3": @(DeviceModeliPadPro9_7),
            @"iPad6,4": @(DeviceModeliPadPro9_7),
            @"iPad7,3": @(DeviceModeliPadPro10_5),
            @"iPad7,4": @(DeviceModeliPadPro10_5),
            @"iPad6,7": @(DeviceModeliPadPro12_9),
            @"iPad6,8": @(DeviceModeliPadPro12_9),
            @"iPad7,1": @(DeviceModeliPadPro2_12_9),
            @"iPad7,2": @(DeviceModeliPadPro2_12_9),
            @"iPad8,1": @(DeviceModeliPadPro11),
            @"iPad8,2": @(DeviceModeliPadPro11),
            @"iPad8,3": @(DeviceModeliPadPro11),
            @"iPad8,4": @(DeviceModeliPadPro11),
            @"iPad8,9": @(DeviceModeliPadPro2_11),
            @"iPad8,10": @(DeviceModeliPadPro2_11),
            @"iPad13,4": @(DeviceModeliPadPro3_11),
            @"iPad13,5": @(DeviceModeliPadPro3_11),
            @"iPad13,6": @(DeviceModeliPadPro3_11),
            @"iPad13,7": @(DeviceModeliPadPro3_11),
            @"iPad8,5": @(DeviceModeliPadPro3_12_9),
            @"iPad8,6": @(DeviceModeliPadPro3_12_9),
            @"iPad8,7": @(DeviceModeliPadPro3_12_9),
            @"iPad8,8": @(DeviceModeliPadPro3_12_9),
            @"iPad8,11": @(DeviceModeliPadPro4_12_9),
            @"iPad8,12": @(DeviceModeliPadPro4_12_9),
            @"iPad13,8": @(DeviceModeliPadPro5_12_9),
            @"iPad13,9": @(DeviceModeliPadPro5_12_9),
            @"iPad13,10": @(DeviceModeliPadPro5_12_9),
            @"iPad13,11": @(DeviceModeliPadPro5_12_9),

            // iPad Air
            @"iPad4,1": @(DeviceModeliPadAir),
            @"iPad4,2": @(DeviceModeliPadAir),
            @"iPad4,3": @(DeviceModeliPadAir),
            @"iPad5,3": @(DeviceModeliPadAir2),
            @"iPad5,4": @(DeviceModeliPadAir2),
            @"iPad11,3": @(DeviceModeliPadAir3),
            @"iPad11,4": @(DeviceModeliPadAir3),
            @"iPad13,1": @(DeviceModeliPadAir4),
            @"iPad13,2": @(DeviceModeliPadAir4),
            @"iPad13,16": @(DeviceModeliPadAir5),
            @"iPad13,17": @(DeviceModeliPadAir5),

            // iPhone
            @"iPhone3,1": @(DeviceModeliPhone4),
            @"iPhone3,2": @(DeviceModeliPhone4),
            @"iPhone3,3": @(DeviceModeliPhone4),
            @"iPhone4,1": @(DeviceModeliPhone4S),
            @"iPhone5,1": @(DeviceModeliPhone5),
            @"iPhone5,2": @(DeviceModeliPhone5),
            @"iPhone5,3": @(DeviceModeliPhone5C),
            @"iPhone5,4": @(DeviceModeliPhone5C),
            @"iPhone6,1": @(DeviceModeliPhone5S),
            @"iPhone6,2": @(DeviceModeliPhone5S),
            @"iPhone7,1": @(DeviceModeliPhone6Plus),
            @"iPhone7,2": @(DeviceModeliPhone6),
            @"iPhone8,1": @(DeviceModeliPhone6S),
            @"iPhone8,2": @(DeviceModeliPhone6SPlus),
            @"iPhone8,4": @(DeviceModeliPhoneSE),
            @"iPhone9,1": @(DeviceModeliPhone7),
            @"iPhone9,3": @(DeviceModeliPhone7),
            @"iPhone9,2": @(DeviceModeliPhone7Plus),
            @"iPhone9,4": @(DeviceModeliPhone7Plus),
            @"iPhone10,1": @(DeviceModeliPhone8),
            @"iPhone10,4": @(DeviceModeliPhone8),
            @"iPhone10,2": @(DeviceModeliPhone8Plus),
            @"iPhone10,5": @(DeviceModeliPhone8Plus),
            @"iPhone10,3": @(DeviceModeliPhoneX),
            @"iPhone10,6": @(DeviceModeliPhoneX),
            @"iPhone11,2": @(DeviceModeliPhoneXS),
            @"iPhone11,4": @(DeviceModeliPhoneXSMax),
            @"iPhone11,6": @(DeviceModeliPhoneXSMax),
            @"iPhone11,8": @(DeviceModeliPhoneXR),
            @"iPhone12,1": @(DeviceModeliPhone11),
            @"iPhone12,3": @(DeviceModeliPhone11Pro),
            @"iPhone12,5": @(DeviceModeliPhone11ProMax),
            @"iPhone12,8": @(DeviceModeliPhoneSE2),
            @"iPhone13,1": @(DeviceModeliPhone12Mini),
            @"iPhone13,2": @(DeviceModeliPhone12),
            @"iPhone13,3": @(DeviceModeliPhone12Pro),
            @"iPhone13,4": @(DeviceModeliPhone12ProMax),
            @"iPhone14,4": @(DeviceModeliPhone13Mini),
            @"iPhone14,5": @(DeviceModeliPhone13),
            @"iPhone14,2": @(DeviceModeliPhone13Pro),
            @"iPhone14,3": @(DeviceModeliPhone13ProMax),
            @"iPhone14,6": @(DeviceModeliPhoneSE3),
            @"iPhone14,7": @(DeviceModeliPhone14),
            @"iPhone14,8": @(DeviceModeliPhone14Plus),
            @"iPhone15,2": @(DeviceModeliPhone14Pro),
            @"iPhone15,3": @(DeviceModeliPhone14ProMax),
            @"iPhone15,4": @(DeviceModeliPhone15),
            @"iPhone15,5": @(DeviceModeliPhone15Plus),
            @"iPhone16,1": @(DeviceModeliPhone15Pro),
            @"iPhone16,2": @(DeviceModeliPhone15ProMax),
            @"iPhone17,3": @(DeviceModeliPhone16),
            @"iPhone17,4": @(DeviceModeliPhone16Plus),
            @"iPhone17,1": @(DeviceModeliPhone16Pro),
            @"iPhone17,2": @(DeviceModeliPhone16ProMax),

            // Apple Watch
            @"Watch1,1": @(DeviceModelAppleWatch1),
            @"Watch1,2": @(DeviceModelAppleWatch1),
            @"Watch2,6": @(DeviceModelAppleWatchS1),
            @"Watch2,7": @(DeviceModelAppleWatchS1),
            @"Watch2,3": @(DeviceModelAppleWatchS2),
            @"Watch2,4": @(DeviceModelAppleWatchS2),
            @"Watch3,1": @(DeviceModelAppleWatchS3),
            @"Watch3,2": @(DeviceModelAppleWatchS3),
            @"Watch3,3": @(DeviceModelAppleWatchS3),
            @"Watch3,4": @(DeviceModelAppleWatchS3),
            @"Watch4,1": @(DeviceModelAppleWatchS4),
            @"Watch4,2": @(DeviceModelAppleWatchS4),
            @"Watch4,3": @(DeviceModelAppleWatchS4),
            @"Watch4,4": @(DeviceModelAppleWatchS4),
            @"Watch5,1": @(DeviceModelAppleWatchS5),
            @"Watch5,2": @(DeviceModelAppleWatchS5),
            @"Watch5,3": @(DeviceModelAppleWatchS5),
            @"Watch5,4": @(DeviceModelAppleWatchS5),
            @"Watch5,9": @(DeviceModelAppleWatchSE),
            @"Watch5,10": @(DeviceModelAppleWatchSE),
            @"Watch5,11": @(DeviceModelAppleWatchSE),
            @"Watch5,12": @(DeviceModelAppleWatchSE),
            @"Watch6,1": @(DeviceModelAppleWatchS6),
            @"Watch6,2": @(DeviceModelAppleWatchS6),
            @"Watch6,3": @(DeviceModelAppleWatchS6),
            @"Watch6,4": @(DeviceModelAppleWatchS6),
            @"Watch6,6": @(DeviceModelAppleWatchS7),
            @"Watch6,7": @(DeviceModelAppleWatchS7),
            @"Watch6,8": @(DeviceModelAppleWatchS7),
            @"Watch6,9": @(DeviceModelAppleWatchS7),

            // Apple TV
            @"AppleTV1,1": @(DeviceModelAppleTV1),
            @"AppleTV2,1": @(DeviceModelAppleTV2),
            @"AppleTV3,1": @(DeviceModelAppleTV3),
            @"AppleTV3,2": @(DeviceModelAppleTV3),
            @"AppleTV5,3": @(DeviceModelAppleTV4),
            @"AppleTV6,2": @(DeviceModelAppleTV_4K),
            @"AppleTV11,1": @(DeviceModelAppleTV2_4K),
            @"AppleTV14,1": @(DeviceModelAppleTV3_4K)
        };
    });

    NSNumber *modelNumber = modelMap[modelCode];
    if (modelNumber) {
        DeviceModel model = (DeviceModel)[modelNumber integerValue];
        if (model == DeviceModelSimulator) {
            NSString *simModelCode = [NSProcessInfo processInfo].environment[@"SIMULATOR_MODEL_IDENTIFIER"];
            if (simModelCode) {
                NSNumber *simModelNumber = modelMap[simModelCode];
                if (simModelNumber) {
                    return (DeviceModel)[simModelNumber integerValue];
                }
            }
        }
        return model;
    }

    return DeviceModelUnrecognized;
}

+ (BOOL)isEsimCompatible {
    DeviceModel model = [self currentDeviceModel];

    switch (model) {
        case DeviceModeliPhoneXR:
        case DeviceModeliPhoneXS:
        case DeviceModeliPhoneXSMax:
        case DeviceModeliPhone11:
        case DeviceModeliPhone11Pro:
        case DeviceModeliPhone11ProMax:
        case DeviceModeliPhone12:
        case DeviceModeliPhone12Pro:
        case DeviceModeliPhone12ProMax:
        case DeviceModeliPhone12Mini:
        case DeviceModeliPhone13:
        case DeviceModeliPhone13Pro:
        case DeviceModeliPhone13ProMax:
        case DeviceModeliPhone13Mini:
        case DeviceModeliPhone14:
        case DeviceModeliPhone14Pro:
        case DeviceModeliPhone14ProMax:
        case DeviceModeliPhone14Plus:
        case DeviceModeliPhone15:
        case DeviceModeliPhone15Pro:
        case DeviceModeliPhone15ProMax:
        case DeviceModeliPhone15Plus:
        case DeviceModeliPhone16:
        case DeviceModeliPhone16Pro:
        case DeviceModeliPhone16ProMax:
        case DeviceModeliPhone16Plus:
        case DeviceModeliPhoneSE2:
        case DeviceModeliPhoneSE3:
            return YES;
        default:
            return NO;
    }
}

@end
