import shared

class GenericMviViewModelWrapper: ObservableObject {
    private let vm: IMviViewModel

    @Published var state: Any?
    @Published var sideEffect: Any?

    private var cancellables = Set<AnyCancellable>()

    init(vm: IMviViewModel) {
        self.vm = vm

        vm.anyState
            .asPublisher()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] state in
                self?.state = state
            }
            .store(in: &cancellables)

        vm.anySideEffect
            .asPublisher()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] effect in
                self?.sideEffect = effect
            }
            .store(in: &cancellables)
    }

    func accept(_ intent: Any) {
        vm.accept(intent: intent)
    }
}