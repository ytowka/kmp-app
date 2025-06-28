//
//  MarkPicker.swift
//  iosApp
//
//  Created by kerik on 27.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct StarRatingView: View {
    @Binding var rating: Int

    var body: some View {
        HStack {
            ForEach(1...10, id: \.self) { index in
                Image(systemName: index <= rating ? "star.fill" : "star")
                    .foregroundColor(index <= rating ? MarkColors.getColor(for: Float(rating)) : .gray.opacity(0.3))
                    .onTapGesture {
                        withAnimation {
                            rating = index
                        }
                    }
            }
        }
    }
}

