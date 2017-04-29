package com.felipecosta.microservice.app.movies.domain

import com.felipecosta.microservice.app.core.domain.MoviesRepository
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class GetMoviesUseCaseTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository

    lateinit var getMoviesUseCase: GetMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        getMoviesUseCase = GetMoviesUseCase(moviesRepository)
    }

    @Test
    fun name() {
        given(moviesRepository.findAll()).willReturn(emptyList())

        assertEquals(emptyList(), getMoviesUseCase.execute())
    }
}