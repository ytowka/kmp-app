import SwiftUI
import shared

struct ReviewCardView: View {
    let review: ReviewCard
    var showContentInfo: Bool = false
    var onContentTapped: ((Int64, String) -> Void)? = nil

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
                    if showContentInfo {
                        Text("Пишет про \(review.reviewModel.contentName)")
                            .font(.subheadline)
                            .foregroundColor(Color("PrimaryColor"))
                            .onTapGesture {
                                onContentTapped?(review.reviewModel.contentId, review.reviewModel.contentName)
                            }
                    }
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

