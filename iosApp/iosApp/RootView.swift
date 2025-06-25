import SwiftUI
import shared

struct RootView: View {
	let greet = Greeting().greet()

	var body: some View {
		Text(greet)
	}
}

struct RootScreen: View {
    @StateObject var vm = MviViewModelWrapper(vm: ViewModelProvider.shared.getRootViewModel())
    

    var body: some View {
        ZStack {
            if let successState = vm.state as? FeatureRootStateSuccess {
                let isLoggedIn: Bool = successState.isLoggedIn
                NavigationView {
                    if isLoggedIn {
                        MainTabView()
                    } else {
                        AuthView()
                    }
                }
            }
        }
        .task {
            await vm.activateSideEffects()
        }
        .task {
            await vm.activate()
        }
    }
}
