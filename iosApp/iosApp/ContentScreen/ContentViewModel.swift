import Foundation

final class ContentViewModel: ObservableObject {
    @Published var contents: [ContentModel] = []

    func loadContents(for themeId: Int64) {
        // TODO: загрузка из KMP
        self.contents = mockContentList(for: themeId)
    }

    private func mockContentList(for themeId: Int64) -> [ContentModel] {
        return [
            ContentModel(
                id: 1,
                name: "Interstellar",
                imageUrl: "https://upload.wikimedia.org/wikipedia/en/b/bc/Interstellar_film_poster.jpg",
                avgMark: 10.0,
                reviewCount: 5
            ),
            ContentModel(
                id: 2,
                name: "Inception",
                imageUrl: "https://upload.wikimedia.org/wikipedia/en/7/7f/Inception_ver3.jpg",
                avgMark: 9.2,
                reviewCount: 5
            ),
            ContentModel(
                id: 3,
                name: "The Dark Knight",
                imageUrl: "fafefqsff",
                avgMark: 9.0,
                reviewCount: 0
            ),
            ContentModel(
                id: 4,
                name: "Tenet",
                imageUrl: "https://upload.wikimedia.org/wikipedia/en/1/14/Tenet_movie_poster.jpg",
                avgMark: 8.1,
                reviewCount: 0
            )
        ]
    }

}
