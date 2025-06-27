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

    func push(_ route: AppRoute) {
        path.append(route)
    }

    func pop() {
        path.removeLast()
    }

    func popToRoot() {
        path.removeLast(path.count)
    }
}

