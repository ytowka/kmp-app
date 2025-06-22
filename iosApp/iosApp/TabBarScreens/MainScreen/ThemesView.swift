import SwiftUI

struct ThemesView: View {
    @StateObject private var viewModel = ThemesViewModel()

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 24) {
                ForEach(viewModel.themes) { theme in
                    NavigationLink(destination: ContentView(theme: theme)) {
                        ThemeCardView(theme: theme)
                    }
                }
                //                ForEach(viewModel.themes) { theme in
                //                    ThemeCardView(theme: theme)
                //                }
            }
            .padding(.top)
        }
        .navigationBarModifier(title: "Темы")
    }
}

#Preview {
    ThemesView()
}
