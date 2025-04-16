package com.example.liftnotes.ui.screens.view_sessions

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import com.example.liftnotes.ui.common.FormTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSessionBottomSheet(
    bottomSheetState: BottomSheetState,
    onBottomSheetClose: () -> Unit,
    onSaveClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (bottomSheetState != BottomSheetState.Closed) {
        ModalBottomSheet(
            onDismissRequest = { onBottomSheetClose() },
            sheetState = sheetState
        ) {
            Column {
                FormTopBar(onBottomSheetClose, onSaveClick)
            }
        }
    }
}