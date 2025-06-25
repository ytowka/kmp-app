import SwiftUI
import shared

struct ProfileHeaderView: View {
    let user: FeatureUserModel

    var body: some View {
        HStack(spacing: 12) {
            AvatarImageView(avatarUrl: user.avatarUrl)
                .frame(width: 56, height: 56)
                .clipShape(Circle())

            VStack(alignment: .leading, spacing: 4) {
                Text("@\(user.login)")
                    .font(.headline)
                    .foregroundColor(.black)
                
                Text(user.fullName)
                    .font(.subheadline)
                    .foregroundColor(.black)
            }

            Spacer()
        }
        .padding(10)
        .background(Color("NavigationBarColor"))
        .frame(maxWidth: .infinity)
    }
}

