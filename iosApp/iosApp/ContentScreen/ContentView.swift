import SwiftUI

struct ContentView: View {
    let theme: ThemeModel
    @StateObject private var viewModel = ContentViewModel()

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                Text("Тема: \(theme.name)")
                    .font(.title)
                    .bold()
                    .padding(.horizontal)

                ForEach(viewModel.contents) { content in
                    ContentCardView(content: content)
                        .padding(.horizontal)
//                    NavigationLink(destination: ReviewView(content: content)) {
//                        ContentCardView(content: content)
//                            .padding(.horizontal)
//                    }
                }
            }
            .padding(.top)
        }
        .navigationBarTitleDisplayMode(.inline)
        .navigationTitle("Поиск")
        .toolbarBackground(Color("NavigationBarColor"), for: .navigationBar)
        //.toolbarBackgroundVisibility(.visible, for: .navigationBar)
        .onAppear {
            viewModel.loadContents(for: theme.id)
        }
    }
}

