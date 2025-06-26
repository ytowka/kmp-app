//
//  TopicView.swift
//  iosApp
//
//  Created by kerik on 26.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TopicView: View {
    @StateObject var wrapper: MviViewModelWrapper<TopicListIntent, TopicListState, TopicSideEffect>

    var body: some View {
        let state = wrapper.state

        ScrollView {
            VStack(alignment: .leading, spacing: 24) {
                ForEach(state.topics, id: \.id) { topic in
                    TopicCardView(topic: topic)
                }
            }
            .padding(.top)
        }
        .background(Color.clear)
        .navigationBarModifier(title: "Темы")
        .task {
            await wrapper.activate()
        }
    }
}
