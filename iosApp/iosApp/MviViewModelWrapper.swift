import shared
import Combine

class BaseViewModel<State: AnyObject, Action: AnyObject, Intent: AnyObject>: ObservableObject {
    private let viewModel: IMviViewModel
    
    @Published var state: State
    
    init(viewModel: IMviViewModel, initialState: State) {
        self.viewModel = viewModel
        state = initialState
    }
    
    @MainActor
    func activate() async {
        for await newState in viewModel.state {
            guard let newCastState = newState as? State else { return }
            state = newCastState
            print("newState: \(newCastState)")
        }
    }
    
    @MainActor
    func activateSideEffectFlow() async {
        for await sideEffect in viewModel.sideEffect {
            guard let sideEffect else { return }
            
            
            print("New action: \(sideEffect)")
        }
    }
    
    func accept(intent: Intent) {
        viewModel.accept(intent: intent)
    }
    
    
}
