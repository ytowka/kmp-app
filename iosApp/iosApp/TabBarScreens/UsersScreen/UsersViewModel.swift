import Foundation

final class UsersViewModel: ObservableObject {
    @Published var allUsers: [UserModel] = []
    @Published var searchText: String = ""

    var filteredUsers: [UserModel] {
        if searchText.isEmpty {
            return allUsers
        } else {
            return allUsers.filter {
                $0.username.localizedCaseInsensitiveContains(searchText)
            }
        }
    }

    init() {
        loadUsers()
    }

    func loadUsers() {
        // Здесь будет вызов KMP API
        allUsers = mockUsers()
    }

    private func mockUsers() -> [UserModel] {
        [
            UserModel(id: "1", username: "Test", fullName: nil, avatarUrl: nil),
            UserModel(id: "2", username: "kerikg", fullName: "Kirill Abramov", avatarUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVzdxX6cZ5CrZJl1rE6FvzVT5_GFb11AZ9Cg&s"),
            UserModel(id: "3", username: "danilk", fullName: nil, avatarUrl: nil)
        ]
    }
}
