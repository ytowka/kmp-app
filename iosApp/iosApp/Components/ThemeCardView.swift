import SwiftUI

struct ThemeCardView: View {
    let theme: ThemeModel

    var body: some View {
        VStack(alignment: .center, spacing: 12) {

            //            AsyncImage(url: URL(string: theme.imageUrl)) { phase in
            //                switch phase {
            //                case .empty:
            //                    Color.gray.opacity(0.3)
            //                case .success(let image):
            //                    image
            //                        .resizable()
            //                        .scaledToFit()
            //                        .cornerRadius(8)
            //                case .failure:
            //                    Color.red.opacity(0.3)
            //                @unknown default:
            //                    EmptyView()
            //                }
            //            }
            //            .frame(maxWidth: .infinity)
            //            .aspectRatio(1.8, contentMode: .fit)

            //            Group {
            //                if let url = URL(string: theme.imageUrl) {
            //                    CachedAsyncImage(
            //                        url: url,
            //                        placeholder: {
            //                            AnyView(Color.gray.opacity(0.3))
            //                        },
            //                        contentMode: .fit
            //                    )
            //                    .aspectRatio(1.8, contentMode: .fit)
            //                    .cornerRadius(8)
            //                } else {
            //                    Color.gray.opacity(0.2)
            //                        .aspectRatio(1.8, contentMode: .fit)
            //                        .cornerRadius(8)
            //                }
            //            }

            if let url = URL(string: theme.imageUrl) {
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

            // Название и количество контента
            HStack {
                Text(theme.name)
                    .font(.subheadline)
                    .foregroundColor(.primary)
                Spacer()
                Text("\(theme.contentCount)")
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

#Preview {
    let content = ContentModel(id: 1, name: "Test", imageUrl: nil, avgMark: nil, reviewCount: 3)
    let theme = ThemeModel(id: 1, name: "Фильмы", contentCount: 1, imageUrl: "https://avatars.mds.yandex.net/get-kinopoisk-image/4483445/e4f4c471-890f-4e03-8823-1adb919eaf35/384x384", contents: [content])
    ThemeCardView(theme: theme)
}
