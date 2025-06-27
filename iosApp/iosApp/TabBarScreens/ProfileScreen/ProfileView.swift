import SwiftUI

struct ProfileView: View {
    @StateObject private var viewModel: ProfileViewModel

    init(user: UserModel) {
        _viewModel = StateObject(wrappedValue: ProfileViewModel(user: user))
    }

    var body: some View {
        VStack {
//            ProfileHeaderView(user: viewModel.user)

//            ScrollView {
//                VStack(spacing: 12) {
//                    ForEach(viewModel.reviews) { review in
//                        ReviewCardView(review: review)
//                    }
//                }
//                .padding(.top)
//            }
        }
        .navigationBarModifier(title: "Профиль пользователя")
    }
}
