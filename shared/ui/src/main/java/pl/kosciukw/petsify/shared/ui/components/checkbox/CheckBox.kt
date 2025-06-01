package pl.kosciukw.petsify.shared.ui.components.checkbox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pl.kosciukw.petsify.shared.ui.components.spacer.Spacer8dp
import pl.kosciukw.petsify.shared.ui.theme.BodyXS
import pl.kosciukw.petsify.shared.ui.theme.MidnightBlue
import pl.kosciukw.petsify.shared.ui.theme.PureWhite
import pl.kosciukw.petsify.shared.ui.R as SharedR

@Composable
fun CheckBoxText(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    text: String,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onCheckedChange(!isChecked) }
    ) {
        Checkbox(
            checked = isChecked,
            colors = CheckboxDefaults.colors(
                checkedColor = MidnightBlue,
                checkmarkColor = PureWhite
            ),
            onCheckedChange = {
                onCheckedChange(!isChecked)
            }
        )

        Spacer(modifier = Modifier.width(BodyXS))

        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium
        )

//        TextWithLinks(
//            text = "Akceptuję Regulamin i Politykę Prywatności",
//            onClickTerms = { /* TODO: Open Terms screen */ },
//            onClickPrivacy = { /* TODO: Open Privacy screen */ }
//        )
    }

}

@Composable
private fun TermsAndConditionsCheckbox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onCheckedChange(!isChecked) }
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {

            }
        )
        Spacer8dp()
        Text(
            text = stringResource(id = SharedR.string.sign_up_screen_confirm_accept_terms_and_conditions),
            style = MaterialTheme.typography.labelLarge
        )
//        TextWithLinks(
//            text = "Akceptuję Regulamin i Politykę Prywatności",
//            onClickTerms = { /* TODO: Open Terms screen */ },
//            onClickPrivacy = { /* TODO: Open Privacy screen */ }
//        )
    }
}