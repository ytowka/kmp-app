import SwiftUI
import shared

struct UserListView: View {
    @StateObject var wrapper: MviViewModelWrapper<UserListIntent, UserListState, UserListSideEffect>
    var onUserSelected: (String) -> Void

    var body: some View {
        let state = wrapper.state
        let users = state.currentPagingState.list.compactMap { $0 as? UserModel }

        VStack {
            SearchBarView(
                text: Binding(
                    get: { state.searchQuery },
                    set: { wrapper.accept(intent: UserListIntentSearch(query: $0)) }
                )
            )

            ScrollView {
                LazyVStack(spacing: 12) {
                    ForEach(users, id: \.id) { user in
                        Button {
                            onUserSelected(user.id.description())
                        } label: {
                            UserRowView(user: user)
                        }
                        .onAppear {
                            if user.id == users.last?.id,
                               state.users.hasNextPage,
                               !state.isLoading {
                                wrapper.accept(intent: UserListIntentLoadNext())
                            }
                        }
                    }
                }
                .padding()
            }
        }
        .navigationBarModifier(title: "Пользователи")
        .task {
            await wrapper.activate()
        }
    }
}
