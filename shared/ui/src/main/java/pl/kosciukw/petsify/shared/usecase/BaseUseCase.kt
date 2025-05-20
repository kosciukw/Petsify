package pl.kosciukw.petsify.shared.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class UseCase<out Type, in Params> where Type : Any? {

    abstract fun action(params: Params): Flow<Type>

    fun execute(
        params: Params,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<Type> = flow {
        emitAll(action(params))
    }.flowOn(dispatcher)

    operator fun invoke(
        params: Params,
        viewModelScope: CoroutineScope,
        executionDispatcher: CoroutineDispatcher = Dispatchers.IO,
        onResult: (Result<Type>) -> Unit = {}
    ): Job = viewModelScope.launch {
        execute(params, executionDispatcher)
            .catch { e -> onResult(Result.failure(e)) }
            .collect { data -> onResult(Result.success(data)) }
    }
}