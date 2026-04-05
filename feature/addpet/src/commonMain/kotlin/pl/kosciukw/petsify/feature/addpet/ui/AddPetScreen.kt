package pl.kosciukw.petsify.feature.addpet.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import pl.kosciukw.petsify.feature.addpet.model.domain.PetSpeciesDomainType
import pl.kosciukw.petsify.feature.addpet.model.domain.PetWeightUnitDomainType
import pl.kosciukw.petsify.feature.addpet.model.ui.PetSexUiModel
import pl.kosciukw.petsify.feature.addpet.model.ui.PetSpeciesUiModel
import pl.kosciukw.petsify.feature.addpet.model.ui.PetTemperamentUiModel
import pl.kosciukw.petsify.feature.addpet.model.ui.PetWeightUnitUiModel
import pl.kosciukw.petsify.feature.addpet.presentation.AddPetAction
import pl.kosciukw.petsify.feature.addpet.presentation.AddPetEvent
import pl.kosciukw.petsify.feature.addpet.presentation.AddPetState
import pl.kosciukw.petsify.shared.strings.AddPetStrings
import pl.kosciukw.petsify.shared.strings.CommonScreenStrings
import pl.kosciukw.petsify.shared.ui.theme.BlackLiquorice
import pl.kosciukw.petsify.shared.ui.theme.GoshawkGrey
import pl.kosciukw.petsify.shared.ui.theme.PureWhite
import pl.kosciukw.petsify.shared.ui.theme.TextBoldS
import pl.kosciukw.petsify.shared.ui.theme.TextRegularS
import pl.kosciukw.petsify.shared.ui.theme.paddingGapM
import pl.kosciukw.petsify.shared.ui.theme.paddingL
import pl.kosciukw.petsify.shared.ui.theme.paddingM
import pl.kosciukw.petsify.shared.ui.theme.paddingXL
import pl.kosciukw.petsify.shared.ui.theme.paddingXXL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(
    state: AddPetState,
    events: (AddPetEvent) -> Unit,
    action: Flow<AddPetAction>,
    strings: AddPetStrings,
    commonStrings: CommonScreenStrings,
    onNavigateUp: () -> Unit
) {
    LaunchedEffect(action) {
        action.collect { currentAction ->
            when (currentAction) {
                AddPetAction.NavigateUp -> onNavigateUp()
            }
        }
    }

    val primarySpecies = remember(state.speciesOptions) {
        state.speciesOptions.filter { it.isPrimary }
    }
    val otherSpecies = remember(state.speciesOptions) {
        state.speciesOptions.filterNot { it.isPrimary }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = strings.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = GoshawkGrey
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = commonStrings.navigateUpContentDescription
                        )
                    }
                }
            )
        },
        bottomBar = {
            AddPetBottomBar(
                isSaveEnabled = state.isSaveEnabled,
                strings = strings,
                onSaveClick = { events(AddPetEvent.OnSaveClicked) }
            )
        }
    ) { paddingValues ->
        AddPetContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            strings = strings,
            name = state.name,
            knowsBirthDate = state.knowsBirthDate,
            age = state.age,
            birthDate = state.birthDate,
            weight = state.weight,
            weightUnits = state.weightUnits,
            selectedWeightUnit = state.selectedWeightUnit,
            primarySpecies = primarySpecies,
            otherSpecies = otherSpecies,
            selectedSpecies = state.selectedSpecies,
            isOtherSpeciesExpanded = state.isOtherSpeciesExpanded,
            customSpecies = state.customSpecies,
            isMoreDetailsExpanded = state.isMoreDetailsExpanded,
            breed = state.breed,
            sex = state.sex,
            temperaments = state.temperaments,
            color = state.color,
            notes = state.notes,
            onNameChanged = { events(AddPetEvent.OnNameChanged(it)) },
            onSpeciesSelected = { events(AddPetEvent.OnSpeciesSelected(it)) },
            onOtherSpeciesToggled = { events(AddPetEvent.OnOtherSpeciesToggled) },
            onCustomSpeciesChanged = { events(AddPetEvent.OnCustomSpeciesChanged(it)) },
            onKnowsBirthDateChanged = { events(AddPetEvent.OnKnowsBirthDateChanged(it)) },
            onAgeChanged = { events(AddPetEvent.OnAgeChanged(it)) },
            onBirthDateChanged = { events(AddPetEvent.OnBirthDateChanged(it)) },
            onWeightChanged = { events(AddPetEvent.OnWeightChanged(it)) },
            onWeightUnitSelected = { events(AddPetEvent.OnWeightUnitSelected(it)) },
            onMoreDetailsToggled = { events(AddPetEvent.OnMoreDetailsToggled) },
            onBreedChanged = { events(AddPetEvent.OnBreedChanged(it)) },
            onSexSelected = { events(AddPetEvent.OnSexSelected(it)) },
            onTemperamentToggled = { events(AddPetEvent.OnTemperamentToggled(it)) },
            onColorChanged = { events(AddPetEvent.OnColorChanged(it)) },
            onNotesChanged = { events(AddPetEvent.OnNotesChanged(it)) }
        )
    }
}

