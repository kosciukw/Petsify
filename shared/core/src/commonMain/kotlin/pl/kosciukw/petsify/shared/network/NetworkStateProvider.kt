package pl.kosciukw.petsify.shared.network

interface NetworkStateProvider {

    fun isInternetConnectionAvailable(): Boolean
}