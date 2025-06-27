//import SwiftUI
//import shared
//
//struct ContentView: View {
//    let topic: TopicModel
//
//    var body: some View {
//        ScrollView {
//            VStack(alignment: .leading, spacing: 16) {
//                Text("Тема: \(theme.name)")
//                    .font(.title)
//                    .bold()
//                    .padding(.horizontal)
//
//                ForEach(viewModel.contents) { content in
//                    ContentCardView(content: content)
//                        .padding(.horizontal)
////                    NavigationLink(destination: ReviewView(content: content)) {
////                        ContentCardView(content: content)
////                            .padding(.horizontal)
////                    }
//                }
//            }
//            .padding(.top)
//        }
//        .navigationBarTitleDisplayMode(.inline)
//        .navigationTitle("Поиск")
//        .toolbarBackground(Color("NavigationBarColor"), for: .navigationBar)
//        //.toolbarBackgroundVisibility(.visible, for: .navigationBar)
//        .onAppear {
//            viewModel.loadContents(for: theme.id)
//        }
//    }
//}
//

//import SwiftUI
//import shared
//
//struct ContentListView: View {
//    @StateObject var wrapper: MviViewModelWrapper<ContentListIntent, ContentListState, ContentListSideEffect>
//    @State private var path = NavigationPath()
//
//    var body: some View {
//        let state = wrapper.state
//        let contentItems = state.currentPagerState.list.compactMap { $0 as? shared.ContentModel }
//
//        NavigationStack(path: $path) {
//            VStack {
//                SearchBarView(
//                    text: Binding(
//                        get: { state.searchQuery },
//                        set: { wrapper.accept(intent: ContentListIntentSearch(query: $0)) }
//                    )
//                )
//
//                ScrollView {
//                    LazyVStack(spacing: 12) {
//                        ForEach(state.recommendedContent, id: \.id) { content in
//                            ContentCardView(content: content)
//                                .onTapGesture {
//                                    onContentClick(content)
//                                }
//                        }
//
//                        Text("Тема: \(state.topicName)")
//                            .font(.headline)
//                            .padding(.horizontal)
//                    }
//
//                    ForEach(contentItems, id: \.id) { content in
//                        ContentCardView(model: content)
//                            .onTapGesture {
//                                path.append(content)
//                            }
//                            .onAppear {
//                                if content.id == contentItems.last?.id,
//                                   state.pagerState.hasNextPage,
//                                   !state.isLoading {
//                                    wrapper.accept(intent: ContentListIntentLoadNext())
//                                }
//                            }
//                    }
//                }
//                .padding()
//            }
//            .navigationTitle(state.topicName)
//            .navigationBarTitleDisplayMode(.inline)
//            .task {
//                await wrapper.activate()
//            }
//            .navigationDestination(for: shared.ContentModel.self) { content in
//                let vm = MviViewModelWrapper(
//                    vm: ViewModelProvider.shared.getContentListViewModel(topicId: , topicName:)
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
//struct ContentListView: View {
//    @StateObject var wrapper: MviViewModelWrapper<ContentListIntent, ContentListState, ContentListSideEffect>
//    var onContentClick: (shared.ContentModel) -> Void
//
//    var body: some View {
//        let state = wrapper.state
//        let items = state.currentPagerState.list.compactMap { $0 as? shared.ContentModel }
//
//        VStack {
//            SearchBarView(
//                text: Binding(
//                    get: { state.searchQuery },
//                    set: { wrapper.accept(intent: ContentListIntentSearch(query: $0)) }
//                )
//            )
//
//            ScrollView {
//                LazyVStack(spacing: 12) {
//                    ForEach(items, id: \.id) { content in
//                        Button {
//                            onContentClick(content)
//                        } label: {
//                            ContentCardView(content: content)
//                        }
//                        .onAppear {
//                            if content.id == items.last?.id,
//                               state.pagerState.hasNextPage,
//                               !state.isLoading {
//                                wrapper.accept(intent: ContentListIntentLoadNext())
//                            }
//                        }
//                    }
//                }
//                .padding()
//            }
//        }
//        .navigationTitle(state.topicName)
//        .navigationBarTitleDisplayMode(.inline)
//        .task {
//            await wrapper.activate()
//        }
//        .navigationDestination(for: shared.ContentModel.self) { content in
//            let vm = MviViewModelWrapper(
//                vm: ViewModelProvider.shared.getReviewListViewModel(contentId: content.id)
//            )
//            ReviewListView(wrapper: vm, contentName: content.name)
//        }
//
//    }
//}

