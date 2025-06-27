//
//  ReviewEditView.swift
//  iosApp
//
//  Created by kerik on 27.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ReviewEditorView: View {
    @StateObject var wrapper: MviViewModelWrapper<EditReviewIntent, EditReviewState, EditReviewSideEffect>
    var onSave: () -> Void
    var onBack: () -> Void

    var body: some View {
        let state = wrapper.state

        VStack {
            ScrollView {
                VStack(spacing: 16) {
                    if let content = state.content {
                        ContentPreview(content: content)
                    }

                    TextField("Текст отзыва", text: Binding(
                        get: { state.text },
                        set: { wrapper.accept(intent: EditReviewIntentEditText(text: $0)) }
                    ))
                    .textFieldStyle(.roundedBorder)

                    MarkPicker(mark: Int(truncating: state.mark ?? 0)) { newMark in
                        wrapper.accept(intent: EditReviewIntentEditMark(mark: Int32(newMark)))
                    }

                    Button("Сохранить") {
                        wrapper.accept(intent: EditReviewIntentSave())
                    }
                    .disabled(!state.isValid)
                    .buttonStyle(.borderedProminent)
                }
                .padding()
            }
        }
        .task {
            await wrapper.activateSideEffects()
        }
        .task {
            await wrapper.activate()
        }
    }
}

