import SwiftUI

struct AvatarImageView: View {
    let avatarUrl: String?

    var body: some View {
        if let urlString = avatarUrl, let url = URL(string: urlString) {
            CachedAsyncImage(
                url: url,
                placeholder: { AnyView(placeholderView) },
                errorPlaceholder: {
                    ErrorPlaceholderImageView()
                },
                contentMode: .fit
            )
        } else {
            placeholderView
        }
    }

    var placeholderView: some View {
        Image(systemName: "person.circle.fill")
            .resizable()
            .scaledToFit()
            .foregroundColor(.black)
    }
}
