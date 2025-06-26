//
//  UserInfoView.swift
//  iosApp
//
//  Created by kerik on 25.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

//import SwiftUI
//import shared
//
//struct UserInfoView: View {
//    @StateObject var wrapper: MviViewModelWrapper<UserInfoIntent, UserInfoState, UserInfoSideEffect>
//
//    var body: some View {
//        let state = wrapper.state
//
//        VStack {
//            Group {
//                if let user = state.userModel {
//                    ProfileHeaderView(user: user)
//                } else {
//                    ProgressView()
//                        .padding()
//                }
//            }
//
//            Spacer()
//            //                VStack(spacing: 12) {
//            //                    ForEach(state.reviewListState.list, id: \.reviewModel.id) { review in
//            //                        ReviewCardView(review: review)
//            //                            .onAppear {
//            //                                if review == state.reviewListState.list.last {
//            //                                    wrapper.accept(intent: FeatureUserInfoIntent.LoadNext())
//            //                                }
//            //                            }
//            //                    }
//            //                }
//            //                .padding(.horizontal)
//        }
//        .navigationBarModifier(title: "Профиль пользователя")
//        .task {
//            await wrapper.activate()
//        }
//    }
//}
import SwiftUI
import shared

struct UserInfoView: View {
    @StateObject var wrapper: MviViewModelWrapper<UserInfoIntent, UserInfoState, UserInfoSideEffect>
    var initialUserModel: shared.UserModel?

    var body: some View {
        let state = wrapper.state
        let user = state.userModel ?? initialUserModel

        VStack {
            if let user {
                ProfileHeaderView(user: user)
            }

            Spacer()
        }
        .navigationBarModifier(title: "Профиль пользователя")
        .task {
            await wrapper.activate()
        }
    }
}
