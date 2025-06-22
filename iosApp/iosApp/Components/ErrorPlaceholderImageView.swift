import SwiftUI

struct ErrorPlaceholderImageView: View {
    var body: some View {
        ZStack {
            Color.gray.opacity(0.2)
            Image(systemName: "photo")
                .resizable()
                .scaledToFit()
                .foregroundColor(.gray)
                .padding(24)
        }
    }
}
