import SwiftUI

struct ProfileHeaderView: View {
    let user: UserModel

    var body: some View {
        HStack(spacing: 12) {
            AvatarImageView(avatarUrl: user.avatarUrl)
                .frame(width: 56, height: 56)
                .clipShape(Circle())

            VStack(alignment: .leading, spacing: 4) {
                Text("@\(user.username)")
                    .font(.headline)
                    .foregroundColor(.black)
                if let fullName = user.fullName {
                    Text(fullName)
                        .font(.subheadline)
                        .foregroundColor(.black)
                }
            }

            Spacer()
        }
        .padding(10)
        .background(Color("NavigationBarColor"))
        .frame(maxWidth: .infinity)
    }
}

