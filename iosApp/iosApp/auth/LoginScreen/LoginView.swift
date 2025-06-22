import SwiftUI
import shared

struct LoginView: View {
    let vm: MviViewModelWrapper<FeatureAuthIntent, FeatureAuthState, FeatureAuthSideEffect>

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
                InputField(
                    placeholder: "Логин",
                    text: Binding(
                        get: { state.username },
                        set: { newValue in
                            vm.accept(intent: FeatureAuthIntentOnUsernameChange(username: newValue))
                        }
                    ),
                    isSecure: false,
                    showError: state.error != nil
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
                    vm.accept(intent: FeatureAuthIntentOnLogin())
                }

                NavigationLink("Создать аккаунт", destination: RegistrationView(vm: vm))
                    .foregroundColor(Color("PrimaryColor"))
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
