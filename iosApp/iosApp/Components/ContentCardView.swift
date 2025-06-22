import SwiftUI

struct ContentCardView: View {
    let content: ContentModel

    var body: some View {
        ZStack(alignment: .topTrailing) {
            VStack(alignment: .leading, spacing: 8) {
                if let urlString = content.imageUrl, let url = URL(string: urlString) {
                    CachedAsyncImage(
                        url: url,
                        placeholder: { AnyView(Color.gray.opacity(0.2)) },
                        errorPlaceholder: {
                            ErrorPlaceholderImageView()
                        },
                        contentMode: .fill
                    )
                    .cornerRadius(12)
                }

                Text(content.name)
                    .font(.headline)
                    .foregroundColor(.white)
                    .padding([.leading, .bottom], 8)
                    .shadow(radius: 4)
            }

            if let mark = content.avgMark {
                Text(String(format: "%.1f", mark))
                    .font(.caption)
                    .foregroundColor(.black)
                    .padding(6)
                    .background(Color.yellow)
                    .clipShape(Circle())
                    .padding(8)
            }
        }
        .background(Color.black)
        .cornerRadius(12)
        .shadow(radius: 4)
    }
}

