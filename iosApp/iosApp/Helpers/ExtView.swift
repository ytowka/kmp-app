import SwiftUI

extension View {
    func navigationBarModifier(title: String) -> some View {
        self
            .navigationBarTitleDisplayMode(.inline)
            .navigationTitle(title)
    }
}

