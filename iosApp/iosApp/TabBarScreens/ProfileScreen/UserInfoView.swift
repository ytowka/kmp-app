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
    @StateObject var wrapper: MviViewModelWrapper<FeatureUserInfoIntent, FeatureUserInfoState, FeatureUserInfoSideEffect>

    var body: some View {
        let state = wrapper.state

        VStack {
            Group {
                if let user = state.userModel {
                    ProfileHeaderView(user: user)
                }
            }

            Spacer()
            //                VStack(spacing: 12) {
            //                    ForEach(state.reviewListState.list, id: \.reviewModel.id) { review in
            //                        ReviewCardView(review: review)
            //                            .onAppear {
            //                                if review == state.reviewListState.list.last {
            //                                    wrapper.accept(intent: FeatureUserInfoIntent.LoadNext())
            //                                }
            //                            }
            //                    }
            //                }
            //                .padding(.horizontal)
        }
        .navigationBarModifier(title: "Профиль пользователя")
        .task {
            await wrapper.activate()
        }
    }
}
