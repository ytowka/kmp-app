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
                Text(String(format: "%.1f", Float(truncating: mark)))
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

struct ContentCardView_Previes: PreviewProvider {
    static var previews: some View {
        let model = shared.ContentModel(id: 100, themeId: 1, name: "Веном", imageUrl: "https://sun1-27.userapi.com/impg/ScrivahxBfrTHs8fPOO7fwwgFLGJZ-yaLeXEUQ/bM5sZfC-XB0.jpg?size=403x604&quality=95&sign=35301c033ba084f3f0111c3938dcf6c4&type=album", avgMark: 5, reviewCount: 0)
        ContentCardView(content: model)
    }
}
//#Preview {
//    let model = shared.ContentModel(id: 100, themeId: 1, name: "Веном", imageUrl: "https://sun1-27.userapi.com/impg/ScrivahxBfrTHs8fPOO7fwwgFLGJZ-yaLeXEUQ/bM5sZfC-XB0.jpg?size=403x604&quality=95&sign=35301c033ba084f3f0111c3938dcf6c4&type=album", avgMark: 5, reviewCount: 0)
//    ContentCardView(content: model)
//}
