import SwiftUI

struct TabBarItemView: View {
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
                            .fill(Color("PrimaryColor"))
                            .matchedGeometryEffect(id: "tabBackground", in: namespace)
                    } else {
                        Color.clear
                    }
                }
            )
            .foregroundColor(isSelected ? .white : .black)
        }
        //.frame(maxWidth: .infinity)
    }

    //    var body: some View {
    //        Button(action: onTap) {
    //            VStack(spacing: 4) {
    //                Image(systemName: tab.systemImage)
    //                    .foregroundColor(isSelected ? .white : .black)
    //                Text(tab.label)
    //                    .font(.caption)
    //                    .foregroundColor(isSelected ? .white : .black)
    //            }
    //            .padding(.horizontal, 16)
    //            .padding(.vertical, 8)
    //            .background(isSelected ? Color("PrimaryColor") : Color.clear)
    //            .cornerRadius(8)
    //        }
    //    }
}

