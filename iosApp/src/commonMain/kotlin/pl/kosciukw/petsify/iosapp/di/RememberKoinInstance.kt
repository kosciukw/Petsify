package pl.kosciukw.petsify.iosapp.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.mp.KoinPlatform

@Composable
internal inline fun <reified T : Any> rememberKoinInstance(): T = remember {
    KoinPlatform.getKoin().get<T>()
}
