//
//  AppRouter.swift
//  iosApp
//
//  Created by kerik on 27.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

final class AppRouter: ObservableObject {
    @Published var path = NavigationPath()
    @Published var refreshProfileView: Bool = false

    func push(_ route: AppRoute) {
        path.append(route)
    }

    func pop() {
        path.removeLast()
    }

    func popToRoot() {
        path.removeLast(path.count)
    }

    func replace(_ route: AppRoute) {
        if !path.isEmpty {
            path.removeLast()
        }
        path.append(route)
    }

    func triggerProfileRefresh() {
        refreshProfileView.toggle()
    }
}

