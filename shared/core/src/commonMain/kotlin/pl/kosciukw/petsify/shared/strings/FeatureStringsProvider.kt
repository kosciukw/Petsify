package pl.kosciukw.petsify.shared.strings

interface FeatureStringsProvider {
    fun common(): CommonScreenStrings
    fun login(): LoginStrings
    fun signUp(): SignUpStrings
    fun otp(): OtpStrings
    fun settings(): SettingsStrings
}
