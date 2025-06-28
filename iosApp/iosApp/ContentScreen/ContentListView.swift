import SwiftUI
import shared

struct ContentListView: View {
    @StateObject var wrapper: MviViewModelWrapper<ContentListIntent, ContentListState, ContentListSideEffect>
    var onContentSelected: (ContentModel) -> Void

    var body: some View {
        let state = wrapper.state
        let contentList = state.currentPagerState.list.compactMap { $0 as? ContentModel }

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
                }
                .padding()
            }
        }
        .navigationBarModifier(title: "\(state.topicName)")
        .task {
            await wrapper.activate()
        }
    }
}
