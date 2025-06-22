import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()

	var body: some View {
		Text(greet)
	}
}

struct RootScreen: View {
    @StateObject var viewModel = BaseViewModel<RootState, RootSideEffect, RootIntent>(viewModel: ViewModelProvider.shared.getRootViewModel(), initialState: RootStateLoading() as RootState)

    var body: some View {
        VStack {
            if let state = viewModel.state as? RootStateSuccess {
                Text("User: \(state.currentUser?.fullName ?? "-")")
            } else {
                Text("Loading...")
            }
        }
        .onAppear {
            viewModel.accept(RootIntentInit(isLoggedIn: true, currentUser: nil))
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
