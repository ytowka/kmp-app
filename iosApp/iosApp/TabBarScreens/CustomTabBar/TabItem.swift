import Foundation

enum TabItem: String, CaseIterable {
    case topics
    case users
    case profile

    var label: String {
        switch self {
        case .topics: return "Главная"
        case .users: return "Пользователи"
        case .profile: return "Профиль"
        }
    }

    var systemImage: String {
        switch self {
        case .topics: return "list.bullet"
        case .users: return "person.3.fill"
        case .profile: return "person.crop.circle"
        }
    }
}
