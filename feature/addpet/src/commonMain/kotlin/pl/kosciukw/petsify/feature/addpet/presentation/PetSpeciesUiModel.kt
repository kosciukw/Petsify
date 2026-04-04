package pl.kosciukw.petsify.feature.addpet.presentation

data class PetSpeciesUiModel(
    val code: String,
    val label: String,
    val isPrimary: Boolean,
    val requiresCustomValue: Boolean = false
)

class PetSpeciesUiModelProvider {

    fun getSpecies(): List<PetSpeciesUiModel> = listOf(
        PetSpeciesUiModel(
            code = DOG_CODE,
            label = "Dog",
            isPrimary = true
        ),
        PetSpeciesUiModel(
            code = CAT_CODE,
            label = "Cat",
            isPrimary = true
        ),
        PetSpeciesUiModel(
            code = RABBIT_CODE,
            label = "Rabbit",
            isPrimary = false
        ),
        PetSpeciesUiModel(
            code = HAMSTER_CODE,
            label = "Hamster",
            isPrimary = false
        ),
        PetSpeciesUiModel(
            code = BIRD_CODE,
            label = "Bird",
            isPrimary = false
        ),
        PetSpeciesUiModel(
            code = FISH_CODE,
            label = "Fish",
            isPrimary = false
        ),
        PetSpeciesUiModel(
            code = TURTLE_CODE,
            label = "Turtle",
            isPrimary = false
        ),
        PetSpeciesUiModel(
            code = OTHER_CODE,
            label = "Other",
            isPrimary = false,
            requiresCustomValue = true
        )
    )

    companion object {
        const val DOG_CODE = "dog"
        const val CAT_CODE = "cat"
        const val RABBIT_CODE = "rabbit"
        const val HAMSTER_CODE = "hamster"
        const val BIRD_CODE = "bird"
        const val FISH_CODE = "fish"
        const val TURTLE_CODE = "turtle"
        const val OTHER_CODE = "other"
    }
}
