import SwiftUI
import PetsifyIosApp

struct ContentView: View {
    var body: some View {
        ComposeRootView()
            .ignoresSafeArea(.all)
    }
}

private struct ComposeRootView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
