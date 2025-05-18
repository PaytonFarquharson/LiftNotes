package com.example.liftnotes.feature.view_sessions

import com.example.liftnotes.implementations.FakeViewSessionsRepository
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.test.testCurrentSessionsModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek

@OptIn(ExperimentalCoroutinesApi::class)
class ViewSessionsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeRepository: ViewSessionsRepository
    private lateinit var viewModel: ViewSessionsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCurrentSessions emits Success`() = runTest {
        val sessions = testCurrentSessionsModel
        fakeRepository = FakeViewSessionsRepository(DataResult.Success(sessions))
        viewModel = ViewSessionsViewModel(fakeRepository)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ViewSessionsUiState.Success)
        assertEquals(sessions, (state as ViewSessionsUiState.Success).sessions)
    }

    @Test
    fun `fetchCurrentSessions emits Error`() = runTest {
        val result = DataResult.Error("message")
        fakeRepository = FakeViewSessionsRepository(result)
        viewModel = ViewSessionsViewModel(fakeRepository)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ViewSessionsUiState.Error)
        assertEquals("message", (state as ViewSessionsUiState.Error).message)
    }

    @Test
    fun `SessionClicked emits NavigateToSession effect`() = runTest {
        fakeRepository = FakeViewSessionsRepository(DataResult.Success(emptyList()))
        viewModel = ViewSessionsViewModel(fakeRepository)

        val emitted = mutableListOf<ViewSessionsUiEffect>()
        val job = launch {
            viewModel.effect.collect{
                emitted.add(it)
            }
        }

        viewModel.onUiEvent(ViewSessionsUiEvent.SessionClicked(1))
        advanceUntilIdle()

        assertTrue(emitted.contains(ViewSessionsUiEffect.NavigateToSession(1)))
        job.cancel()
    }

    @Test
    fun `AddClicked sets bottom sheet state to Edit`() = runTest {
        fakeRepository = FakeViewSessionsRepository(DataResult.Success(emptyList()))
        viewModel = ViewSessionsViewModel(fakeRepository)

        viewModel.onUiEvent(ViewSessionsUiEvent.AddClicked)

        val state = viewModel.bottomSheetState.value
        assertTrue(state is EditSessionBottomSheetState.Edit)
    }

    @Test
    fun `DayChanged toggles day highlight`() = runTest {
        fakeRepository = FakeViewSessionsRepository(DataResult.Success(emptyList()))
        viewModel = ViewSessionsViewModel(fakeRepository)

        viewModel.onUiEvent(ViewSessionsUiEvent.AddClicked)
        viewModel.onBottomSheetEvent(EditSessionBottomSheetEvent.DayChanged(DayOfWeek.THURSDAY))

        val state = viewModel.bottomSheetState.value as EditSessionBottomSheetState.Edit
        assertTrue(state.completionDays.first { it.dayOfWeek == DayOfWeek.THURSDAY }.isHighlighted)
    }
}