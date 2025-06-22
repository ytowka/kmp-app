import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()

	var body: some View {
		Text(greet)
	}
}

struct RootScreen: View {
    @StateObject var vm = MviViewModelWrapper(vm: ViewModelProvider.shared.getRootViewModel())

    var body: some View {
        VStack {
            if let state = vm.state as? FeatureRootStateSuccess {
                if let currentUser = state.currentUser {
                    Text("User: \(currentUser.fullName)")
                } else {
                    Text("No user")
                }
            } else {
                Text("Loading...")
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
