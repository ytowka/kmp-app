import SwiftUI
import shared

struct LoginView: View {
    @StateObject var vm: MviViewModelWrapper<FeatureAuthIntent, FeatureAuthState, FeatureAuthSideEffect>

    var body: some View {
        let state = vm.state.loginState
        VStack {
            Spacer()
            Text("Вход")
                .font(.title)
                .foregroundStyle(Color("PrimaryColor"))
                .bold()
                .padding()
            
            VStack(spacing: 16) {
            
                let loginBinding = Binding<String>(
                    get: { state.username },
                    set: { newValue in
                        vm.accept(intent: FeatureAuthIntentOnUsernameChange(username: newValue))
                    }
                )
                InputField(
                    placeholder: "Логин",
                    text: loginBinding,
                    isSecure: false,
                    showError: state.isUsernameValid == false && state.showFieldError
                )

                InputField(
                    placeholder: "Пароль",
                    text: Binding(
                        get: { state.password },
                        set: { newValue in
                            vm.accept(intent: FeatureAuthIntentOnPasswordChange(password: newValue))
                        }
                    ),
                    isSecure: true,
                    showError: state.isPasswordValid == false && state.showFieldError
                )

                Group {
                    if let error = state.error {
                        Text(error.localizedMessage)
                            .foregroundColor(.red)
                            .font(.caption)
                    }
                }

                PrimaryButton(title: "Войти") {
                    vm.accept(intent: FeatureAuthIntentOnLogin())
                }

                Button("Создать аккаунт") {
                    vm.accept(intent: FeatureAuthIntentOnCreateAccount())
                }
            }
            .padding()
            .background(Color(UIColor.systemGray6))
            .cornerRadius(16)
            .padding(.horizontal)

            Spacer()
        }
        .background(Color(UIColor.systemGray5).opacity(0.1).ignoresSafeArea())
    }
}
