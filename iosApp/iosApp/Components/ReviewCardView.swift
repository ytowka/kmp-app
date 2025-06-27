import SwiftUI
import shared

struct ReviewCardView: View {
    let review: ReviewCard

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            HStack {
                AvatarImageView(avatarUrl: review.reviewUserInfo.userAvatarUrl)
                    .frame(width: 40, height: 40)
                    .clipShape(Circle())

                VStack(alignment: .leading, spacing: 2) {
                    Text("@\(review.reviewUserInfo.userName)")
                        .font(.subheadline)
                        .bold()
                    Text("Пишет про \(review.reviewModel.contentName)")
                        .font(.subheadline)
                        .foregroundColor(Color("PrimaryColor"))
                }
                
                Spacer()

                let mark = review.reviewModel.mark
                Text("\(mark)")
                    .font(.headline)
                    .foregroundColor(.white)
                    .frame(width: 40, height: 40)
                    .background(MarkColors.getColor(for: Float(mark)))
                    .clipShape(Circle())
                    .multilineTextAlignment(.center)

            }

            Text(review.reviewModel.text)
                .font(.body)
                .foregroundColor(.primary)
        }
        .padding()
        .background(Color(UIColor.systemGray6))
        .cornerRadius(12)
        .padding(.horizontal)
    }
}

