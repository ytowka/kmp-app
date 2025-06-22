import SwiftUI
import PhotosUI

struct AvatarPickerView: View {
    @Binding var image: UIImage?
    @Binding var isPickerPresented: Bool

    var body: some View {
        Button {
            isPickerPresented = true
        } label: {
            Group {
                if let avatar = image {
                    Image(uiImage: avatar)
                        .resizable()
                } else {
                    Image(systemName: "person.circle.fill")
                        .resizable()
                        .foregroundColor(.black)
                }
            }
            .aspectRatio(contentMode: .fill)
            .frame(width: 80, height: 80)
            .clipShape(Circle())
        }
        .padding()
    }
}
