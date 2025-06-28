import SwiftUI

struct SearchBarView: View {
    @Binding var text: String

    var body: some View {
        HStack(spacing: 8) {
            TextField("", text: $text, prompt: Text("Поиск").foregroundColor(.black))
                .autocorrectionDisabled(true)
                .textInputAutocapitalization(.never)
            
            Button(action: { text = "" }) {
                text.isEmpty
                ?
                Image(systemName: "magnifyingglass")
                    .foregroundColor(.black)
                :
                Image(systemName: "xmark.circle.fill")
                    .foregroundColor(.black)

            }
        }
        .padding(10)
        .background(Color("NavigationBarColor"))
        .frame(maxWidth: .infinity)
    }
}

