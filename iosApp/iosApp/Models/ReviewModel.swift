import Foundation

struct ReviewUIModel: Identifiable {
    let id: String
    let username: String
    let userAvatarUrl: String?
    let movieTitle: String
    let text: String
    let rating: Double
}
