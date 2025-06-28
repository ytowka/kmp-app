import SwiftUI
import shared

//struct MainTabView: View {
//    @Namespace var tabAnimation
//    @State private var selectedTab: TabItem = .topics
//    @StateObject private var profileWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getUserInfoViewModel(userId: "")
//    )
//
//    @StateObject private var topicWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getTopicViewModel()
//    )
//
//    @StateObject private var userListWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getUserListViewModel()
//    )
//
//    var body: some View {
//        let _ = ViewModelProvider.shared.getUserInfoViewModel(userId: "")
//
//        NavigationStack {
//            VStack(spacing: 0) {
//                ZStack {
//                    switch selectedTab {
//                    case .topics:
//                        TopicView(wrapper: topicWrapper)
//                            .id("topicView")
//                    case .users:
//                        UserListView(wrapper: userListWrapper)
//                            .id("userListView")
//                    case .profile:
//                        UserInfoView(wrapper: profileWrapper)
//                            .id("userInfoView")
//                    }
//                }
//                .frame(maxWidth: .infinity, maxHeight: .infinity)
//                .animation(nil, value: selectedTab)
//
//                Divider()
//
//                HStack {
//                    ForEach(TabItem.allCases, id: \.self) { tab in
//                        TabBarItemView(tab: tab, isSelected: tab == selectedTab, namespace: tabAnimation) {
//                            withAnimation(.easeInOut(duration: 0.1)) {
//                                selectedTab = tab
//                            }
//                        }
//                        .frame(maxWidth: .infinity)
//                    }
//                }
//                .frame(height: 60)
//                .background(Color.white)
//                //            .padding(.top, 8)
//                //            .padding(.bottom, 16)
//                //            .background(Color.white.ignoresSafeArea(edges: .bottom))
//            }
//        }
//        .onAppear {
//            setupNavigationBarAppearance()
//        }
//    }
//
//    func setupNavigationBarAppearance() {
//        let appearance = UINavigationBarAppearance()
//        appearance.configureWithOpaqueBackground()
//        appearance.backgroundColor = UIColor(named: "NavigationBarColor")
//        appearance.titleTextAttributes = [.foregroundColor: UIColor.black]
//
//        UINavigationBar.appearance().standardAppearance = appearance
//        UINavigationBar.appearance().scrollEdgeAppearance = appearance
//        UINavigationBar.appearance().compactAppearance = appearance
//    }
//}

//struct MainTabView: View {
//    @Binding var path: [AppRoute]
//
//    @Namespace var tabAnimation
//    @State private var selectedTab: TabItem = .topics
//
//    @StateObject private var profileWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getUserInfoViewModel(userId: "")
//    )
//
//    @StateObject private var topicWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getTopicViewModel()
//    )
//
//    @StateObject private var userListWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getUserListViewModel()
//    )
//
//    var body: some View {
//        VStack(spacing: 0) {
//            ZStack {
//                switch selectedTab {
//                case .topics:
//                    TopicView(wrapper: topicWrapper, navigateToContentList: { topicId, topicName  in
//                        path.append(.contentList(topicId: topicId, topicName: topicName))
//                    }).id("topicView")
////
//                case .users:
//                    UserListView(wrapper: userListWrapper, onUserSelected: { user in
//                        path.append(.userProfile(user: user))
//                    }).id("userListView")
//
//                case .profile:
//                    UserInfoView(wrapper: profileWrapper)
//                        .id("userInfoView")
//                }
//            }
//
//            Divider()
//
//            HStack {
//                ForEach(TabItem.allCases, id: \.self) { tab in
//                    TabBarItemView(tab: tab, isSelected: tab == selectedTab, namespace: tabAnimation) {
//                        withAnimation(.easeInOut(duration: 0.1)) {
//                            selectedTab = tab
//                        }
//                    }
//                    .frame(maxWidth: .infinity)
//                }
//            }
//            .frame(height: 60)
//            .background(Color.white)
//        }
//        .onAppear {
//            setupNavigationBarAppearance()
//        }
//    }
//
//    func setupNavigationBarAppearance() {
//        let appearance = UINavigationBarAppearance()
//        appearance.configureWithOpaqueBackground()
//        appearance.backgroundColor = UIColor(named: "NavigationBarColor")
//        appearance.titleTextAttributes = [.foregroundColor: UIColor.black]
//
//        UINavigationBar.appearance().standardAppearance = appearance
//        UINavigationBar.appearance().scrollEdgeAppearance = appearance
//        UINavigationBar.appearance().compactAppearance = appearance
//    }
//}

