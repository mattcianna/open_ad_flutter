#import "OpenAdFlutterPlugin.h"
#if __has_include(<open_ad_flutter/open_ad_flutter-Swift.h>)
#import <open_ad_flutter/open_ad_flutter-Swift.h>
#else
#import "open_ad_flutter-Swift.h"
#endif

@implementation OpenAdFlutterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftOpenAdFlutterPlugin registerWithRegistrar:registrar];
}
@end
