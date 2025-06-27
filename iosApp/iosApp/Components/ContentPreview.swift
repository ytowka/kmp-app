//
//  ContentPreview.swift
//  iosApp
//
//  Created by kerik on 27.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ContentPreview: View {
    let content: shared.ContentModel

    var body: some View {
        VStack(alignment: .leading) {
            if let url = URL(string: content.imageUrl ?? "") {
                CachedAsyncImage(
                    url: url,
                    placeholder: { AnyView(Color.gray.opacity(0.1)) },
                    errorPlaceholder: { ErrorPlaceholderImageView() },
                    contentMode: .fill
                )
                .frame(height: 150)
                .cornerRadius(12)
            }

            Text(content.name)
                .font(.headline)
        }
    }
}

