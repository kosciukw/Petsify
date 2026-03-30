package pl.kosciukw.petsify.iosapp

import pl.kosciukw.petsify.shared.network.NetworkStateProvider

internal class IosNetworkStateProvider : NetworkStateProvider {
    override fun isInternetConnectionAvailable(): Boolean = true
}
