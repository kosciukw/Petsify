package pl.kosciukw.petsify.feature.pairdevice.domain

import pl.kosciukw.petsify.shared.validator.EmailIdentifier
import pl.kosciukw.petsify.shared.validator.IdentifierValidator

interface EmailIdentifierValidator : IdentifierValidator<EmailIdentifier>