import SwiftUI
import shared

struct MainTabView: View {
    @Namespace var tabAnimation
    @State private var selectedTab: TabItem = .main
    @StateObject private var profileWrapper = MviViewModelWrapper(
        vm: ViewModelProvider.shared.getUserInfoViewModel(userId: "")
    )

    var body: some View {
        NavigationStack {
            VStack(spacing: 0) {
                ZStack {
                    switch selectedTab {
                    case .main:
                        ThemesView()
                            .id("themesView")
                    case .users:
                        UsersView()
                            .id("usersView")
                    case .profile:
                        UserInfoView(wrapper: profileWrapper)
                        .id("userInfo")
                        //                        let user = UserModel(id: "555", username: "kerikg", fullName: "Kirill Abramov", avatarUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVzdxX6cZ5CrZJl1rE6FvzVT5_GFb11AZ9Cg&s")
                        //                        ProfileView(user: user)
                        //                            .id("profileView")
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

    }
}


//struct MainTabView: View {
//    var body: some View {
//        TabView {
//            ThemesView()
//                .tabItem {
//                    Image(systemName: "list.bullet")
//                    Text("Главная")
//                }
//
//            Text("Пользователи")
//                .tabItem {
//                    Image(systemName: "person.2.fill")
//                    Text("Пользователи")
//                }
//
//            Text("Профиль")
//                .tabItem {
//                    Image(systemName: "person.crop.circle")
//                    Text("Профиль")
//                }
//        }
//        .accentColor(Color.white)
//    }
//}
