import SwiftUI
import PhotosUI
import shared

struct RegistrationView: View {
    @StateObject var vm: MviViewModelWrapper<AuthIntent, AuthState, AuthSideEffect>
    @State private var showImagePicker = false
    @State private var pickerItem: PhotosPickerItem? = nil
    @State var avatar: UIImage?

    var body: some View {
        let state = vm.state.registerState
        ScrollView {
            VStack(spacing: 16) {
                Text("Регистрация")
                    .font(.title)
                    .foregroundStyle(Color("PrimaryColor"))
                    .bold()
                    .padding(.top, 24)

                VStack(spacing: 16) {
                    AvatarPickerView(
                        image: $avatar,
                        isPickerPresented: $showImagePicker
                    )

                    InputField(
                        placeholder: "Логин",
                        text: Binding(
                            get: { state.username },
                            set: { newValue in
                                vm.accept(intent: AuthIntentOnUsernameChange(username: newValue))
                            }
                        ),
                        showError: vm.state.registerState.isUsernameValid == false && vm.state.registerState.showFieldError
                    )

                    InputField(
                        placeholder: "Эл. почта",
                        text: Binding(
                            get: { state.email },
                            set: { newValue in
                                vm.accept(intent: AuthIntentOnEmailChange(email: newValue))
                            }
                        ),
                        showError: state.isEmailValid == false && state.showFieldError
                    )

                    InputField(
                        placeholder: "Номер телефона",
                        text: Binding(
                            get: { state.phone },
                            set: { newValue in
                                vm.accept(intent: AuthIntentOnPhoneChange(phone: newValue))
                            }
                        ),
                        showError: false
                    )

                    InputField(
                        placeholder: "Полное имя",
                        text: Binding(
                            get: { state.fullName },
                            set: { newValue in
                                vm.accept(intent: AuthIntentOnFullNameChange(fullName: newValue))
                            }
                        ),
                        showError: state.isFullNameValid == false && state.showFieldError
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
                        showError: state.isPasswordValid == false && state.showFieldError
                    )

                    InputField(
                        placeholder: "Подтверждение пароля",
                        text: Binding(
                            get: { state.passwordConfirmation },
                            set: { newValue in
                                vm.accept(intent: AuthIntentOnPasswordConfirmationChange(passwordConfirmation: newValue))
                            }
                        ),
                        isSecure: true,
                        showError: state.isPasswordConfirmationValid == false && state.showFieldError
                    )

                    Group {
                        if let error = state.error {
                            Text(error.localizedMessage)
                                .foregroundColor(.red)
                                .font(.caption)
                        }
                    }

                    PrimaryButton(title: "Создать аккаунт") {
                        vm.accept(intent: AuthIntentOnRegister())
                    }

                    Button("Войти в аккаунт") {
                        vm.accept(intent: AuthIntentReturnToSignIn())
                    }
                    .foregroundColor(Color("PrimaryColor"))
                }
                .padding()
                .background(Color(UIColor.systemGray6))
                .cornerRadius(16)
                .padding(.horizontal)
            }
            .padding(.bottom, 32)

        }
        .background(Color(UIColor.systemGray5).opacity(0.1).ignoresSafeArea())
        .photosPicker(isPresented: $showImagePicker, selection: $pickerItem)
        .onChange(of: pickerItem) { newItem in
            guard let item = newItem else { return }
            Task {
                if let data = try? await item.loadTransferable(type: Data.self),
                   let uiImage = UIImage(data: data) {
                    avatar = uiImage

                    let filename = UUID().uuidString + ".jpg"
                    let tempUrl = FileManager.default.temporaryDirectory.appendingPathComponent(filename)
                    do {
                        try data.write(to: tempUrl)
                        print("✅ Image saved: \(tempUrl)")
                        vm.accept(intent: AuthIntentOnImagePicked(uri: tempUrl.absoluteString))
                    } catch {
                        print("❌ Failed to save image: \(error)")
                    }
                }
            }
        }
    }
}
