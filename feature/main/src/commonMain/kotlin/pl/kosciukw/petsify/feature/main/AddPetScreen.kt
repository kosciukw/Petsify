package pl.kosciukw.petsify.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import pl.kosciukw.petsify.feature.main.presentation.AddPetAction
import pl.kosciukw.petsify.feature.main.presentation.AddPetEvent
import pl.kosciukw.petsify.feature.main.presentation.AddPetState
import pl.kosciukw.petsify.feature.main.presentation.PetSexUiModel
import pl.kosciukw.petsify.feature.main.presentation.PetSpeciesUiModel
import pl.kosciukw.petsify.feature.main.presentation.PetSpeciesUiModelProvider
import pl.kosciukw.petsify.feature.main.presentation.PetTemperamentUiModel
import pl.kosciukw.petsify.shared.strings.AddPetStrings
import pl.kosciukw.petsify.shared.strings.CommonScreenStrings
import pl.kosciukw.petsify.shared.ui.theme.BlackLiquorice
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

  val primarySpecies = state.speciesOptions.filter { it.isPrimary }
  val otherSpecies = state.speciesOptions.filterNot { it.isPrimary }
  val selectedSpecies = state.selectedSpecies

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
            style = MaterialTheme.typography.titleLarge
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Button(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = paddingM)
              .height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BlackLiquorice),
            enabled = state.isSaveEnabled,
            onClick = { events(AddPetEvent.OnSaveClicked) }
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
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = paddingXXL)
        .padding(bottom = paddingGapM),
      verticalArrangement = Arrangement.spacedBy(paddingXL)
    ) {
      Text(
        text = strings.intro,
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = paddingM),
        style = TextRegularS,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
      )

      PhotoUploadCard(strings = strings)

      PetsifyInput(
        modifier = Modifier.fillMaxWidth(),
        text = state.name,
        onTextChange = { events(AddPetEvent.OnNameChanged(it)) },
        label = strings.nameLabel,
        placeholder = strings.namePlaceholder
      )

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
              selected = selectedSpecies?.code == species.code,
              onClick = { events(AddPetEvent.OnSpeciesSelected(species)) }
            )
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          OtherSpeciesTrigger(
            label = strings.differentSpeciesTrigger,
            expanded = state.isOtherSpeciesExpanded,
            onClick = { events(AddPetEvent.OnOtherSpeciesToggled) }
          )
        }

        if (state.isOtherSpeciesExpanded) {
          OtherSpeciesSection(
            species = otherSpecies,
            selectedSpeciesCode = selectedSpecies?.code,
            strings = strings,
            onSpeciesSelected = { events(AddPetEvent.OnSpeciesSelected(it)) }
          )
        }

        if (selectedSpecies?.requiresCustomValue == true) {
          PetsifyInput(
            modifier = Modifier.fillMaxWidth(),
            text = state.customSpecies,
            onTextChange = { events(AddPetEvent.OnCustomSpeciesChanged(it)) },
            label = strings.customSpeciesLabel,
            placeholder = strings.customSpeciesPlaceholder
          )
        }
      }

      CheckBoxRow(
        isChecked = state.knowsBirthDate,
        text = strings.knowsBirthDateLabel,
        onCheckedChange = { events(AddPetEvent.OnKnowsBirthDateChanged(it)) }
      )

      if (state.knowsBirthDate) {
        PetsifyInput(
          modifier = Modifier.fillMaxWidth(),
          text = state.birthDate,
          onTextChange = { events(AddPetEvent.OnBirthDateChanged(it)) },
          label = strings.birthDateLabel,
          placeholder = strings.birthDatePlaceholder
        )
      } else {
        PetsifyInput(
          modifier = Modifier.fillMaxWidth(),
          text = state.age,
          onTextChange = { events(AddPetEvent.OnAgeChanged(it)) },
          label = strings.ageLabel,
          placeholder = strings.agePlaceholder
        )
      }

      PetsifyInput(
        modifier = Modifier.fillMaxWidth(),
        text = state.weight,
        onTextChange = { events(AddPetEvent.OnWeightChanged(it)) },
        label = strings.weightLabel,
        placeholder = strings.weightPlaceholder
      )

      MoreDetailsSection(
        state = state,
        strings = strings,
        events = events
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
          .clip(RoundedCornerShape(28.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Pets,
          contentDescription = strings.photoCta,
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.size(48.dp)
        )
      }

      Box(
        modifier = Modifier
          .size(42.dp)
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.AddAPhoto,
          contentDescription = strings.photoCta,
          tint = MaterialTheme.colorScheme.onPrimary
        )
      }
    }
  }
}

