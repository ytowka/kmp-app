import SwiftUI
import shared

struct ContentCardView: View {
    let content: shared.ContentModel

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
                        contentMode: .fit
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
                let markValue = Float(truncating: mark)
                Text(String(format: "%.1f", Float(truncating: mark)))
                    .font(.caption)
                    .bold()
                    .foregroundColor(.white)
                    .frame(width: 40, height: 40)
                    .background(MarkColors.getColor(for: markValue))
                    .clipShape(Circle())
                    .multilineTextAlignment(.center)
                    .padding(8)
            }
        }
        .background(Color.black)
        .cornerRadius(12)
        .shadow(radius: 4)
    }
}
