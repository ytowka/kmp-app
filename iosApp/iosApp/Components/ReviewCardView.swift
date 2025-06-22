import SwiftUI

struct ReviewCardView: View {
    let review: ReviewUIModel

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            HStack {
                AvatarImageView(avatarUrl: review.userAvatarUrl)
                    .frame(width: 40, height: 40)
                    .clipShape(Circle())

                VStack(alignment: .leading, spacing: 2) {
                    Text("@\(review.username)")
                        .font(.subheadline)
                        .bold()
                    Text("Пишет про \(review.movieTitle)")
                        .font(.subheadline)
                        .foregroundColor(Color("PrimaryColor"))
                }
                
                Spacer()

                Text("\(Int(review.rating))")
                    .font(.headline)
                    .foregroundColor(.white)
                    .padding(8)
                    .background(Color.yellow)
                    .clipShape(Circle())
            }

            Text(review.text)
                .font(.body)
                .foregroundColor(.primary)
        }
        .padding()
        .background(Color(UIColor.systemGray6))
        .cornerRadius(12)
        .padding(.horizontal)
    }
}

