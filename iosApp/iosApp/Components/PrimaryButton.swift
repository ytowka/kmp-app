import SwiftUI

struct PrimaryButton: View {
    let title: String
    let action: () -> Void

    var body: some View {
        Button(title, action: action)
            .padding(.horizontal, 24)
            .padding(.vertical, 12)
            .background(Color("PrimaryColor"))
            .foregroundColor(Color("ButtonTextColor"))
            .fontWeight(.semibold)
            .clipShape(Capsule())
    }
}
