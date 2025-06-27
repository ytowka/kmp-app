import Foundation

final class ThemesViewModel: ObservableObject {
    @Published var themes: [ThemeModel] = []

    init() {
        loadThemes()
    }

    func loadThemes() {
        // TODO: Здесь можно вызывать shared Kotlin API
        themes = [
            ThemeModel(
                id: 1,
                name: "Фильмы",
                contentCount: 9,
                imageUrl: "https://avatars.mds.yandex.net/get-kinopoisk-image/4483445/e4f4c471-890f-4e03-8823-1adb919eaf35/384x384",
                contents: mockContents()
            ),

            ThemeModel(
                id: 2,
                name: "Книги",
                contentCount: 2,
                imageUrl: "https://avatars.mds.yandex.net/get-kinopoisk-image/4483445/e4f4c471-8кпупкпe03-8823-1adb919eaf35/384x384",
                contents: mockContents()
            ),

        ]
    }

    private func mockContents() -> [ContentModel] {
        (1...9).map {
            ContentModel(
                id: Int64($0),
                name: "Фильм \($0)",
                imageUrl: "https://placehold.co/100x140?text=\($0)",
                avgMark: nil,
                reviewCount: 0
            )
        }
    }
}
