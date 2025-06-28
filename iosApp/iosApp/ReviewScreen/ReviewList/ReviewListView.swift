//
//  ReviewListView.swift
//  iosApp
//
//  Created by kerik on 27.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ReviewListView: View {
    @EnvironmentObject var router: AppRouter
    @StateObject var wrapper: MviViewModelWrapper<ReviewListIntent, ReviewListState, ReviewListSideEffect>
    let contentName: String
    let contentId: Int64

    var body: some View {
        let state = wrapper.state
        let reviews = state.listState.list.compactMap { $0 as? ReviewCard }

        VStack {
            ScrollView {
                LazyVStack(spacing: 12) {
                    ForEach(reviews.indices, id: \.self) { index in
                        let review = reviews[index]
                        ReviewCardView(review: review)
                            .id(review.reviewModel.id)
                            .onAppear {
                                if index == reviews.count - 1, state.listState.hasNextPage {
                                    wrapper.accept(intent: ReviewListIntentLoadNext())
                                }
                            }
                            .onTapGesture {
                                let userId = review.reviewUserInfo.userId
                                router.push(.userProfile(userId: userId))
                            }

                    }
                }.padding()
            }

            PrimaryButton(title: "Оставить отзыв") {
                let contentId = reviews.first?.reviewModel.contentId ?? 0
                router.push(.reviewEditor(contentId: contentId))
            }
        }
        .navigationBarModifier(title: contentName)
        .task { await wrapper.activate() }
    }
}
