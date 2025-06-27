import SwiftUI
import shared

//struct UserListView: View {
//    @StateObject var wrapper: MviViewModelWrapper<UserListIntent, UserListState, UserListSideEffect>
//    @State private var path = NavigationPath()
//
//    var body: some View {
//        let state = wrapper.state
//        let users = state.currentPagingState.list.compactMap { $0 as? shared.UserModel }
//
//        NavigationStack(path: $path) {
//            VStack {
//                SearchBarView(
//                    text: Binding(
//                        get: { state.searchQuery },
//                        set: { wrapper.accept(intent: UserListIntentSearch(query: $0)) }
//                    )
//                )
//
//                ScrollView {
//                    LazyVStack {
//                        ForEach(users, id: \.id) { user in
//                            Button {
//                                path.append(user)
//                            } label: {
//                                UserRowView(user: user)
//                            }
//                            .onAppear {
//                                if user.id == users.last?.id,
//                                   state.users.hasNextPage,
//                                   !state.isLoading {
//                                    wrapper.accept(intent: UserListIntentLoadNext())
//                                }
//                            }
//                        }
//                    }
//                    .padding()
//                }
//            }
//            .navigationTitle("Пользователи")
//            .navigationBarTitleDisplayMode(.inline)
//            .task {
//                await wrapper.activate()
//            }
//            .navigationDestination(for: shared.UserModel.self) { user in
//                let vm = MviViewModelWrapper(
//                    vm: ViewModelProvider.shared.getUserInfoViewModel(userId: user.id.description())
//                )
//
//                UserInfoView(wrapper: vm, initialUserModel: user)
//            }
//        }
//    }
//}

//import SwiftUI
//import shared
//
//struct UserListView: View {
//    @ObservedObject var wrapper: MviViewModelWrapper<UserListIntent, UserListState, UserListSideEffect>
//    var onUserSelected: (shared.UserModel) -> Void
//
//    var body: some View {
//        let state = wrapper.state
//        let users = state.currentPagingState.list.compactMap { $0 as? shared.UserModel }
//
//        VStack {
//            SearchBarView(
//                text: Binding(
//                    get: { state.searchQuery },
//                    set: { wrapper.accept(intent: UserListIntentSearch(query: $0)) }
//                )
//            )
//
//            ScrollView {
//                LazyVStack {
//                    ForEach(users, id: \.id) { user in
//                        Button {
//                            onUserSelected(user)
//                        } label: {
//                            UserRowView(user: user)
//                        }
//                        .onAppear {
//                            if user.id == users.last?.id,
//                               state.users.hasNextPage,
//                               !state.isLoading {
//                                wrapper.accept(intent: UserListIntentLoadNext())
//                            }
//                        }
//                    }
//                }
//                .padding()
//            }
//        }
//        .navigationTitle("Пользователи")
//        .navigationBarTitleDisplayMode(.inline)
//        .task {
//            await wrapper.activate()
//        }
//    }
//}

import SwiftUI
import shared

struct UserListView: View {
    @StateObject var wrapper: MviViewModelWrapper<UserListIntent, UserListState, UserListSideEffect>
    var onUserSelected: (shared.UserModel) -> Void

    var body: some View {
        let state = wrapper.state
        let users = state.currentPagingState.list.compactMap { $0 as? shared.UserModel }

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
                            onUserSelected(user)
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
        .navigationTitle("Пользователи")
        .navigationBarTitleDisplayMode(.inline)
        .task {
            await wrapper.activate()
        }
    }
}
