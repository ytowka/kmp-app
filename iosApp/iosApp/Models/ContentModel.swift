import Foundation

struct ContentModel: Identifiable {
    let id: Int64
    let name: String
    let imageUrl: String?
    let avgMark: Float?
    let reviewCount: Int?
}
