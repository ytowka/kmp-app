import SwiftUI

struct UserRowView: View {
    let user: UserModel

    var body: some View {
        HStack(spacing: 12) {
            AvatarImageView(avatarUrl: user.avatarUrl)
                .frame(width: 48, height: 48)
                .clipShape(Circle())

            VStack(alignment: .leading, spacing: 4) {
                Text("@\(user.username)")
                    .font(.subheadline)
                    .fontWeight(.medium)

                Text(user.id)
                    .font(.caption)
                    .foregroundColor(.gray)
                    .lineLimit(1)
                    .truncationMode(.middle)
            }

            Spacer()
        }
        .padding()
        .background(Color(UIColor.systemGray6))
        .cornerRadius(12)
    }
}

