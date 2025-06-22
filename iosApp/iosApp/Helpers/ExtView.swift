import SwiftUI

extension View {
    func navigationBarModifier(title: String) -> some View {
        self
            //.toolbarBackgroundVisibility(.visible, for: .navigationBar)
            .toolbarBackground(Color("NavigationBarColor"), for: .navigationBar)
            .navigationBarTitleDisplayMode(.inline)
            .navigationTitle(title)
    }
}

