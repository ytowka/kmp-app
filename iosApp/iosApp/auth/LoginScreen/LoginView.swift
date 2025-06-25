import SwiftUI
import shared

struct LoginView: View {
    @StateObject var vm: MviViewModelWrapper<FeatureAuthIntent, FeatureAuthState, FeatureAuthSideEffect>

    var body: some View {
//        let state = vm.state.loginState
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
                    showError: self.vm.state.loginState.isUsernameValid == false && self.vm.state.loginState.showFieldError
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
                    showError: self.vm.state.loginState.isPasswordValid == false && self.vm.state.loginState.showFieldError
                )

                if let error = self.vm.state.loginState.error {
                    Text("\(error)")
                }

//                switch state.error {
//                case .field:
//                    Text("Проверьте поля")
//                        .foregroundColor(.red)
//                        .font(.caption)
//                default:
//                    EmptyView()
//                }
        

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
