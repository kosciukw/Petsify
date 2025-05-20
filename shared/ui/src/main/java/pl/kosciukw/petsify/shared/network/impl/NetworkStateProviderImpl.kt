package pl.kosciukw.petsify.shared.network.impl

import pl.kosciukw.petsify.shared.network.NetworkStateProvider

class NetworkStateProviderImpl : NetworkStateProvider {
    
    override fun isInternetConnectionAvailable() = runCatching {
        val ipProcess = Runtime.getRuntime()
            .exec(PING_COMMAND)
        val exitValue = ipProcess.waitFor()
        exitValue == 0
    }.getOrDefault(false)
    
    companion object {
        const val PING_COMMAND = "/system/bin/ping -c 1 8.8.8.8"
    }
}