@Composable
private fun AddPetBottomBar(
    isSaveEnabled: Boolean,
    strings: AddPetStrings,
    onSaveClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingXXL, vertical = paddingXL)
        ) {
            Text(
                text = strings.saveSummary,
                style = TextRegularS,
                color = GoshawkGrey
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = paddingM)
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlackLiquorice),
                enabled = isSaveEnabled,
                onClick = onSaveClick
            ) {
                Text(
                    text = strings.saveButton,
                    style = TextBoldS,
                    color = PureWhite
                )
            }
        }
    }
}

@Composable
private fun AddPetContent(
    modifier: Modifier = Modifier,
    strings: AddPetStrings,
    name: String,
    knowsBirthDate: Boolean,
    age: String,
    birthDate: String,
    weight: String,
    weightUnits: List<PetWeightUnitUiModel>,
    selectedWeightUnit: PetWeightUnitUiModel?,
    primarySpecies: List<PetSpeciesUiModel>,
    otherSpecies: List<PetSpeciesUiModel>,
    selectedSpecies: PetSpeciesUiModel?,
    isOtherSpeciesExpanded: Boolean,
    customSpecies: String,
    isMoreDetailsExpanded: Boolean,
    breed: String,
    sex: PetSexUiModel?,
    temperaments: Set<PetTemperamentUiModel>,
    color: String,
    notes: String,
    onNameChanged: (String) -> Unit,
    onSpeciesSelected: (PetSpeciesUiModel) -> Unit,
    onOtherSpeciesToggled: () -> Unit,
    onCustomSpeciesChanged: (String) -> Unit,
    onKnowsBirthDateChanged: (Boolean) -> Unit,
    onAgeChanged: (String) -> Unit,
    onBirthDateChanged: (String) -> Unit,
    onWeightChanged: (String) -> Unit,
    onWeightUnitSelected: (PetWeightUnitUiModel) -> Unit,
    onMoreDetailsToggled: () -> Unit,
    onBreedChanged: (String) -> Unit,
    onSexSelected: (PetSexUiModel) -> Unit,
    onTemperamentToggled: (PetTemperamentUiModel) -> Unit,
    onColorChanged: (String) -> Unit,
    onNotesChanged: (String) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = paddingXXL)
            .padding(bottom = paddingGapM),
        verticalArrangement = Arrangement.spacedBy(paddingXL)
    ) {
        AddPetIntro(strings = strings)

        PhotoUploadCard(strings = strings)

        NameFieldSection(
            name = name,
            strings = strings,
            onNameChanged = onNameChanged
        )

        SpeciesSection(
            strings = strings,
            primarySpecies = primarySpecies,
            otherSpecies = otherSpecies,
            selectedSpecies = selectedSpecies,
            isOtherSpeciesExpanded = isOtherSpeciesExpanded,
            customSpecies = customSpecies,
            onSpeciesSelected = onSpeciesSelected,
            onOtherSpeciesToggled = onOtherSpeciesToggled,
            onCustomSpeciesChanged = onCustomSpeciesChanged
        )

        AgeAndWeightSection(
            knowsBirthDate = knowsBirthDate,
            age = age,
            birthDate = birthDate,
            weight = weight,
            weightUnits = weightUnits,
            selectedWeightUnit = selectedWeightUnit,
            strings = strings,
            onKnowsBirthDateChanged = onKnowsBirthDateChanged,
            onAgeChanged = onAgeChanged,
            onBirthDateChanged = onBirthDateChanged,
            onWeightChanged = onWeightChanged,
            onWeightUnitSelected = onWeightUnitSelected
        )

        MoreDetailsSection(
            isExpanded = isMoreDetailsExpanded,
            breed = breed,
            sex = sex,
            temperaments = temperaments,
            color = color,
            notes = notes,
            strings = strings,
            onMoreDetailsToggled = onMoreDetailsToggled,
            onBreedChanged = onBreedChanged,
            onSexSelected = onSexSelected,
            onTemperamentToggled = onTemperamentToggled,
            onColorChanged = onColorChanged,
            onNotesChanged = onNotesChanged
        )
    }
}

