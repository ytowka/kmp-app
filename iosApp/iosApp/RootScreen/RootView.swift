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
                            onUserSelected: { userId in
                                router.push(.userProfile(userId: userId))
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
                                ReviewListView(wrapper: vm, contentName: contentName, contentId: contentId)

                            case .userProfile(let userId):
                                let vm = MviViewModelWrapper(
                                    vm: ViewModelProvider.shared.getUserInfoViewModel(userId: userId)
                                )
                                UserInfoView(wrapper: vm, initialUserModel: nil)
                            case .reviewEditor(let contentId):
                                let vm = MviViewModelWrapper(
                                    vm: ViewModelProvider.shared.getReviewEditorViewModel(contentId: contentId)
                                )
                                
                                ReviewEditorView(
                                    wrapper: vm,
                                    onSave: {
                                        router.triggerProfileRefresh()
                                        router.replace( .userProfile(userId: ""))
                                    },
                                    onBack: { router.pop() }
                                )
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
