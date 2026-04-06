package pl.kosciukw.petsify.shared.data.network

sealed class NetworkState {

    // TODO 11.05.2025 no-op yet
    data object Established : NetworkState()

    data object Disconnected : NetworkState()
}