import Foundation
import SwiftUI

final class LoginViewModel: ObservableObject {
    @Published var username: String = ""
    @Published var password: String = ""
    @Published var showError: Bool = false

    @AppStorage("isLoggedIn") var isLoggedIn: Bool = false

    var hasValidationError: Bool {
        showError && (username.isEmpty || password.isEmpty)
    }

    func login() {
        if username.isEmpty || password.isEmpty {
            showError = true
        } else {
            showError = false
            isLoggedIn = true
        }

        // TODO: интеграция с KMP
    }
}
