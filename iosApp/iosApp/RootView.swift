import SwiftUI
import shared

struct RootScreen: View {
    @StateObject var vm = MviViewModelWrapper(vm: ViewModelProvider.shared.getRootViewModel())
    

    var body: some View {
        ZStack {
            if let successState = vm.state as? RootStateSuccess {
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