//import SwiftUI
//import shared
//
//struct ContentListView: View {
//    @StateObject var wrapper: MviViewModelWrapper<ContentListIntent, ContentListState, ContentListSideEffect>
//    @State private var path = NavigationPath()
//
//    var body: some View {
//        let state = wrapper.state
//        let contentList = state.currentPagerState.list.compactMap { $0 as? shared.ContentModel }
//
//        NavigationStack(path: $path) {
//            VStack {
//                SearchBarView(
//                    text: Binding(
//                        get: { state.searchQuery },
//                        set: { wrapper.accept(intent: ContentListIntentSearch(query: $0)) }
//                    )
//                )
//                ScrollView {
//                    LazyVStack(spacing: 12) {
//                        ForEach(contentList, id: \.id) { content in
//                            Button {
//                                path.append(content)
//                            } label: {
//                                ContentCardView(content: content)
//                            }
//                            .onAppear {
//                                if content.id == contentList.last?.id,
//                                   state.pagerState.hasNextPage {
//                                    wrapper.accept(intent: ContentListIntentLoadNext())
//                                }
//                            }
//                        }
//                    }
//                    .padding()
//                }
//            }
//            .navigationTitle(state.topicName)
//            .navigationBarTitleDisplayMode(.inline)
//            .task {
//                await wrapper.activate()
//            }
//            .navigationDestination(for: shared.ContentModel.self) { content in
//                let vm = MviViewModelWrapper(
//                    vm: ViewModelProvider.shared.getReviewListViewModel(contentId: content.id)
//                )
//                ReviewListView(wrapper: vm, contentName: content.name)
//            }
//        }
//    }
//}

//import SwiftUI
//import shared
//
//struct ContentListView: View {
//    @StateObject var wrapper: MviViewModelWrapper<ContentListIntent, ContentListState, ContentListSideEffect>
//    @State private var path = NavigationPath()
//
//    var body: some View {
//        let state = wrapper.state
//        let contentItems = state.currentPagerState.list.compactMap { $0 as? shared.ContentModel }
//
//        NavigationStack(path: $path) {
//            VStack {
//                SearchBarView(
//                    text: Binding(
//                        get: { state.searchQuery },
//                        set: { wrapper.accept(intent: ContentListIntentSearch(query: $0)) }
//                    )
//                )
//
//                ScrollView {
//                    LazyVStack(spacing: 12) {
//                        ForEach(contentItems, id: \.id) { content in
//                            Button {
//                                path.append(content)
//                            } label: {
//                                ContentCardView(content: content)
//                            }
//                            .onAppear {
//                                if content.id == contentItems.last?.id,
//                                   state.pagerState.hasNextPage {
//                                    wrapper.accept(intent: ContentListIntentLoadNext())
//                                }
//                            }
//                        }
//                    }
//                    .padding()
//                }
//            }
//            .navigationTitle(state.topicName)
//            .navigationBarTitleDisplayMode(.inline)
//            .task {
//                await wrapper.activate()
//            }
//            .navigationDestination(for: shared.ContentModel.self) { content in
//                let vm = MviViewModelWrapper(
//                    vm: ViewModelProvider.shared.getReviewListViewModel(contentId: content.id)
//                )
//                ReviewListView(wrapper: vm, contentName: content.name)
//            }
//        }
//    }
//}


