import Foundation
import UIKit

class RegisterViewModel: ObservableObject {
    @Published var username: String = ""
    @Published var email: String = ""
    @Published var phone: String = ""
    @Published var fullName: String = ""
    @Published var password: String = ""
    @Published var confirmPassword: String = ""
    @Published var showError: Bool = false
    @Published var avatar: UIImage?

    var hasValidationError: Bool {
        username.isEmpty ||
        email.isEmpty ||
        phone.isEmpty ||
        fullName.isEmpty ||
        password.isEmpty ||
        confirmPassword.isEmpty ||
        password != confirmPassword
    }

    func fieldHasError(_ field: String) -> Bool {
        guard showError else { return false }

        switch field {
        case "username": return username.isEmpty
        case "email": return email.isEmpty
        case "phone": return phone.isEmpty
        case "name": return fullName.isEmpty
        case "password": return password.isEmpty
        case "confirm": return confirmPassword.isEmpty || password != confirmPassword
        default: return false
        }
    }

    func register() {
        showError = true
        if !hasValidationError {
            print("✅ Регистрация успешна")
            // TODO: вызов shared logic
        } else {
            print("❌ Ошибка: заполните все поля")
        }
    }
}

