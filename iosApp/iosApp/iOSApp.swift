import SwiftUI

import SwiftUI
import FirebaseCore
import shared


class AppDelegate: NSObject, UIApplicationDelegate {
  func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
      FirebaseApp.configure()
      AnalyticHolder.shared.analytics = AnalyticsImpl()
      CommonDi.shared.doInitDi(appDeclaration: { _ in
          
      })
    

    return true
  }
}

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
	var body: some Scene {
		WindowGroup {
			//RootScreen()
            RootView()
		}
	}
}
