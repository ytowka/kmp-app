//
//  UserInfoView.swift
//  iosApp
//
//  Created by kerik on 25.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct UserInfoView: View {
    @EnvironmentObject var router: AppRouter
    @StateObject var wrapper: MviViewModelWrapper<UserInfoIntent, UserInfoState, UserInfoSideEffect>
    var initialUserModel: UserModel?

    var body: some View {
        let state = wrapper.state
        let user = state.userModel ?? initialUserModel

        let allReviews = state.reviewListState.list
        let reviews: [ReviewCard] = allReviews.compactMap { $0 as? ReviewCard }

        VStack {
            if let user {
                ProfileHeaderView(user: user)
            }

            ScrollView {
                LazyVStack(spacing: 12) {
                    ForEach(reviews, id: \.reviewModel.id) { review in
                        ReviewCardView(
                            review: review,
                            showContentInfo: true,
                            onContentTapped: { contentId, contentName in
                                router.push(.reviewList(contentId: contentId, contentName: contentName))
                            }
                        )
                        .onAppear {
                            if review.reviewModel.id == reviews.last?.reviewModel.id,
                               state.reviewListState.hasNextPage {
                                wrapper.accept(intent: UserInfoIntentLoadNext())
                            }
                        }
                    }
                }
            }
        }
        .navigationBarModifier(title: "Профиль пользователя")
        .task {
            await wrapper.activate()
        }
    }
}