//struct MainTabView: View {
//    @Namespace var tabAnimation
//    @State private var selectedTab: TabItem = .topics
//
//    @StateObject private var profileWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getUserInfoViewModel(userId: "")
//    )
//
//    @StateObject private var topicWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getTopicViewModel()
//    )
//
//    @StateObject private var userListWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getUserListViewModel()
//    )
//
//    var body: some View {
//        VStack(spacing: 0) {
//            ZStack {
//                switch selectedTab {
//                case .topics:
//                    NavigationStack {
//                        TopicView(
//                            wrapper: topicWrapper,
//                            navigateToContentList: { /* handle */ }
//                        )
//                        .navigationTitle("Темы")
//                        .navigationBarTitleDisplayMode(.inline)
//                    }
//                    .id("topicView")
//
//                case .users:
//                    NavigationStack {
//                        UserListView(
//                            wrapper: userListWrapper,
//                            onUserSelected: { /* handle */ }
//                        )
//                        .navigationTitle("Пользователи")
//                        .navigationBarTitleDisplayMode(.inline)
//                    }
//                    .id("userListView")
//
//                case .profile:
//                    NavigationStack {
//                        UserInfoView(
//                            wrapper: profileWrapper,
//                            initialUserModel: nil
//                        )
//                        .navigationTitle("Профиль")
//                        .navigationBarTitleDisplayMode(.inline)
//                    }
//                    .id("userInfoView")
//                }
//            }
//            .animation(nil, value: selectedTab)
//
//            Divider()
//
//            HStack {
//                ForEach(TabItem.allCases, id: \.self) { tab in
//                    TabBarItemView(tab: tab, isSelected: tab == selectedTab, namespace: tabAnimation) {
//                        withAnimation(.easeInOut(duration: 0.1)) {
//                            selectedTab = tab
//                        }
//                    }
//                    .frame(maxWidth: .infinity)
//                }
//            }
//            .frame(height: 60)
//            .background(Color.white)
//        }
//        .onAppear {
//            setupNavigationBarAppearance()
//        }
//    }
//
//    func setupNavigationBarAppearance() {
//        let appearance = UINavigationBarAppearance()
//        appearance.configureWithOpaqueBackground()
//        appearance.backgroundColor = UIColor(named: "NavigationBarColor")
//        appearance.titleTextAttributes = [.foregroundColor: UIColor.black]
//
//        UINavigationBar.appearance().standardAppearance = appearance
//        UINavigationBar.appearance().scrollEdgeAppearance = appearance
//        UINavigationBar.appearance().compactAppearance = appearance
//    }
//}

