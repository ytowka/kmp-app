import SwiftUI
import shared

struct ReviewEditorView: View {
    @EnvironmentObject var router: AppRouter
    @StateObject var wrapper: MviViewModelWrapper<EditReviewIntent, EditReviewState, EditReviewSideEffect>

    var onSave: () -> Void
    var onBack: () -> Void

    @Environment(\.dismiss) var dismiss

    var body: some View {
        let state = wrapper.state

        VStack {
            ScrollView {
                VStack(spacing: 16) {
                    if let content = state.content {
                        ReviewHeaderView(content: content)
                    }

                    ReviewTextInputView(
                        text: Binding(
                            get: { state.text },
                            set: { wrapper.accept(intent: EditReviewIntentEditText(text: $0)) }
                        )
                    )

                    StarRatingView(
                        rating: Binding(
                            get: { Int(truncating: state.mark ?? 0) },
                            set: { wrapper.accept(intent: EditReviewIntentEditMark(mark: Int32($0))) }
                        )
                    )

                    PrimaryButton(title: "Сохранить") {
                        wrapper.accept(intent: EditReviewIntentSave())
                    }
                }
                .padding()
            }
        }
        .navigationBarModifier(title: "Редактировать отзыв")
        .toolbar {
            if case is EditReviewModeEdit = state.mode {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(role: .destructive) {
                        wrapper.accept(intent: EditReviewIntentDelete())
                    } label: {
                        Image(systemName: "trash")
                            .foregroundStyle(Color.black)
                    }
                }
            }
        }
        .task {
            await wrapper.activateSideEffects { sideEffect in
                if sideEffect is EditReviewSideEffectReviewUpdated {
                    onSave()
                }
            }
        }
        .task {
            await wrapper.activate()
        }
    }
}
