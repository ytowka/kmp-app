import SwiftUI
import PhotosUI
import shared

struct RegistrationView: View {
    let vm: MviViewModelWrapper<FeatureAuthIntent, FeatureAuthState, FeatureAuthSideEffect>
    @State private var showImagePicker = false
    @State private var pickerItem: PhotosPickerItem? = nil
    @State var avatar: UIImage?
    @Environment(\.dismiss) var dismiss

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
                                vm.accept(intent: FeatureAuthIntentOnUsernameChange(username: newValue))
                            }
                        ),
                        showError: state.isUsernameValid == false
                    )

                    InputField(
                        placeholder: "Эл. почта",
                        text: Binding(
                            get: { state.email },
                            set: { newValue in
                                vm.accept(intent: FeatureAuthIntentOnEmailChange(email: newValue))
                            }
                        ),
                        showError: state.isEmailValid == false
                    )

                    InputField(
                        placeholder: "Номер телефона",
                        text: Binding(
                            get: { state.phone },
                            set: { newValue in
                                vm.accept(intent: FeatureAuthIntentOnPhoneChange(phone: newValue))
                            }
                        ),
                        showError: false
                    )

                    InputField(
                        placeholder: "Полное имя",
                        text: Binding(
                            get: { state.fullName },
                            set: { newValue in
                                vm.accept(intent: FeatureAuthIntentOnFullNameChange(fullName: newValue))
                            }
                        ),
                        showError: state.isFullNameValid == false
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
                        showError: state.isPasswordValid == false
                    )

                    InputField(
                        placeholder: "Подтверждение пароля",
                        text: Binding(
                            get: { state.passwordConfirmation },
                            set: { newValue in
                                vm.accept(intent: FeatureAuthIntentOnPasswordConfirmationChange(passwordConfirmation: newValue))
                            }
                        ),
                        isSecure: true,
                        showError: state.isPasswordConfirmationValid == false
                    )

//                    InputField(placeholder: "Логин", text: $viewModel.username)
//                    InputField(placeholder: "Эл. почта", text: $viewModel.email)
//                    InputField(placeholder: "Номер телефона", text: $viewModel.phone)
//                    InputField(placeholder: "Полное имя", text: $viewModel.fullName)
//                    InputField(placeholder: "Пароль", text: $viewModel.password, isSecure: true)
//                    InputField(placeholder: "Подтверждение пароля", text: $viewModel.confirmPassword, isSecure: true)

                    switch state.error {
                    case .field:
                        Text("Проверьте поля")
                            .foregroundColor(.red)
                            .font(.caption)
                    default:
                        EmptyView()
                    }

                    PrimaryButton(title: "Создать аккаунт") {
                        vm.accept(intent: FeatureAuthIntentOnRegister())
                    }

                    Button("Войти в аккаунт") {
                        dismiss()
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
//        .onChange(of: pickerItem) { oldItem, newItem in
//            guard let item = newItem else { return }
//            Task {
//                if let data = try? await item.loadTransferable(type: Data.self),
//                   let uiImage = UIImage(data: data) {
//                    viewModel.avatar = uiImage
//                }
//            }
//        }

    }
}