//import SwiftUI
//import shared
//
//struct MainTabView: View {
//    @Namespace var tabAnimation
//    @State private var selectedTab: TabItem = .topics
//    @State private var topicPath = NavigationPath()
//    @State private var userPath = NavigationPath()
//    @State private var profilePath = NavigationPath()
//
//    // ViewModels
//    @StateObject private var topicWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getTopicViewModel()
//    )
//    @StateObject private var userWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getUserListViewModel()
//    )
//    @StateObject private var profileWrapper = MviViewModelWrapper(
//        vm: ViewModelProvider.shared.getUserInfoViewModel(userId: "")
//    )
//
//    var body: some View {
//        VStack(spacing: 0) {
//            ZStack {
//                switch selectedTab {
//                case .topics:
//                    NavigationStack(path: $topicPath) {
//                        TopicView(
//                            wrapper: topicWrapper,
//                            navigateToContentList: { topicId, topicName in
//                                topicPath.append(AppRoute.contentList(topicId: topicId, topicName: topicName))
//                            }
//                        )
//                        .id("topicView")
//                        .navigationDestination(for: AppRoute.self) { route in
//                            switch route {
//                            case .contentList(let topicId, let topicName):
//                                let vm = MviViewModelWrapper(
//                                    vm: ViewModelProvider.shared.getContentListViewModel(topicId: topicId, topicName: topicName)
//                                )
//                                ContentListView(wrapper: vm) { content in
//                                    topicPath.append(AppRoute.reviewList(contentId: content.id, contentName: content.name))
//                                }
//                            case .reviewList(let contentId, let contentName):
//                                let vm = MviViewModelWrapper(
//                                    vm: ViewModelProvider.shared.getReviewListViewModel(contentId: contentId)
//                                )
//                                ReviewListView(wrapper: vm, contentName: contentName)
//                            default: EmptyView()
//                            }
//                        }
//                    }
//
//                case .users:
//                    NavigationStack(path: $userPath) {
//                        UserListView(wrapper: userWrapper) { user in
//                            userPath.append(AppRoute.userProfile(user: user))
//                        }
//                        .id("userListView")
//                        .navigationDestination(for: AppRoute.self) { route in
//                            switch route {
//                            case .userProfile(let user):
//                                let vm = MviViewModelWrapper(
//                                    vm: ViewModelProvider.shared.getUserInfoViewModel(userId: user.id.description())
//                                )
//                                UserInfoView(wrapper: vm, initialUserModel: user)
//                            default: EmptyView()
//                            }
//                        }
//                    }
//
//                case .profile:
//                    NavigationStack(path: $profilePath) {
//                        UserInfoView(wrapper: profileWrapper, initialUserModel: nil)
//                            .id("userInfoView")
//                    }
//                }
//            }
//
//            Divider()
//
//            HStack {
//                ForEach(TabItem.allCases, id: \.self) { tab in
//                    TabBarItemView(tab: tab, isSelected: tab == selectedTab, namespace: tabAnimation) {
//                        withAnimation(.easeInOut(duration: 0.1)) {
//                            selectedTab = tab
//                        }
//                    }
//                    .frame(maxWidth: .infinity)
//                }
//            }
//            .frame(height: 60)
//            .background(Color.white)
//        }
//        .onAppear {
//            setupNavigationBarAppearance()
//        }
//    }
//
//    func setupNavigationBarAppearance() {
//        let appearance = UINavigationBarAppearance()
//        appearance.configureWithOpaqueBackground()
//        appearance.backgroundColor = UIColor(named: "NavigationBarColor")
//        appearance.titleTextAttributes = [.foregroundColor: UIColor.black]
//
//        UINavigationBar.appearance().standardAppearance = appearance
//        UINavigationBar.appearance().scrollEdgeAppearance = appearance
//        UINavigationBar.appearance().compactAppearance = appearance
//    }
//}

import SwiftUI
import shared

struct MainTabView: View {
    @Namespace var tabAnimation
    @State private var selectedTab: TabItem = .topics
    @EnvironmentObject var router: AppRouter
    @State private var refreshToken = UUID()

    @StateObject private var profileWrapper = MviViewModelWrapper(
        vm: ViewModelProvider.shared.getUserInfoViewModel(userId: "")
    )

    @StateObject private var topicWrapper = MviViewModelWrapper(
        vm: ViewModelProvider.shared.getTopicViewModel()
    )

    @StateObject private var userListWrapper = MviViewModelWrapper(
        vm: ViewModelProvider.shared.getUserListViewModel()
    )

    var onTopicSelected: (shared.TopicModel) -> Void
    var onContentSelected: (shared.ContentModel) -> Void
    var onUserSelected: (String) -> Void

    var body: some View {
        VStack(spacing: 0) {
            ZStack {
                switch selectedTab {
                case .topics:
                    TopicView(wrapper: topicWrapper) { topic in
                        onTopicSelected(topic)
                    }

                case .users:
                    UserListView(wrapper: userListWrapper) { userId in
                        onUserSelected(userId)
                    }

                case .profile:
                    let wrapper = MviViewModelWrapper(
                        vm: ViewModelProvider.shared.getUserInfoViewModel(userId: "")
                    )
                    UserInfoView(wrapper: wrapper)
                        .id(refreshToken)
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .animation(nil, value: selectedTab)

            Divider()

            HStack {
                ForEach(TabItem.allCases, id: \.self) { tab in
                    TabBarItemView(tab: tab, isSelected: tab == selectedTab, namespace: tabAnimation) {
                        withAnimation(.easeInOut(duration: 0.1)) {
                            selectedTab = tab
                        }
                    }
                    .frame(maxWidth: .infinity)
                }
            }
            .frame(height: 60)
            .background(Color("TabBarColor"))
        }
        .onAppear {
            setupNavigationBarAppearance()
        }
        .onChange(of: router.refreshProfileView) { _ in
            refreshToken = UUID()
        }
    }

    func setupNavigationBarAppearance() {
        let appearance = UINavigationBarAppearance()
        appearance.configureWithOpaqueBackground()
        appearance.backgroundColor = UIColor(named: "NavigationBarColor")
        appearance.titleTextAttributes = [.foregroundColor: UIColor.black]

        UINavigationBar.appearance().standardAppearance = appearance
        UINavigationBar.appearance().scrollEdgeAppearance = appearance
        UINavigationBar.appearance().compactAppearance = appearance
    }
}
