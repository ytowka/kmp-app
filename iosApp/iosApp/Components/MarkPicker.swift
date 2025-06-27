//
//  MarkPicker.swift
//  iosApp
//
//  Created by kerik on 27.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct MarkPicker: View {
    let mark: Int
    let onMarkChanged: (Int) -> Void

    var body: some View {
        HStack {
            ForEach(1...10, id: \.self) { i in
                Button(action: { onMarkChanged(i) }) {
                    Image(systemName: "star.fill")
                        .foregroundColor(i <= mark ? MarkColors.getColor(for: Float(mark)) : .gray.opacity(0.3))
                }
                .frame(width: 30, height: 30)
            }
        }
    }
}