@Composable
private fun SpeciesOptionCard(
  modifier: Modifier = Modifier,
  species: PetSpeciesUiModel,
  selected: Boolean,
  onClick: () -> Unit
) {
  Surface(
    modifier = modifier
      .clip(RoundedCornerShape(24.dp))
      .clickable(onClick = onClick),
    color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
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
        contentDescription = species.label,
        tint = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
      )
      Text(
        text = species.label,
        fontWeight = FontWeight.Bold,
        color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
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
      color = if (expanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}

@Composable
private fun OtherSpeciesSection(
  species: List<PetSpeciesUiModel>,
  selectedSpeciesCode: String?,
  strings: AddPetStrings,
  onSpeciesSelected: (PetSpeciesUiModel) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(
        color = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(24.dp)
      )
      .padding(paddingL),
    verticalArrangement = Arrangement.spacedBy(paddingM)
  ) {
    Text(
      text = strings.otherSpeciesLabel,
      style = MaterialTheme.typography.labelMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      species.take(3).forEach { item ->
        SpeciesChip(
          modifier = Modifier.weight(1f),
          species = item,
          selected = selectedSpeciesCode == item.code,
          onClick = { onSpeciesSelected(item) }
        )
      }
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      species.drop(3).forEach { item ->
        SpeciesChip(
          modifier = Modifier.weight(1f),
          species = item,
          selected = selectedSpeciesCode == item.code,
          onClick = { onSpeciesSelected(item) }
        )
      }
    }
  }
}

@Composable
private fun SpeciesChip(
  modifier: Modifier = Modifier,
  species: PetSpeciesUiModel,
  selected: Boolean,
  onClick: () -> Unit
) {
  Surface(
    modifier = modifier
      .clip(RoundedCornerShape(999.dp))
      .clickable(onClick = onClick),
    color = if (selected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp, horizontal = 12.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = species.label,
        style = MaterialTheme.typography.labelLarge,
        color = if (selected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Composable
private fun MoreDetailsSection(
  state: AddPetState,
  strings: AddPetStrings,
  events: (AddPetEvent) -> Unit
) {
  Column(verticalArrangement = Arrangement.spacedBy(paddingL)) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clickable { events(AddPetEvent.OnMoreDetailsToggled) },
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = strings.moreDetailsLabel,
        style = MaterialTheme.typography.titleMedium
      )
      Text(
        text = if (state.isMoreDetailsExpanded) "expand_less" else "expand_more",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary
      )
    }

    if (state.isMoreDetailsExpanded) {
      PetsifyInput(
        modifier = Modifier.fillMaxWidth(),
        text = state.breed,
        onTextChange = { events(AddPetEvent.OnBreedChanged(it)) },
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
          selected = state.sex == PetSexUiModel.Male,
          onClick = { events(AddPetEvent.OnSexSelected(PetSexUiModel.Male)) }
        )
        SelectableAssistChip(
          label = strings.femaleOption,
          selected = state.sex == PetSexUiModel.Female,
          onClick = { events(AddPetEvent.OnSexSelected(PetSexUiModel.Female)) }
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
          selected = PetTemperamentUiModel.Calm in state.temperaments,
          onClick = { events(AddPetEvent.OnTemperamentToggled(PetTemperamentUiModel.Calm)) }
        )
        TemperamentChip(
          label = strings.energeticTemperament,
          selected = PetTemperamentUiModel.Energetic in state.temperaments,
          onClick = { events(AddPetEvent.OnTemperamentToggled(PetTemperamentUiModel.Energetic)) }
        )
      }
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(paddingM)
      ) {
        TemperamentChip(
          label = strings.friendlyTemperament,
          selected = PetTemperamentUiModel.Friendly in state.temperaments,
          onClick = { events(AddPetEvent.OnTemperamentToggled(PetTemperamentUiModel.Friendly)) }
        )
        TemperamentChip(
          label = strings.shyTemperament,
          selected = PetTemperamentUiModel.Shy in state.temperaments,
          onClick = { events(AddPetEvent.OnTemperamentToggled(PetTemperamentUiModel.Shy)) }
        )
        TemperamentChip(
          label = strings.curiousTemperament,
          selected = PetTemperamentUiModel.Curious in state.temperaments,
          onClick = { events(AddPetEvent.OnTemperamentToggled(PetTemperamentUiModel.Curious)) }
        )
      }

      PetsifyInput(
        modifier = Modifier.fillMaxWidth(),
        text = state.color,
        onTextChange = { events(AddPetEvent.OnColorChanged(it)) },
        label = strings.colorLabel,
        placeholder = strings.colorPlaceholder
      )

      PetsifyInput(
        modifier = Modifier.fillMaxWidth(),
        text = state.notes,
        onTextChange = { events(AddPetEvent.OnNotesChanged(it)) },
        label = strings.notesLabel,
        placeholder = strings.notesPlaceholder,
        singleLine = false,
        minLines = 3
      )
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
  singleLine: Boolean = true,
  minLines: Int = 1
) {
  OutlinedTextField(
    modifier = modifier,
    value = text,
    onValueChange = onTextChange,
    label = { Text(text = label) },
    placeholder = { Text(text = placeholder) },
    singleLine = singleLine,
    minLines = minLines,
    shape = RoundedCornerShape(20.dp)
  )
}

@Composable
private fun CheckBoxRow(
  isChecked: Boolean,
  text: String,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onCheckedChange(!isChecked) },
    verticalAlignment = Alignment.CenterVertically
  ) {
    Checkbox(
      checked = isChecked,
      onCheckedChange = { onCheckedChange(!isChecked) }
    )
    Text(
      text = text,
      style = TextRegularS
    )
  }
}
