import Foundation
import SwiftUI

final class ProfileViewModel: ObservableObject {
    @Published var user: UserModel
    @Published var reviews: [ReviewUIModel] = []

    init(user: UserModel) {
        self.user = user
        loadReviews()
    }

    func loadReviews() {
        // TODO: Заменить на загрузку из shared-модуля
        self.reviews = mockReviews(for: user)
    }

    private func mockReviews(for user: UserModel) -> [ReviewUIModel] {
        return [
            ReviewUIModel(
                id: UUID().uuidString,
                username: user.username,
                userAvatarUrl: user.avatarUrl,
                movieTitle: "Interstellar",
                text: "good",
                rating: 10
            )
        ]
    }
}

