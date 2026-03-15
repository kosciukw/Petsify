package pl.kosciukw.petsify.shared.callback

import pl.kosciukw.petsify.shared.mapper.ApiToDomainErrorMapper

suspend fun <T> mapResult(
    errorMapper: ApiToDomainErrorMapper,
    apiCall: suspend () -> T
): T {
    return runCatching {
        apiCall()
    }.getOrElse {
        throw errorMapper.map(it)
    }
}