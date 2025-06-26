package com.kosciukw.services.user.mapper

import com.kosciukw.services.data.user.mapper.LoginByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.impl.LoginByPasswordDomainToRequestModelMapperImpl
import com.kosciukw.services.data.user.model.domain.LoginByPasswordDomainModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LoginByPasswordDomainToRequestModelMapperImplTest {

    private lateinit var mapper: LoginByPasswordDomainToRequestModelMapper

    @BeforeEach
    fun setUp() {
        mapper = LoginByPasswordDomainToRequestModelMapperImpl()
    }

    @Test
    fun `When domain model is mapped Then should return request model with same values`() {
        val givenEmail = "user@example.com"
        val givenPassword = "securePass123"
        val domainModel = LoginByPasswordDomainModel(
            email = givenEmail,
            password = givenPassword
        )

        val result = mapper.map(domainModel)

        assertEquals(givenEmail, result.email)
        assertEquals(givenPassword, result.password)
    }
}