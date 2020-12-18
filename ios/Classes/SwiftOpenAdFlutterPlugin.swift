import Flutter
import UIKit
import GoogleMobileAds

public class SwiftOpenAdFlutterPlugin: NSObject, FlutterPlugin {
   public static func register(with registrar: FlutterPluginRegistrar) {
      let channel = FlutterMethodChannel(name: "open_ad_flutter", binaryMessenger: registrar.messenger())
      let instance = SwiftOpenAdFlutterPlugin()
      registrar.addMethodCallDelegate(instance, channel: channel)
      // TODO
   }
   
   var adUnitId = "ca-app-pub-3940256099942544/5662855259"
   var shouldPresent = true

   public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
      result("iOS " + UIDevice.current.systemVersion)
      switch call.method {
      case "configure":
         let args = call.arguments as! [String:Any?]
         // adUnitId
         if let id = args["adUnitId"] as? String {
            self.adUnitId = id
         }
         NotificationCenter.default.addObserver(self,
                                                selector: #selector(onEnterForeground),
                                                name: UIApplication.didBecomeActiveNotification,
                                                object: nil)
         
         if let immediately = args["showImmediately"] as? Bool, immediately {
            self.requestAppOpenAd(showImmediately: true)
         } else {
            self.requestAppOpenAd()
         }
         break
      case "pause":
         self.shouldPresent = false
         break
      case "resume":
         self.shouldPresent = true
         break
      default:
         break
      }
   }
   
   
   
   
   /**
      OPEN AD CODE
    */
   
   var appOpenAd: GADAppOpenAd?
   
   @objc func onEnterForeground() {
       tryToPresentAd()
   }
   
   func requestAppOpenAd(showImmediately: Bool = false) {
       if !shouldPresent {
           return
       }
       
       GADMobileAds.sharedInstance().requestConfiguration.testDeviceIdentifiers = [kGADSimulatorID as? String ?? ""]
              
       GADAppOpenAd.load(withAdUnitID: adUnitId,
                         request: GADRequest(),
                         orientation: UIApplication.shared.statusBarOrientation,
                         completionHandler: { (openAd, error) in
                           if let err = error {
                               print(err.localizedDescription)
                               return
                           }
                           self.appOpenAd = openAd
                           if showImmediately {
                               self.tryToPresentAd()
                           }
                         })
       
   }
   
   func tryToPresentAd() {
       if !shouldPresent {
           return
       }
       if let ad = self.appOpenAd, let rc = UIApplication.shared.keyWindow?.rootViewController {
           ad.present(fromRootViewController: rc)
           self.appOpenAd = nil
           self.requestAppOpenAd()
       } else {
           self.requestAppOpenAd(showImmediately: false)
       }
   }
}
