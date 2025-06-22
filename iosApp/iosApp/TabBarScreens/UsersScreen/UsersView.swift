import SwiftUI

struct UsersView: View {
    @StateObject private var viewModel = UsersViewModel()

    var body: some View {
        VStack {
            SearchBarView(text: $viewModel.searchText)
            
            ScrollView {
                VStack(spacing: 12) {
                    ForEach(viewModel.filteredUsers) { user in
                        UserRowView(user: user)
                    }
                }
                .padding()
            }
        }
        .navigationBarModifier(title: "Пользователи")
    }
}

