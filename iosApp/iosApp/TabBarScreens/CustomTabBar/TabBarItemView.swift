import SwiftUI

struct TabBarItemView: View {
    @Environment(\.colorScheme) private var colorScheme
    let tab: TabItem
    let isSelected: Bool
    let namespace: Namespace.ID
    let onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            VStack(spacing: 4) {
                Image(systemName: tab.systemImage)
                Text(tab.label)
                    .font(.caption)
            }
            .padding(.horizontal, 12)
            .padding(.vertical, 8)
            .background(
                Group {
                    if isSelected {
                        RoundedRectangle(cornerRadius: 8)
                            .fill(Color("TabBarSelectedColor"))
                            .matchedGeometryEffect(id: "tabBackground", in: namespace)
                    } else {
                        Color.clear
                    }
                }
            )
            .foregroundColor(isSelected ? Color("SelectedColor") : Color("ReversedSelectedColor"))
        }
    }
}