@Composable
private fun AddPetIntro(strings: AddPetStrings) {
    Text(
        text = strings.intro,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingM),
        style = TextRegularS,
        color = GoshawkGrey,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun NameFieldSection(
    name: String,
    strings: AddPetStrings,
    onNameChanged: (String) -> Unit
) {
    PetsifyInput(
        modifier = Modifier.fillMaxWidth(),
        text = name,
        onTextChange = onNameChanged,
        label = strings.nameLabel,
        placeholder = strings.namePlaceholder
    )
}

@Composable
private fun AgeAndWeightSection(
    knowsBirthDate: Boolean,
    age: String,
    birthDate: String,
    weight: String,
    weightUnits: List<PetWeightUnitUiModel>,
    selectedWeightUnit: PetWeightUnitUiModel?,
    strings: AddPetStrings,
    onKnowsBirthDateChanged: (Boolean) -> Unit,
    onAgeChanged: (String) -> Unit,
    onBirthDateChanged: (String) -> Unit,
    onWeightChanged: (String) -> Unit,
    onWeightUnitSelected: (PetWeightUnitUiModel) -> Unit
) {
    PetsifyInput(
        modifier = Modifier.fillMaxWidth(),
        text = birthDate,
        onTextChange = onBirthDateChanged,
        label = strings.birthDateLabel,
        placeholder = strings.birthDatePlaceholder,
        enabled = knowsBirthDate
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        BirthdayKnowledgeTrigger(
            label = if (knowsBirthDate) {
                strings.birthdayUnknownTrigger
            } else {
                strings.birthdayKnownTrigger
            },
            onClick = { onKnowsBirthDateChanged(!knowsBirthDate) }
        )
    }

    AnimatedVisibility(
        visible = !knowsBirthDate,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        PetsifyInput(
            modifier = Modifier.fillMaxWidth(),
            text = age,
            onTextChange = onAgeChanged,
            label = strings.ageLabel,
            placeholder = strings.agePlaceholder
        )
    }

    PetsifyInput(
        modifier = Modifier.fillMaxWidth(),
        text = weight,
        onTextChange = onWeightChanged,
        label = strings.weightLabel,
        placeholder = strings.weightPlaceholder
    )

    Row(horizontalArrangement = Arrangement.spacedBy(paddingM)) {
        weightUnits.forEach { weightUnit ->
            SelectableAssistChip(
                label = weightUnit.label(strings),
                selected = selectedWeightUnit == weightUnit,
                onClick = { onWeightUnitSelected(weightUnit) }
            )
        }
    }
}

@Composable
private fun BirthdayKnowledgeTrigger(
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = GoshawkGrey
        )
    }
}

