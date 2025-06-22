import Foundation

struct ThemeModel: Identifiable {
    let id: Int64
    let name: String
    let contentCount: Int
    let imageUrl: String
    let contents: [ContentModel]
}

