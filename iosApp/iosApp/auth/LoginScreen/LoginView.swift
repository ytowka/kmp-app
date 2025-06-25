import SwiftUI
import shared

struct LoginView: View {
    @StateObject var vm: MviViewModelWrapper<AuthIntent, AuthState, AuthSideEffect>

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
                        vm.accept(intent: AuthIntentOnUsernameChange(username: newValue))
                    }
                )
                InputField(
                    placeholder: "Логин",
                    text: loginBinding,
                    isSecure: false,
                    showError: state.error != nil
                )

                InputField(
                    placeholder: "Пароль",
                    text: Binding(
                        get: { state.password },
                        set: { newValue in
                            vm.accept(intent: AuthIntentOnPasswordChange(password: newValue))
                        }
                    ),
                    isSecure: true,
                    showError: state.error != nil
                )


                switch state.error {
                case .field:
                    Text("Проверьте поля")
                        .foregroundColor(.red)
                        .font(.caption)
                default:
                    EmptyView()
                }
        

                PrimaryButton(title: "Войти") {
                    vm.accept(intent: AuthIntentOnLogin())
                }

                Button("Создать аккаунт") {
                    vm.accept(intent: AuthIntentOnCreateAccount())
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
