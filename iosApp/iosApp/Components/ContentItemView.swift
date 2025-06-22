import SwiftUI

struct ContentItemView: View {
    let content: ContentModel

    var body: some View {
        VStack(spacing: 4) {
            AsyncImage(url: URL(string: content.imageUrl ?? "")) { phase in
                if let image = phase.image {
                    image
                        .resizable()
                        .aspectRatio(0.7, contentMode: .fill)
                } else {
                    Color.gray
                }
            }
            .frame(width: 100, height: 140)
            .cornerRadius(8)

            Text(content.name)
                .font(.caption)
                .frame(width: 100)
                .lineLimit(2)
        }
    }
}

#Preview {
    let content = ContentModel(id: 1, name: "Test", imageUrl: nil, avgMark: nil, reviewCount: 3)
    ContentItemView(content: content)
}
