package pl.kosciukw.petsify.shared.mapper

interface ModelMapper<Input, Result> {
    
    fun map(input: Input): Result
}
