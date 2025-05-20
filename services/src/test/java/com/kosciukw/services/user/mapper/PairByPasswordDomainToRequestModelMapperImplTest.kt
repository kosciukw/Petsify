package com.kosciukw.services.user.mapper

import com.kosciukw.services.data.user.mapper.PairByPasswordDomainToRequestModelMapper
import com.kosciukw.services.data.user.mapper.impl.PairByPasswordDomainToRequestModelMapperImpl
import com.kosciukw.services.data.user.model.domain.PairByPasswordDomainModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PairByPasswordDomainToRequestModelMapperImplTest {

    private lateinit var mapper: PairByPasswordDomainToRequestModelMapper

    @BeforeEach
    fun setUp() {
        mapper = PairByPasswordDomainToRequestModelMapperImpl()
    }

    @Test
    fun `When domain model is mapped Then should return request model with same values`() {
        val givenEmail = "user@example.com"
        val givenPassword = "securePass123"
        val domainModel = PairByPasswordDomainModel(
            email = givenEmail,
            password = givenPassword
        )

        val result = mapper.map(domainModel)

        assertEquals(givenEmail, result.email)
        assertEquals(givenPassword, result.password)
    }
}