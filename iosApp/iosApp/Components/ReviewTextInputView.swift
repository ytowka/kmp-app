//
//  Untitled.swift
//  iosApp
//
//  Created by kerik on 28.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct ReviewTextInputView: View {
    @Binding var text: String

    var body: some View {
        TextEditor(text: $text)
            .frame(height: 100)
            .padding(8)
            .background(Color(UIColor.systemGray6))
            .cornerRadius(12)
            .overlay(RoundedRectangle(cornerRadius: 12).stroke(Color.gray.opacity(0.3)))
    }
}
