import SwiftUI
import shared

struct TopicCardView: View {
    let topic: TopicModel

    var body: some View {
        VStack(alignment: .center, spacing: 12) {
            if let url = URL(string: topic.imageUrl) {
                CachedAsyncImage(
                    url: url,
                    placeholder: {
                        AnyView(Color.gray.opacity(0.3))
                    },
                    errorPlaceholder: {
                        ErrorPlaceholderImageView()
                    },
                    contentMode: .fit
                )
                .aspectRatio(1.8, contentMode: .fit)
            }

            HStack {
                Text(topic.name)
                    .font(.subheadline)
                    .foregroundColor(.primary)
                Spacer()
                Text("\(topic.contentCount)")
                    .font(.subheadline)
                    .foregroundColor(.gray)
            }
        }
        .padding()
        .background(Color(UIColor.systemGray6))
        .cornerRadius(16)
        .padding(.horizontal)
    }
}
