//import SwiftUI
//
//struct CachedAsyncImage: View {
//    let url: URL?
//    let placeholder: () -> AnyView
//    let contentMode: ContentMode
//
//    @State private var image: UIImage?
//
//    var body: some View {
//        Group {
//            if let uiImage = image {
//                Image(uiImage: uiImage)
//                    .resizable()
//                    .aspectRatio(contentMode: contentMode)
//            } else {
//                placeholder()
//                    .onAppear {
//                        loadImage()
//                    }
//            }
//        }
//    }
//
//    private func loadImage() {
//        guard let url else { return }
//
//        let key = url.absoluteString as NSString
//
//        if let cached = ImageCache.shared.object(forKey: key) {
//            self.image = cached
//            return
//        }
//
//        // Загрузка из сети
//        URLSession.shared.dataTask(with: url) { data, _, _ in
//            if let data = data, let uiImage = UIImage(data: data) {
//                ImageCache.shared.setObject(uiImage, forKey: key)
//                DispatchQueue.main.async {
//                    self.image = uiImage
//                }
//            }
//        }.resume()
//    }
//}
//

//import SwiftUI
//
//struct CachedAsyncImage: View {
//    let url: URL?
//    let placeholder: () -> AnyView
//    let errorPlaceholder: () -> ErrorPlaceholderImageView
//    let contentMode: ContentMode
//
//    @State private var image: UIImage?
//    @State private var loadFailed = false
//
//    var body: some View {
//        Group {
//            if let uiImage = image {
//                Image(uiImage: uiImage)
//                    .resizable()
//                    .aspectRatio(contentMode: contentMode)
//                    .clipped()
//            } else if loadFailed {
//                errorPlaceholder()
//            } else {
//                placeholder()
//                    .onAppear(perform: loadImage)
//            }
//        }
//    }
//
//    private func loadImage() {
//        guard let url else {
//            loadFailed = true
//            return
//        }
//
//        let key = url.absoluteString as NSString
//
//        if let cached = ImageCache.shared.object(forKey: key) {
//            self.image = cached
//            return
//        }
//
//        URLSession.shared.dataTask(with: url) { data, _, error in
//            if let data = data, let uiImage = UIImage(data: data) {
//                ImageCache.shared.setObject(uiImage, forKey: key)
//                DispatchQueue.main.async {
//                    self.image = uiImage
//                }
//            } else {
//                DispatchQueue.main.async {
//                    self.loadFailed = true
//                }
//            }
//        }.resume()
//    }
//}

import SwiftUI

struct CachedAsyncImage: View {
    let url: URL?
    let placeholder: () -> AnyView
    let errorPlaceholder: () -> ErrorPlaceholderImageView
    let contentMode: ContentMode

    @State private var image: UIImage?
    @State private var loadFailed = false
    @State private var isLoading = false

    var body: some View {
        Group {
            if let uiImage = image {
                Image(uiImage: uiImage)
                    .resizable()
                    .aspectRatio(contentMode: contentMode)
                    .transition(.opacity)
            } else if loadFailed {
                errorPlaceholder()
            } else {
                placeholder()
                    .onAppear {
                        if !isLoading { loadImage() }
                    }
            }
        }
        .animation(.easeInOut(duration: 0.2), value: image)
    }

    private func loadImage() {
        guard let url else {
            loadFailed = true
            return
        }

        isLoading = true
        let key = url.absoluteString as NSString

        if let cached = ImageCache.shared.object(forKey: key) {
            self.image = cached
            return
        }

        let request = URLRequest(url: url, cachePolicy: .returnCacheDataElseLoad, timeoutInterval: 15)

        URLSession.shared.dataTask(with: request) { data, _, error in
            DispatchQueue.main.async {
                self.isLoading = false
            }

            if let data, let uiImage = UIImage(data: data) {
                ImageCache.shared.setObject(uiImage, forKey: key)
                DispatchQueue.main.async {
                    self.image = uiImage
                }
            } else {
                DispatchQueue.main.async {
                    self.loadFailed = true
                }
            }
        }.resume()
    }
}

