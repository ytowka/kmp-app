//
//  AuthView.swift
//  iosApp
//
//  Created by Alfa on 22.06.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared


struct AuthView: View {
    @StateObject var vm = MviViewModelWrapper(vm: ViewModelProvider.shared.getAuthViewModel())
    
    var body: some View {
        ZStack {
            switch vm.state.currentForm {
            case .login:
                LoginView(vm: vm)
            case .registration:
                RegistrationView(vm: vm)                    
            }
        }
        .task {
            await vm.activate()
        }
        
    }
}
