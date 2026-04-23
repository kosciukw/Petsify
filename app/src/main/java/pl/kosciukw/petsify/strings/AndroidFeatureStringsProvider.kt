package pl.kosciukw.petsify.strings

import android.content.Context
import pl.kosciukw.petsify.shared.strings.AddPetStrings
import pl.kosciukw.petsify.shared.strings.CommonScreenStrings
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider
import pl.kosciukw.petsify.shared.strings.LoginStrings
import pl.kosciukw.petsify.shared.strings.OtpStrings
import pl.kosciukw.petsify.shared.strings.SettingsStrings
import pl.kosciukw.petsify.shared.strings.SignUpStrings
import pl.kosciukw.petsify.shared.ui.R

class AndroidFeatureStringsProvider(
    private val context: Context
) : FeatureStringsProvider {

    override fun common() = CommonScreenStrings(
        okButton = context.getString(R.string.ok_button),
        navigateUpContentDescription = context.getString(R.string.navigate_up_content_description)
    )

    override fun login() = LoginStrings(
        welcome = context.getString(R.string.login_screen_welcome_to_our_community_today),
        emailLabel = context.getString(R.string.login_screen_email_field),
        emailError = context.getString(R.string.email_validation_error),
        passwordLabel = context.getString(R.string.login_screen_password_field),
        passwordError = context.getString(R.string.password_validation_error),
        forgotPassword = context.getString(R.string.login_screen_forgot_password),
        loginButton = context.getString(R.string.login_screen_login_button),
        newToPetsify = context.getString(R.string.login_screen_login_new_to_petsify),
        signUp = context.getString(R.string.login_screen_signup),
        orLabel = context.getString(R.string.login_screen_or),
        googleButton = context.getString(R.string.login_screen_google_login_button),
        appName = context.getString(R.string.app_name)
    )

    override fun signUp() = SignUpStrings(
        title = context.getString(R.string.sign_up_screen_header),
        intro = context.getString(R.string.sign_up_screen_please_enter_data),
        nameLabel = context.getString(R.string.sign_up_screen_name),
        emailLabel = context.getString(R.string.sign_up_screen_email),
        passwordLabel = context.getString(R.string.sign_up_screen_password),
        confirmPasswordLabel = context.getString(R.string.sign_up_screen_confirm_password),
        nameError = context.getString(R.string.name_validation_error),
        emailError = context.getString(R.string.email_validation_error),
        passwordError = context.getString(R.string.create_password_validation_error),
        confirmPasswordError = context.getString(R.string.confirm_password_validation_error),
        termsConsent = context.getString(R.string.sign_up_screen_confirm_accept_terms_and_conditions),
        marketingConsent = context.getString(R.string.sign_up_screen_confirm_accept_marketing),
        submitButton = context.getString(R.string.sign_up_screen_sign_up),
        haveAccount = context.getString(R.string.sign_up_screen_have_an_account),
        loginAction = context.getString(R.string.sign_up_screen_login)
    )

    override fun otp() = OtpStrings(
        title = context.getString(R.string.sign_up_screen_header),
        intro = context.getString(R.string.sign_up_by_otp_screen),
        validationError = context.getString(R.string.otp_validation_error),
        submitButton = context.getString(R.string.sign_up_screen_sign_up)
    )

    override fun settings() = SettingsStrings(
        title = context.getString(R.string.settings_screen_header),
        body = context.getString(R.string.settings_screen_title)
    )

    override fun addPet() = AddPetStrings(
        title = context.getString(R.string.add_pet_screen_title),
        intro = context.getString(R.string.add_pet_screen_intro),
        photoCta = context.getString(R.string.add_pet_screen_photo_cta),
        nameLabel = context.getString(R.string.add_pet_screen_name_label),
        speciesLabel = context.getString(R.string.add_pet_screen_species_label),
        dogOption = context.getString(R.string.add_pet_screen_species_dog),
        catOption = context.getString(R.string.add_pet_screen_species_cat),
        differentSpeciesTrigger = context.getString(R.string.add_pet_screen_species_different_trigger),
        otherSpeciesLabel = context.getString(R.string.add_pet_screen_species_other_label),
        rabbitOption = context.getString(R.string.add_pet_screen_species_rabbit),
        hamsterOption = context.getString(R.string.add_pet_screen_species_hamster),
        birdOption = context.getString(R.string.add_pet_screen_species_bird),
        fishOption = context.getString(R.string.add_pet_screen_species_fish),
        turtleOption = context.getString(R.string.add_pet_screen_species_turtle),
        otherOption = context.getString(R.string.add_pet_screen_species_other),
        customSpeciesLabel = context.getString(R.string.add_pet_screen_custom_species_label),
        ageLabel = context.getString(R.string.add_pet_screen_age_label),
        birthDateLabel = context.getString(R.string.add_pet_screen_birth_date_label),
        knowsBirthDateLabel = context.getString(R.string.add_pet_screen_knows_birth_date_label),
        weightLabel = context.getString(R.string.add_pet_screen_weight_label),
        moreDetailsLabel = context.getString(R.string.add_pet_screen_more_details_label),
        breedLabel = context.getString(R.string.add_pet_screen_breed_label),
        sexLabel = context.getString(R.string.add_pet_screen_sex_label),
        temperamentLabel = context.getString(R.string.add_pet_screen_temperament_label),
        colorLabel = context.getString(R.string.add_pet_screen_color_label),
        notesLabel = context.getString(R.string.add_pet_screen_notes_label),
        saveButton = context.getString(R.string.add_pet_screen_save_button),
        saveSummary = context.getString(R.string.add_pet_screen_save_summary),
        namePlaceholder = context.getString(R.string.add_pet_screen_name_placeholder),
        customSpeciesPlaceholder = context.getString(R.string.add_pet_screen_custom_species_placeholder),
        agePlaceholder = context.getString(R.string.add_pet_screen_age_placeholder),
        birthDatePlaceholder = context.getString(R.string.add_pet_screen_birth_date_placeholder),
        weightPlaceholder = context.getString(R.string.add_pet_screen_weight_placeholder),
        breedPlaceholder = context.getString(R.string.add_pet_screen_breed_placeholder),
        colorPlaceholder = context.getString(R.string.add_pet_screen_color_placeholder),
        notesPlaceholder = context.getString(R.string.add_pet_screen_notes_placeholder),
        maleOption = context.getString(R.string.add_pet_screen_male_option),
        femaleOption = context.getString(R.string.add_pet_screen_female_option),
        calmTemperament = context.getString(R.string.add_pet_screen_temperament_calm),
        energeticTemperament = context.getString(R.string.add_pet_screen_temperament_energetic),
        friendlyTemperament = context.getString(R.string.add_pet_screen_temperament_friendly),
        shyTemperament = context.getString(R.string.add_pet_screen_temperament_shy),
        curiousTemperament = context.getString(R.string.add_pet_screen_temperament_curious)
    )
}
