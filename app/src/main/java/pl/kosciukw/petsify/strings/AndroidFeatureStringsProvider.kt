package pl.kosciukw.petsify.strings

import android.content.Context
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
}
