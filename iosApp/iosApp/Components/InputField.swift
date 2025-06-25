import SwiftUI

struct InputField: View {
    let placeholder: String
    @Binding var text: String
    var isSecure: Bool = false
    var showError: Bool = false

    var body: some View {
        Group {
            if isSecure {
                SecureField(placeholder, text: $text)
                    .autocorrectionDisabled()
                    .textInputAutocapitalization(.never)
            } else {
                TextField(placeholder, text: $text)
                    .autocorrectionDisabled()
                    .textInputAutocapitalization(.never)
            }
        }
        .padding()
        .background(
            RoundedRectangle(cornerRadius: 8)
                .stroke(showError ? Color.red : Color.gray, lineWidth: 1)
        )
    }
}
