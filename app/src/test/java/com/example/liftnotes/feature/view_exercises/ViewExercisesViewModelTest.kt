package com.example.liftnotes.feature.view_exercises

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.liftnotes.navigation.WorkoutRoute
import com.example.liftnotes.repository.interfaces.WorkoutRepository
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.repository.model.ViewExercisesScreenData
import com.example.liftnotes.test.testExercisesModel
import com.example.liftnotes.test.testSessionsModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ViewExercisesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val savedStateHandle: SavedStateHandle = mock()
    private val fakeRepository: WorkoutRepository = mock()
    private lateinit var viewModel: ViewExercisesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Loading then Success State`() = runTest {
        whenever(savedStateHandle.get<Int>(WorkoutRoute.ARG_SESSION_ID)).thenReturn(1)
        whenever(fakeRepository.getSessionExercises(1))
            .thenReturn(flowOf(DataResult.Success(ViewExercisesScreenData(testSessionsModel.get(0), testExercisesModel))))

        viewModel = ViewExercisesViewModel(
            repository = fakeRepository,
            savedStateHandle = savedStateHandle
        )

        viewModel.uiState.test {
            assertTrue(awaitItem() is DataResult.Loading)
            assertTrue(awaitItem() is DataResult.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `BackPressed emits NavigateBack`() = runTest {
        whenever(savedStateHandle.get<Int>(WorkoutRoute.ARG_SESSION_ID)).thenReturn(1)
        viewModel = ViewExercisesViewModel(
            repository = fakeRepository,
            savedStateHandle = savedStateHandle
        )

        viewModel.effect.test {
            viewModel.onUiEvent(ViewExercisesUiEvent.BackPressed)
            assertTrue(awaitItem() is ViewExercisesUiEffect.NavigateBack)
            cancelAndIgnoreRemainingEvents()
        }
    }
}