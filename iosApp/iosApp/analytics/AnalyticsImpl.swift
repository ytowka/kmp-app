//
//  AnalyticsImpl.swift
//  iosApp
//
//  Created by Alfa on 25.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import shared
import FirebaseAnalytics

class AnalyticsImpl : IAnalytics {
    
    func sendEvent(location: String, event: String, params: [String : Any]) {
        var allParams = params
        allParams["location"] = location

        Analytics.logEvent(event, parameters: allParams)
    }
}