@Composable
private fun SpeciesSection(
    strings: AddPetStrings,
    primarySpecies: List<PetSpeciesUiModel>,
    otherSpecies: List<PetSpeciesUiModel>,
    selectedSpecies: PetSpeciesUiModel?,
    isOtherSpeciesExpanded: Boolean,
    customSpecies: String,
    onSpeciesSelected: (PetSpeciesUiModel) -> Unit,
    onOtherSpeciesToggled: () -> Unit,
    onCustomSpeciesChanged: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(paddingL)) {
        Text(
            text = strings.speciesLabel,
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(paddingM),
            verticalAlignment = Alignment.Top
        ) {
            primarySpecies.forEach { species ->
                SpeciesOptionCard(
                    modifier = Modifier.weight(1f),
                    species = species,
                    label = species.label(strings),
                    selected = selectedSpecies == species,
                    onClick = { onSpeciesSelected(species) }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            OtherSpeciesTrigger(
                label = strings.differentSpeciesTrigger,
                expanded = isOtherSpeciesExpanded,
                onClick = onOtherSpeciesToggled
            )
        }

        AnimatedVisibility(
            visible = isOtherSpeciesExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            OtherSpeciesSection(
                species = otherSpecies,
                selectedSpecies = selectedSpecies,
                strings = strings,
                onSpeciesSelected = onSpeciesSelected
            )
        }

        AnimatedVisibility(
            visible = selectedSpecies?.requiresCustomValue == true,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            PetsifyInput(
                modifier = Modifier.fillMaxWidth(),
                text = customSpecies,
                onTextChange = onCustomSpeciesChanged,
                label = strings.customSpeciesLabel,
                placeholder = strings.customSpeciesPlaceholder
            )
        }
    }
}

@Composable
private fun PhotoUploadCard(
    strings: AddPetStrings
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .size(124.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .background(MaterialTheme.colorScheme.tertiaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Pets,
                    contentDescription = strings.photoCta,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.size(48.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = strings.photoCta,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
private fun SpeciesOptionCard(
    modifier: Modifier = Modifier,
    species: PetSpeciesUiModel,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .clickable(onClick = onClick),
        color = if (selected) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.tertiaryContainer
        },
        tonalElevation = if (selected) 2.dp else 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = paddingXL),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(paddingM)
        ) {
            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = label,
                tint = if (selected) MaterialTheme.colorScheme.onSecondaryContainer
                else MaterialTheme.colorScheme.onTertiaryContainer
            )
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                color = if (selected) MaterialTheme.colorScheme.onSecondaryContainer
                else MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Composable
private fun OtherSpeciesTrigger(
    label: String,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (expanded) MaterialTheme.colorScheme.onTertiaryContainer else GoshawkGrey
        )
    }
}

@Composable
private fun OtherSpeciesSection(
    species: List<PetSpeciesUiModel>,
    selectedSpecies: PetSpeciesUiModel?,
    strings: AddPetStrings,
    onSpeciesSelected: (PetSpeciesUiModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(32.dp)
            )
            .padding(paddingL),
        verticalArrangement = Arrangement.spacedBy(paddingM)
    ) {
        Text(
            text = strings.otherSpeciesLabel,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            species.forEach { item ->
                SpeciesChip(
                    label = item.label(strings),
                    selected = selectedSpecies == item,
                    onClick = { onSpeciesSelected(item) }
                )
            }
        }
    }
}

@Suppress("unused")
@Composable
private fun OtherSpeciesSectionGrid(
    species: List<PetSpeciesUiModel>,
    selectedSpecies: PetSpeciesUiModel?,
    strings: AddPetStrings,
    onSpeciesSelected: (PetSpeciesUiModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(32.dp)
            )
            .padding(paddingL),
        verticalArrangement = Arrangement.spacedBy(paddingM)
    ) {
        Text(
            text = strings.otherSpeciesLabel,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .heightIn(max = 96.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(species) { item ->
                SpeciesChip(
                    label = item.label(strings),
                    selected = selectedSpecies == item,
                    onClick = { onSpeciesSelected(item) }
                )
            }
        }
    }
}

@Composable
private fun SpeciesChip(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .clickable(onClick = onClick),
        color = if (selected) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surfaceContainer
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (selected) MaterialTheme.colorScheme.onSecondaryContainer else GoshawkGrey
            )
        }
    }
}

private fun PetSpeciesUiModel.label(strings: AddPetStrings): String = when (petSpeciesDomainType) {
    PetSpeciesDomainType.Dog -> strings.dogOption
    PetSpeciesDomainType.Cat -> strings.catOption
    PetSpeciesDomainType.Rabbit -> strings.rabbitOption
    PetSpeciesDomainType.Hamster -> strings.hamsterOption
    PetSpeciesDomainType.Bird -> strings.birdOption
    PetSpeciesDomainType.Fish -> strings.fishOption
    PetSpeciesDomainType.Turtle -> strings.turtleOption
    PetSpeciesDomainType.Other -> strings.otherOption
}

