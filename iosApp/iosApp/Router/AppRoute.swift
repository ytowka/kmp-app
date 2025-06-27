//
//  AppRoute.swift
//  iosApp
//
//  Created by kerik on 27.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import Foundation
import shared

enum AppRoute: Hashable {
    case contentList(topicId: Int64, topicName: String)
    case reviewList(contentId: Int64, contentName: String)
    case userProfile(user: shared.UserModel)
}
