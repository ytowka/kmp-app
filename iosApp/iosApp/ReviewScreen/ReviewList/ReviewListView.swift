//
//  ReviewListView.swift
//  iosApp
//
//  Created by kerik on 27.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

//import SwiftUI
//import shared
//
//struct ReviewListView: View {
//    @StateObject var wrapper: MviViewModelWrapper<ReviewListIntent, ReviewListState, ReviewListSideEffect>
//    let contentName: String
//
//    var body: some View {
//        let state = wrapper.state
//        let reviews = state.listState.list.compactMap { $0 as? ReviewCard }
//
//        VStack(spacing: 0) {
//            Text(contentName)
//                .font(.title2.bold())
//                .frame(maxWidth: .infinity, alignment: .leading)
//                .padding()
//
//            ScrollView {
//                LazyVStack(spacing: 12) {
//                    ForEach(reviews, id: \.reviewModel.id) { review in
//                        ReviewCardView(review: review)
//                            .onAppear {
//                                if review.reviewModel.id == reviews.last?.reviewModel.id,
//                                   state.listState.hasNextPage {
//                                    wrapper.accept(intent: ReviewListIntentLoadNext())
//                                }
//                            }
//                    }
//                }
//                .padding(.horizontal)
//            }
//        }
//        .navigationBarTitleDisplayMode(.inline)
//        .navigationTitle("\(contentName)")
//        .task {
//            await wrapper.activate()
//        }
//    }
//}

//import SwiftUI
//import shared
//
//struct ReviewListView: View {
//    @StateObject var wrapper: MviViewModelWrapper<ReviewListIntent, ReviewListState, ReviewListSideEffect>
//    let contentName: String
//
//    var body: some View {
//        let state = wrapper.state
//        let reviews = state.listState.list.compactMap { $0 as? ReviewCard }
//
//        ScrollView {
//            LazyVStack(spacing: 12) {
//                ForEach(reviews, id: \.reviewModel.id) { review in
//                    ReviewCardView(review: review)
//                        .onAppear {
//                            if review.reviewModel.id == state.listState.list.last?.reviewModel.id,
//                               state.listState.hasNextPage {
//                                wrapper.accept(intent: ReviewListIntentLoadNext())
//                            }
//                        }
//                }
//            }
//            .padding()
//        }
//        .navigationTitle("Отзывы")
//        .navigationBarTitleDisplayMode(.inline)
//        .task {
//            await wrapper.activate()
//        }
//    }
//}

//import SwiftUI
//import shared
//
//struct ReviewListView: View {
//    @StateObject var wrapper: MviViewModelWrapper<ReviewListIntent, ReviewListState, ReviewListSideEffect>
//    let contentName: String
//
//    var body: some View {
//        let state = wrapper.state
//        let reviews = state.listState.list.compactMap { $0 as? ReviewCard }
//
//        ScrollView {
//            LazyVStack(spacing: 12) {
//                ForEach(reviews, id: \.reviewModel.id) { review in
//                    ReviewCardView(review: review)
//                        .onAppear {
//                            if review.reviewModel.id == reviews.last?.reviewModel.id,
//                               state.listState.hasNextPage {
//                                wrapper.accept(intent: ReviewListIntentLoadNext())
//                            }
//                        }
//                }
//            }
//            .padding()
//        }
//        .navigationTitle(contentName)
//        .navigationBarTitleDisplayMode(.inline)
//        .task {
//            await wrapper.activate()
//        }
//    }
//}

import SwiftUI
import shared

struct ReviewListView: View {
    @EnvironmentObject var router: AppRouter
    @StateObject var wrapper: MviViewModelWrapper<ReviewListIntent, ReviewListState, ReviewListSideEffect>
    let contentName: String

    var body: some View {
        let state = wrapper.state
        let _ = print(state.contentId)
        let rawList = state.listState.list
        let reviews: [ReviewCard] = rawList.compactMap { $0 as? ReviewCard }

        VStack {
            ScrollView {
                LazyVStack(spacing: 12) {
                    ForEach(reviews.indices, id: \.self) { index in
                        let review = reviews[index]

                        ReviewCardView(review: review)
                            .onAppear {
                                let isLast = index == reviews.count - 1
                                let hasNext = state.listState.hasNextPage

                                if isLast && hasNext {
                                    wrapper.accept(intent: ReviewListIntentLoadNext())
                                }
                            }
                    }
                }
                .padding()
            }

            PrimaryButton(title: "Оставить отзыв") {
                router.push(.reviewEditor(contentId: state.contentId))
            }
        }
        .navigationBarModifier(title: contentName)
        .task {
            await wrapper.activate()
        }
    }
}
