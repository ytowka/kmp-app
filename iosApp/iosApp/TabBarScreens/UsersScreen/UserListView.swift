import SwiftUI
import shared

struct UserListView: View {
    @StateObject var wrapper: MviViewModelWrapper<UserListIntent, UserListState, UserListSideEffect>

    var body: some View {
        let state = wrapper.state
        let users = state.currentPagingState.list.compactMap { $0 as? shared.UserModel }

        VStack {
            SearchBarView(
                text: Binding(
                    get: { state.searchQuery },
                    set: { newValue in
                        wrapper.accept(intent: UserListIntentSearch(query: newValue))
                    }
                )
            )

            ScrollView {
                LazyVStack(spacing: 12) {
                    ForEach(users.indices, id: \.self) { index in
                        let user = users[index]
                        UserRowView(user: user)
                            .onAppear {
                                if index == users.count - 1,
                                   state.searchQuery.isEmpty,
                                   state.currentPagingState.hasNextPage {
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

