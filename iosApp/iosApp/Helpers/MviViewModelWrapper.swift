//
//  MviViewModelWrapper.swift
//  iosApp
//
//  Created by Alfa on 22.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared

class MviViewModelWrapper<Intent: AnyObject, State: AnyObject, SideEffect: AnyObject>: ObservableObject {

    private let vm: MviViewModel<Intent, State, SideEffect>

    @Published var state: State

    init(vm: MviViewModel<Intent, State, SideEffect>) {
        self.vm = vm
        state = vm.initialState!
    }

    @MainActor
    func activate() async {
        for await state in vm.state {
            self.state = state!
        }
    }

    @MainActor
    func activateSideEffects() async {
        for await sideEffect in vm.sideEffect {

        }
    }

    @MainActor
    func activateSideEffects(_ handler: @escaping (SideEffect) -> Void) async {
        for await sideEffect in vm.sideEffect {
            if let sideEffect = sideEffect {
                handler(sideEffect)
            }
        }
    }

    func accept(intent: Intent) {
        vm.accept(intent: intent)
    }
}
