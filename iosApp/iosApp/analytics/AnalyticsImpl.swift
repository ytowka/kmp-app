//
//  AnalyticsImpl.swift
//  iosApp
//
//  Created by Alfa on 25.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import shared
import FirebaseAnalytics

class AnalyticsImpl : BaseAnalytics {
    
    override init() {
        super.init()
    }
    
    override func sendEventToServer(event: String, params: [String : Any]) {
        Analytics.logEvent(event, parameters: params)
    }
}
