//
//  ExtFeatureAuthError.swift
//  iosApp
//
//  Created by kerik on 25.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared

extension FeatureError {
    var localizedMessage: String {
        switch self {
        case .field: return "Проверьте поля"
        case .network: return "Нет соединения"
        case .wrongCredentials: return "Неверный логин или пароль"
        case .alreadyRegistered: return "Уже зарегистрирован"
        case .other: return "Ошибка"
        @unknown default: return "Ошибка"
        }
    }
}
