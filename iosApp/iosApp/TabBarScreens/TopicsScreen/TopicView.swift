//
//  TopicView.swift
//  iosApp
//
//  Created by kerik on 26.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

//import SwiftUI
//import shared
//
//struct TopicView: View {
//    @StateObject var wrapper: MviViewModelWrapper<TopicListIntent, TopicListState, TopicSideEffect>
//
//    var body: some View {
//        let state = wrapper.state
//
//        ScrollView {
//            VStack(alignment: .leading, spacing: 24) {
//                ForEach(state.topics, id: \.id) { topic in
//                    TopicCardView(topic: topic)
//                }
//            }
//            .padding(.top)
//        }
//        .background(Color.clear)
//        .navigationBarModifier(title: "Темы")
//        .task {
//            await wrapper.activate()
//        }
//    }
//}

//import SwiftUI
//import shared
//
//struct TopicView: View {
//    @StateObject var wrapper: MviViewModelWrapper<TopicListIntent, TopicListState, TopicSideEffect>
//    @State private var path = NavigationPath()
//
//    var body: some View {
//        let state = wrapper.state
//        let topics = wrapper.state.topics
//
//        NavigationStack(path: $path) {
//            ScrollView {
//                LazyVStack(alignment: .leading, spacing: 24) {
//                    ForEach(topics, id: \.id) { topic in
//
//                    }
//                }
//                .padding(.top)
//            }
//            .background(Color.clear)
//            .navigationBarModifier(title: "Темы")
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
//struct TopicView: View {
//    @StateObject var wrapper: MviViewModelWrapper<TopicListIntent, TopicListState, TopicSideEffect>
//    @State private var path = NavigationPath()
//
//    var body: some View {
//        let state = wrapper.state
//
//        NavigationStack(path: $path) {
//            ScrollView {
//                LazyVStack(alignment: .leading, spacing: 16) {
//                    ForEach(state.topics, id: \.id) { topic in
//                        Button {
//                            path.append(topic)
//                        } label: {
//                            TopicCardView(topic: topic)
//                        }
//                    }
//                }
//                .padding()
//            }
//            .navigationTitle("Темы")
//            .navigationBarTitleDisplayMode(.inline)
//            .task {
//                await wrapper.activate()
//            }
//            .navigationDestination(for: shared.TopicModel.self) { topic in
//                let vm = MviViewModelWrapper(
//                    vm: ViewModelProvider.shared.getContentListViewModel(
//                        topicId: topic.id,
//                        topicName: topic.name
//                    )
//                )
//                ContentListView(wrapper: vm)
//            }
//        }
//    }
//}
//import SwiftUI
//import shared
//
//enum TopicNavigation: Hashable {
//    case contentList(topicId: Int64, topicName: String)
//    case reviewList(contentId: Int64, contentName: String)
//}
//
//import SwiftUI
//import shared
//
//struct TopicView: View {
//    @StateObject var wrapper: MviViewModelWrapper<TopicListIntent, TopicListState, TopicSideEffect>
//    @State private var path = NavigationPath()
//
//    var body: some View {
//        let topics = wrapper.state.topics
//
//        NavigationStack(path: $path) {
//            ScrollView {
//                LazyVStack(alignment: .leading, spacing: 24) {
//                    ForEach(topics, id: \.id) { topic in
//                        Button {
//                            path.append(TopicNavigation.contentList(topicId: topic.id, topicName: topic.name))
//                        } label: {
//                            TopicCardView(topic: topic)
//                                .padding(.horizontal)
//                        }
//                    }
//                }
//                .padding(.top)
//            }
//            .navigationTitle("Темы")
//            .navigationBarTitleDisplayMode(.inline)
//            .task {
//                await wrapper.activate()
//            }
//            .navigationDestination(for: TopicNavigation.self) { destination in
//                switch destination {
//                case let .contentList(topicId, topicName):
//                    let vm = MviViewModelWrapper(
//                        vm: ViewModelProvider.shared.getContentListViewModel(
//                            topicId: topicId,
//                            topicName: topicName
//                        )
//                    )
//                    ContentListView(wrapper: vm) { content in
//                        path.append(TopicNavigation.reviewList(contentId: content.id, contentName: content.name))
//                    }
//
//                case let .reviewList(contentId, contentName):
//                    let vm = MviViewModelWrapper(
//                        vm: ViewModelProvider.shared.getReviewListViewModel(contentId: contentId)
//                    )
//                    ReviewListView(wrapper: vm, contentName: contentName)
//                }
//            }
//        }
//    }
//}

//import SwiftUI
//import shared
//
//struct TopicView: View {
//    @ObservedObject var wrapper: MviViewModelWrapper<TopicListIntent, TopicListState, TopicSideEffect>
//    var navigateToContentList: (Int64, String) -> Void
//
//    var body: some View {
//        let state = wrapper.state
//
//        ScrollView {
//            LazyVStack(alignment: .leading, spacing: 24) {
//                ForEach(state.topics, id: \.id) { topic in
//                    Button {
//                        navigateToContentList(topic.id, topic.name)
//                    } label: {
//                        TopicCardView(topic: topic)
//                    }
//                }
//            }
//            .padding(.top)
//        }
//        .navigationBarModifier(title: "Темы")
////        .navigationTitle("Темы")
////        .navigationBarTitleDisplayMode(.inline)
//        .task {
//            await wrapper.activate()
//        }
//    }
//}

import SwiftUI
import shared

struct TopicView: View {
    @StateObject var wrapper: MviViewModelWrapper<TopicListIntent, TopicListState, TopicSideEffect>

    let onTopicSelected: (TopicModel) -> Void

    var body: some View {
        let state = wrapper.state

        ScrollView {
            LazyVStack(alignment: .leading, spacing: 24) {
                ForEach(state.topics, id: \.id) { topic in
                    Button {
                        onTopicSelected(topic)
                    } label: {
                        TopicCardView(topic: topic)
                    }
                }
            }
            .padding()
        }
        .navigationBarTitleDisplayMode(.inline)
        .navigationTitle("Темы")
        .task {
            await wrapper.activate()
        }
    }
}
