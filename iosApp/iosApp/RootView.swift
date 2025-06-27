import SwiftUI
import shared

//struct RootScreen: View {
//    @StateObject var vm = MviViewModelWrapper(vm: ViewModelProvider.shared.getRootViewModel())
//
//
//    var body: some View {
//        ZStack {
//            if let successState = vm.state as? RootStateSuccess {
//                let isLoggedIn: Bool = successState.isLoggedIn
//                NavigationView {
//                    if isLoggedIn {
//                        MainTabView()
//                    } else {
//                        AuthView()
//                    }
//                }
//            }
//        }
//        .task {
//            await vm.activateSideEffects()
//        }
//        .task {
//            await vm.activate()
//        }
//    }
//}

//struct RootView: View {
//    @StateObject private var vm = MviViewModelWrapper(vm: ViewModelProvider.shared.getRootViewModel())
//    @State private var path: [AppRoute] = []
//
//    var body: some View {
//        ZStack {
//            if let successState = vm.state as? RootStateSuccess {
//                let isLoggedIn: Bool = successState.isLoggedIn
//
//                if isLoggedIn {
//                    NavigationStack(path: $path) {
//                        MainTabView(path: $path)
//                            .navigationDestination(for: AppRoute.self) { route in
//                                switch route {
//                                case let .contentList(topicId, topicName):
//                                    let vm = MviViewModelWrapper(
//                                        vm: ViewModelProvider.shared.getContentListViewModel(topicId: topicId, topicName: topicName)
//                                    )
//                                    ContentListView(wrapper: vm) { content in
//                                        path.append(.reviewList(contentId: content.id, contentName: content.name))
//                                    }
//
//                                case let .reviewList(contentId, contentName):
//                                    let vm = MviViewModelWrapper(
//                                        vm: ViewModelProvider.shared.getReviewListViewModel(contentId: contentId)
//                                    )
//                                    ReviewListView(wrapper: vm, contentName: contentName)
//
//                                case let .userProfile(user):
//                                    let vm = MviViewModelWrapper(
//                                        vm: ViewModelProvider.shared.getUserInfoViewModel(userId: user.id.description())
//                                    )
//                                    UserInfoView(wrapper: vm, initialUserModel: user)
//
//                                case .topicList:
//                                    EmptyView()
//                                }
//                            }
//                    }
//                } else {
//                    AuthView()
//                }
//            }
//        }
//        .task {
//            await vm.activateSideEffects()
//        }
//        .task {
//            await vm.activate()
//        }
//    }
//}

//import SwiftUI
//import shared

//struct RootView: View {
//    @StateObject var vm = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getRootViewModel()
//    )
//
//    var body: some View {
//        ZStack {
//            if let successState = vm.state as? RootStateSuccess {
//                if successState.isLoggedIn {
//                    MainTabView()
//                } else {
//                    AuthView()
//                }
//            } else {
//                ProgressView()
//            }
//        }
//        .task {
//            await vm.activate()
//        }
//    }
//}

import SwiftUI
import shared

struct RootView: View {
    @StateObject private var wrapper = MviViewModelWrapper(
        vm: ViewModelProvider.shared.getRootViewModel()
    )

    @StateObject private var router = AppRouter()

    var body: some View {
        ZStack {
            if let successState = wrapper.state as? RootStateSuccess {
                if successState.isLoggedIn {
                    NavigationStack(path: $router.path) {
                        MainTabView(
                            onTopicSelected: { topic in
                                router.push(.contentList(topicId: topic.id, topicName: topic.name))
                            },
                            onContentSelected: { content in
                                router.push(.reviewList(contentId: content.id, contentName: content.name))
                            },
                            onUserSelected: { user in
                                router.push(.userProfile(user: user))
                            }
                        )
                        .navigationDestination(for: AppRoute.self) { route in
                            switch route {
                            case .contentList(let topicId, let topicName):
                                let vm = MviViewModelWrapper(
                                    vm: ViewModelProvider.shared.getContentListViewModel(
                                        topicId: topicId,
                                        topicName: topicName
                                    )
                                )
                                ContentListView(wrapper: vm) { content in
                                    router.push(.reviewList(contentId: content.id, contentName: content.name))
                                }

                            case .reviewList(let contentId, let contentName):
                                let vm = MviViewModelWrapper(
                                    vm: ViewModelProvider.shared.getReviewListViewModel(contentId: contentId)
                                )
                                ReviewListView(wrapper: vm, contentName: contentName)

                            case .userProfile(let user):
                                let vm = MviViewModelWrapper(
                                    vm: ViewModelProvider.shared.getUserInfoViewModel(userId: user.id.description())
                                )
                                UserInfoView(wrapper: vm, initialUserModel: user)
                            }
                        }
                    }
                } else {
                    AuthView()
                }
            } else {
                ProgressView()
            }
        }
        .task { await wrapper.activate() }
        .task { await wrapper.activateSideEffects() }
        .environmentObject(router)
    }
}