//import SwiftUI
//import shared
//
//struct ContentListView: View {
//    @StateObject var wrapper: MviViewModelWrapper<ContentListIntent, ContentListState, ContentListSideEffect>
//    var onContentClick: (shared.ContentModel) -> Void
//
//    var body: some View {
//        let state = wrapper.state
//        let list = state.currentPagerState.list.compactMap { $0 as? shared.ContentModel }
//
//        ScrollView {
//            LazyVStack(spacing: 12) {
//                ForEach(list, id: \.id) { content in
//                    Button {
//                        onContentClick(content)
//                    } label: {
//                        ContentCardView(content: content)
//                    }
//                }
//            }
//            .padding()
//        }
//        .navigationTitle(state.topicName)
//        .navigationBarTitleDisplayMode(.inline)
//        .task {
//            await wrapper.activate()
//        }
//    }
//}

//import SwiftUI
//import shared
//
//struct ContentListView: View {
//    @ObservedObject var wrapper: MviViewModelWrapper<ContentListIntent, ContentListState, ContentListSideEffect>
//    var onContentSelected: (shared.ContentModel) -> Void
//
//    var body: some View {
//        let state = wrapper.state
//        let contentList = state.currentPagerState.list.compactMap { $0 as? shared.ContentModel }
//
//        VStack {
//            SearchBarView(
//                text: Binding(
//                    get: { state.searchQuery },
//                    set: { wrapper.accept(intent: ContentListIntentSearch(query: $0)) }
//                )
//            )
//
//            ScrollView {
//                LazyVStack(spacing: 12) {
//                    ForEach(contentList, id: \.id) { content in
//                        Button {
//                            onContentSelected(content)
//                        } label: {
//                            ContentCardView(content: content)
//                        }
//                        .onAppear {
//                            if content.id == contentList.last?.id,
//                               state.currentPagerState.hasNextPage,
//                               !state.isLoading {
//                                wrapper.accept(intent: ContentListIntentLoadNext())
//                            }
//                        }
//                    }
//                }
//                .padding(.horizontal)
//            }
//        }
//        .navigationTitle(state.topicName)
//        .navigationBarTitleDisplayMode(.inline)
//        .task {
//            await wrapper.activate()
//        }
//    }
//}

import SwiftUI
import shared

struct ContentListView: View {
    @StateObject var wrapper: MviViewModelWrapper<ContentListIntent, ContentListState, ContentListSideEffect>
    var onContentSelected: (shared.ContentModel) -> Void

    var body: some View {
        let state = wrapper.state
        let contentList = state.currentPagerState.list.compactMap { $0 as? shared.ContentModel }

        VStack {
            SearchBarView(
                text: Binding(
                    get: { state.searchQuery },
                    set: { wrapper.accept(intent: ContentListIntentSearch(query: $0)) }
                )
            )

            ScrollView {
                LazyVStack(spacing: 12) {
                    ForEach(contentList, id: \.id) { content in
                        Button {
                            onContentSelected(content)
                        } label: {
                            ContentCardView(content: content)
                        }
                        .onAppear {
                            if content.id == contentList.last?.id,
                               state.pagerState.hasNextPage,
                               state.searchQuery.isEmpty {
                                wrapper.accept(intent: ContentListIntentLoadNext())
                            }
                        }
                    }

                    let model = shared.ContentModel(id: 100, themeId: 1, name: "Веном", imageUrl: "https://sun1-27.userapi.com/impg/ScrivahxBfrTHs8fPOO7fwwgFLGJZ-yaLeXEUQ/bM5sZfC-XB0.jpg?size=403x604&quality=95&sign=35301c033ba084f3f0111c3938dcf6c4&type=album", avgMark: 5, reviewCount: 0)
                    ContentCardView(content: model)
                }
                .padding()
            }
        }
        .navigationTitle(state.topicName)
        .navigationBarTitleDisplayMode(.inline)
        .task {
            await wrapper.activate()
        }
    }
}
