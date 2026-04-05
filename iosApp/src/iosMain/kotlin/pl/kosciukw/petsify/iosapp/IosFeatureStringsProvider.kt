package pl.kosciukw.petsify.iosapp

import pl.kosciukw.petsify.shared.strings.AddPetStrings
import pl.kosciukw.petsify.shared.strings.CommonScreenStrings
import pl.kosciukw.petsify.shared.strings.FeatureStringsProvider
import pl.kosciukw.petsify.shared.strings.LoginStrings
import pl.kosciukw.petsify.shared.strings.OtpStrings
import pl.kosciukw.petsify.shared.strings.SettingsStrings
import pl.kosciukw.petsify.shared.strings.SignUpStrings

internal class IosFeatureStringsProvider : FeatureStringsProvider {

    override fun common() = CommonScreenStrings(
        okButton = "OK",
        navigateUpContentDescription = "Navigate up"
    )

    override fun login() = LoginStrings(
        welcome = "Welcome to our pet community!",
        emailLabel = "Email*",
        emailError = "Please enter a valid email address",
        passwordLabel = "Password*",
        passwordError = "Please enter a valid password",
        forgotPassword = "Forgot password?",
        loginButton = "Login",
        newToPetsify = "New to Petsify?",
        signUp = "Sign up",
        orLabel = "or",
        googleButton = "Continue with Google",
        appName = "Petsify"
    )

    override fun signUp() = SignUpStrings(
        title = "Sign up",
        intro = "Please enter required data to complete registration",
        nameLabel = "Name",
        emailLabel = "Email*",
        passwordLabel = "Password*",
        confirmPasswordLabel = "Confirm Password*",
        nameError = "Please enter a valid name",
        emailError = "Please enter a valid email address",
        passwordError = "Password should contain at least 9 characters, including one uppercase letter, one lowercase letter, and one digit",
        confirmPasswordError = "Passwords do not match",
        termsConsent = "I accept the Terms and Conditions and Privacy Policy",
        marketingConsent = "I agree to receive marketing and promotional emails from Petsify to the email address I provided. I understand that I can cancel at any time",
        submitButton = "Sign up",
        haveAccount = "Have an account?",
        loginAction = "Log in"
    )

    override fun otp() = OtpStrings(
        title = "Sign up",
        intro = "Type the code we sent you to your email address",
        validationError = "Please enter the 6-digit code",
        submitButton = "Sign up"
    )

    override fun settings() = SettingsStrings(
        title = "Settings",
        body = "Settings"
    )

    override fun addPet() = AddPetStrings(
        title = "Add Pet",
        intro = "Create a profile for your furry friend",
        photoCta = "Add photo",
        nameLabel = "Pet name",
        speciesLabel = "Species",
        dogOption = "Dog",
        catOption = "Cat",
        differentSpeciesTrigger = "Different species?",
        otherSpeciesLabel = "Other species",
        rabbitOption = "Rabbit",
        hamsterOption = "Hamster",
        birdOption = "Bird",
        fishOption = "Fish",
        turtleOption = "Turtle",
        otherOption = "Other",
        customSpeciesLabel = "Pet type",
        ageLabel = "Age",
        birthDateLabel = "Birthday",
        birthdayUnknownTrigger = "Don't know birthday?",
        birthdayKnownTrigger = "Know the birthday?",
        weightLabel = "Weight",
        kilogramsOption = "kg",
        poundsOption = "lbs",
        moreDetailsLabel = "More details",
        breedLabel = "Breed",
        sexLabel = "Sex",
        temperamentLabel = "Temperament",
        colorLabel = "Color / coat",
        notesLabel = "Notes",
        saveButton = "Save Pet",
        saveSummary = "You can add more details later",
        namePlaceholder = "e.g. Luna",
        customSpeciesPlaceholder = "e.g. Rabbit",
        agePlaceholder = "e.g. 3 years",
        birthDatePlaceholder = "YYYY-MM-DD",
        weightPlaceholder = "e.g. 12.5",
        breedPlaceholder = "Breed or type",
        colorPlaceholder = "e.g. Ginger",
        notesPlaceholder = "Anything helpful to remember?",
        maleOption = "Male",
        femaleOption = "Female",
        calmTemperament = "Calm",
        energeticTemperament = "Energetic",
        friendlyTemperament = "Friendly",
        shyTemperament = "Shy",
        curiousTemperament = "Curious"
    )
}
