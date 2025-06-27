//
//  MarkColors.swift
//  iosApp
//
//  Created by kerik on 27.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

import SwiftUI

enum MarkColors {
    static let highestColor = Color(hex: "#f5d742")
    static let highColor = Color(hex: "#26c937")
    static let midColor = Color(hex: "#878787")
    static let poorColor = Color(hex: "#8c0000")          

    static func getColor(for mark: Float) -> Color {
        switch mark {
        case let m where m > 9.0:
            return highestColor
        case let m where m > 7.0:
            return highColor
        case let m where m > 5.0:
            return midColor
        default:
            return poorColor
        }
    }
}
