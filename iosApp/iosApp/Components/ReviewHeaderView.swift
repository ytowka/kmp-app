//
//  ContentPreview.swift
//  iosApp
//
//  Created by kerik on 27.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ReviewHeaderView: View {
    let content: shared.ContentModel

    var body: some View {
        ZStack(alignment: .bottomLeading) {
            CachedAsyncImage(
                url: URL(string: content.imageUrl ?? ""),
                placeholder: { AnyView(Color.gray.opacity(0.3)) },
                errorPlaceholder: { ErrorPlaceholderImageView() },
                contentMode: .fill
            )
            .frame(height: 200)
            .clipped()

            Text(content.name)
                .font(.headline)
                .foregroundColor(.white)
                .padding()
                .shadow(radius: 3)
        }
        .cornerRadius(12)
        .shadow(radius: 5)
    }
}
