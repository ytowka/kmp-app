import SwiftUI
import shared

struct MainTabView: View {
    @Namespace var tabAnimation
    @State private var selectedTab: TabItem = .topics
    @StateObject private var profileWrapper = MviViewModelWrapper(
        vm: ViewModelProvider.shared.getUserInfoViewModel(userId: "")
    )

    @StateObject private var topicWrapper = MviViewModelWrapper(
        vm: ViewModelProvider.shared.getTopicViewModel()
    )

    @StateObject private var userListWrapper = MviViewModelWrapper(
        vm: ViewModelProvider.shared.getUserListViewModel()
    )

    var body: some View {
        let _ = ViewModelProvider.shared.getUserInfoViewModel(userId: "")
        
        NavigationStack {
            VStack(spacing: 0) {
                ZStack {
                    switch selectedTab {
                    case .topics:
                        TopicView(wrapper: topicWrapper)
                            .id("topicView")
                    case .users:
                        UserListView(wrapper: userListWrapper)
                            .id("userListView")
                    case .profile:
                        UserInfoView(wrapper: profileWrapper)
                            .id("userInfoView")
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
                .background(Color.white)
                //            .padding(.top, 8)
                //            .padding(.bottom, 16)
                //            .background(Color.white.ignoresSafeArea(edges: .bottom))
            }
        }
        .onAppear {
            setupNavigationBarAppearance()
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
