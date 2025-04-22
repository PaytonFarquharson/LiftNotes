package com.example.liftnotes.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.liftnotes.R

@Composable
fun IconPicker(
    onIconSelected: (Int) -> Unit,
    @DrawableRes currentIcon: Int
) {
    val showDialog = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        val iconColor = if (currentIcon == R.drawable.ic_empty) {
            MaterialTheme.colorScheme.outline
        } else {
            MaterialTheme.colorScheme.onSurface
        }
        Icon(
            ImageVector.vectorResource(currentIcon),
            contentDescription = "Icon",
            tint = iconColor,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(32.dp)
        )

        ElevatedButton(
            onClick = { showDialog.value = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Text(
                text = "Select Icon",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }

    if (showDialog.value) {
        IconPickerDialog(
            onIconSelected
        ) { showDialog.value = false }
    }
}

@Composable
fun IconPickerDialog(
    onIconSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = "Select Icon")
        },
        text = {
            val icons = Icons.getIcons()
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                columns = GridCells.FixedSize(48.dp)
            ) {
                items(
                    count = icons.size,
                    itemContent = { index ->
                        IconItemView(
                            imageId = icons[index],
                            onIconSelected = onIconSelected
                        )
                    }
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {

        },
        dismissButton = {

        }
    )
}

@Composable
fun IconItemView(@DrawableRes imageId: Int, onIconSelected: (Int) -> Unit) {
    Icon(
        imageVector = ImageVector.vectorResource(imageId),
        contentDescription = "",
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                onIconSelected(imageId)
            }
    )
}

object Icons {
    val gearIcons = listOf(
        R.drawable.ic_ab_wheel,
        R.drawable.ic_balance_ball,
        R.drawable.ic_bands,
        R.drawable.ic_barbell,
        R.drawable.ic_barbell_fixed,
        R.drawable.ic_bicycle,
        R.drawable.ic_boxing_glove,
        R.drawable.ic_cable_handle,
        R.drawable.ic_calorie_burn,
        R.drawable.ic_camber_bar,
        R.drawable.ic_dip_bars,
        R.drawable.ic_dumbbell,
        R.drawable.ic_dumbbell_kettlebell,
        R.drawable.ic_dumbbell_pair,
        R.drawable.ic_dumbbell_timer,
        R.drawable.ic_expander,
        R.drawable.ic_ez_bar,
        R.drawable.ic_ez_bar_fixed,
        R.drawable.ic_gymnastics_rings,
        R.drawable.ic_hand_grip,
        R.drawable.ic_hex_dumbbell,
        R.drawable.ic_hex_dumbbell_pair,
        R.drawable.ic_jump_rope,
        R.drawable.ic_kettlebell,
        R.drawable.ic_massage_gun,
        R.drawable.ic_plyo_boxes,
        R.drawable.ic_push_up_handles,
        R.drawable.ic_rope_handle,
        R.drawable.ic_running_shoe,
        R.drawable.ic_step_up,
        R.drawable.ic_swimming_gear,
        R.drawable.ic_water_bottle,
        R.drawable.ic_weight_plates,
        R.drawable.ic_yoga_ball,
        R.drawable.ic_yoga_mat,
    )

    val stationIcons = listOf(
        R.drawable.ic_bench_press,
        R.drawable.ic_dip_handles,
        R.drawable.ic_elliptical,
        R.drawable.ic_flat_bench,
        R.drawable.ic_fly_machine,
        R.drawable.ic_gym_machine,
        R.drawable.ic_incline_bench,
        R.drawable.ic_ladder_rings,
        R.drawable.ic_landmine,
        R.drawable.ic_lat_pulldown,
        R.drawable.ic_lat_pulldown_low_row,
        R.drawable.ic_leg_machine,
        R.drawable.ic_leg_press,
        R.drawable.ic_monkey_bars,
        R.drawable.ic_pull_up_bar,
        R.drawable.ic_pull_up_station,
        R.drawable.ic_pullup_handles,
        R.drawable.ic_punching_bag,
        R.drawable.ic_push_machine,
        R.drawable.ic_rowing_machine,
        R.drawable.ic_shoulder_press,
        R.drawable.ic_squat_rack,
        R.drawable.ic_stationary_bike,
        R.drawable.ic_treadmill,
        R.drawable.ic_treadmill_handles,
        R.drawable.ic_wall_bars,
    )

    val motionIcons = listOf(
        R.drawable.ic_ab_rolling,
        R.drawable.ic_bench_pressing,
        R.drawable.ic_curling,
        R.drawable.ic_meditation,
        R.drawable.ic_pull_up,
        R.drawable.ic_push_up,
        R.drawable.ic_running,
        R.drawable.ic_stretch1,
        R.drawable.ic_stretch2,
        R.drawable.ic_stretch3,
        R.drawable.ic_stretch4,
        R.drawable.ic_stretch5,
        R.drawable.ic_stretch6,
        R.drawable.ic_swimming,
        R.drawable.ic_training,
        R.drawable.ic_warm_up,
    )

    fun getIcons(): List<Int> {
        return gearIcons + stationIcons + motionIcons
    }
}