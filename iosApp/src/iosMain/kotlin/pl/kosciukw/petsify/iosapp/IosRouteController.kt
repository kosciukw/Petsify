package pl.kosciukw.petsify.iosapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import pl.kosciukw.petsify.shared.navigation.AppRoute

internal class IosRouteController(initialRoute: AppRoute = AppRoute.Splash) {
    var currentRoute by mutableStateOf(initialRoute)
        private set

    fun navigateTo(route: AppRoute) {
        currentRoute = route
    }
}