private fun PetWeightUnitUiModel.label(strings: AddPetStrings): String =
    when (petWeightUnitDomainType) {
        PetWeightUnitDomainType.Kilograms -> strings.kilogramsOption
        PetWeightUnitDomainType.Pounds -> strings.poundsOption
    }

@Composable
private fun MoreDetailsSection(
    isExpanded: Boolean,
    breed: String,
    sex: PetSexUiModel?,
    temperaments: Set<PetTemperamentUiModel>,
    color: String,
    notes: String,
    strings: AddPetStrings,
    onMoreDetailsToggled: () -> Unit,
    onBreedChanged: (String) -> Unit,
    onSexSelected: (PetSexUiModel) -> Unit,
    onTemperamentToggled: (PetTemperamentUiModel) -> Unit,
    onColorChanged: (String) -> Unit,
    onNotesChanged: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(paddingL)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onMoreDetailsToggled),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = strings.moreDetailsLabel,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = if (isExpanded) "expand_less" else "expand_more",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(paddingL)) {
                PetsifyInput(
                    modifier = Modifier.fillMaxWidth(),
                    text = breed,
                    onTextChange = onBreedChanged,
                    label = strings.breedLabel,
                    placeholder = strings.breedPlaceholder
                )

                Text(
                    text = strings.sexLabel,
                    style = MaterialTheme.typography.titleSmall
                )
                Row(horizontalArrangement = Arrangement.spacedBy(paddingM)) {
                    SelectableAssistChip(
                        label = strings.maleOption,
                        selected = sex == PetSexUiModel.Male,
                        onClick = { onSexSelected(PetSexUiModel.Male) }
                    )
                    SelectableAssistChip(
                        label = strings.femaleOption,
                        selected = sex == PetSexUiModel.Female,
                        onClick = { onSexSelected(PetSexUiModel.Female) }
                    )
                }

                Text(
                    text = strings.temperamentLabel,
                    style = MaterialTheme.typography.titleSmall
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(paddingM)
                ) {
                    TemperamentChip(
                        label = strings.calmTemperament,
                        selected = PetTemperamentUiModel.Calm in temperaments,
                        onClick = { onTemperamentToggled(PetTemperamentUiModel.Calm) }
                    )
                    TemperamentChip(
                        label = strings.energeticTemperament,
                        selected = PetTemperamentUiModel.Energetic in temperaments,
                        onClick = { onTemperamentToggled(PetTemperamentUiModel.Energetic) }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(paddingM)
                ) {
                    TemperamentChip(
                        label = strings.friendlyTemperament,
                        selected = PetTemperamentUiModel.Friendly in temperaments,
                        onClick = { onTemperamentToggled(PetTemperamentUiModel.Friendly) }
                    )
                    TemperamentChip(
                        label = strings.shyTemperament,
                        selected = PetTemperamentUiModel.Shy in temperaments,
                        onClick = { onTemperamentToggled(PetTemperamentUiModel.Shy) }
                    )
                    TemperamentChip(
                        label = strings.curiousTemperament,
                        selected = PetTemperamentUiModel.Curious in temperaments,
                        onClick = { onTemperamentToggled(PetTemperamentUiModel.Curious) }
                    )
                }

                PetsifyInput(
                    modifier = Modifier.fillMaxWidth(),
                    text = color,
                    onTextChange = onColorChanged,
                    label = strings.colorLabel,
                    placeholder = strings.colorPlaceholder
                )

                PetsifyInput(
                    modifier = Modifier.fillMaxWidth(),
                    text = notes,
                    onTextChange = onNotesChanged,
                    label = strings.notesLabel,
                    placeholder = strings.notesPlaceholder,
                    singleLine = false,
                    minLines = 3
                )
            }
        }
    }
}

@Composable
private fun SelectableAssistChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    AssistChip(
        onClick = onClick,
        label = { Text(text = label) },
        colors = if (selected) {
            AssistChipDefaults.assistChipColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                labelColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        } else {
            AssistChipDefaults.assistChipColors()
        }
    )
}

@Composable
private fun TemperamentChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text = label) }
    )
}

@Composable
private fun PetsifyInput(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    placeholder: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChange,
        enabled = enabled,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        singleLine = singleLine,
        minLines = minLines,
        shape = RoundedCornerShape(28.dp)
    )
}
