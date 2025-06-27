import SwiftUI

struct ThemesView: View {
    @StateObject private var viewModel = ThemesViewModel()

    var body: some View {
        Text("test")
//        ScrollView {
//            VStack(alignment: .leading, spacing: 24) {
//                ForEach(viewModel.themes) { theme in
//                    NavigationLink(destination: ContentView(theme: theme)) {
////                        TopicCardView(theme: theme)
//                    }
//                }
//                //                ForEach(viewModel.themes) { theme in
//                //                    ThemeCardView(theme: theme)
//                //                }
//            }
//            .padding(.top)
//        }
//        .navigationBarModifier(title: "Темы")
    }
}
