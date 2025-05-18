package com.example.liftnotes.feature.view_exercises

import androidx.lifecycle.SavedStateHandle
import com.example.liftnotes.implementations.FakeViewExercisesRepository
import com.example.liftnotes.repository.model.DataResult
import com.example.liftnotes.test.testExercisesModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewExercisesViewModelTest {

    val dispatcher = StandardTestDispatcher()
    val savedStateHandle = SavedStateHandle()

    lateinit var repository: ViewExercisesRepository
    lateinit var viewModel: ViewExercisesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Fetch Exercises then Success`() = runTest {
        val exercises = testExercisesModel
        repository = FakeViewExercisesRepository(DataResult.Success(testExercisesModel))
        viewModel = ViewExercisesViewModel(repository, savedStateHandle)

        advanceUntilIdle()
        val state = viewModel.uiState.value

        assertTrue(state is ViewExercisesUiState.Success)
        assertEquals(exercises, (state as ViewExercisesUiState.Success).exercises)
    }

    @Test
    fun `AddClicked then Edit state`() = runTest {
        val exercises = testExercisesModel
        repository = FakeViewExercisesRepository(DataResult.Success(testExercisesModel))
        viewModel = ViewExercisesViewModel(repository, savedStateHandle)

        viewModel.onUiEvent(ViewExercisesUiEvent.AddClicked)
        val state = viewModel.bottomSheetState.value
        assertTrue(state is EditExerciseBottomSheetState.Edit)
    }
}