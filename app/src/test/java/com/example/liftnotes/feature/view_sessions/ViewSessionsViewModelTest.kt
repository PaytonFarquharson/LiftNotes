package com.example.liftnotes.feature.view_sessions

import com.example.liftnotes.implementations.FakeViewSessionsRepository
import com.example.liftnotes.interfaces.ViewSessionsRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ViewSessionsViewModelTest {

    private lateinit var fakeRepository: ViewSessionsRepository
    private lateinit var viewModel: ViewSessionsViewModel

    @Test
    fun `fetchCurrentSessions emits Success`() = runBlocking {
        fakeRepository = FakeViewSessionsRepository()
        viewModel = ViewSessionsViewModel(fakeRepository)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ViewSessionsUiState.Success)
    }
}