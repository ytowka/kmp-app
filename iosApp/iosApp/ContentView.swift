import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()

	var body: some View {
		Text(greet)
	}
}

struct RootScreen: View {
    @StateObject var vm = GenericMviViewModelWrapper(vm: KoinHelper().getRootViewModel())

    var body: some View {
        VStack {
            if let state = vm.state as? RootState.Success {
                Text("User: \(state.currentUser?.name ?? "-")")
            } else {
                Text("Loading...")
            }
        }
        .onAppear {
            vm.sendIntent(RootIntent.Init(isLoggedIn: true, currentUser: nil))
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}