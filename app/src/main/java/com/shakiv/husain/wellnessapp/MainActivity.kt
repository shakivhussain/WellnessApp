package com.shakiv.husain.wellnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shakiv.husain.wellnessapp.WellnessData.getWellnessTasks
import com.shakiv.husain.wellnessapp.ui.theme.WellnessAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WellnessAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WellnessScreen()
                }
            }
        }
    }
}


@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column {
        WaterCounter()
//        val listOfTasks = remember { getWellnessTasks().toMutableStateList() }


        val listOfTasks = remember {
            mutableStateListOf<WellnessTask>().apply { addAll(getWellnessTasks()) }
        }


        WellnessTasksList(modifier, listOfTasks, onClose = { task ->
            listOfTasks.remove(task)
        })
    }

}

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {

    Column(modifier = modifier.padding(16.dp)) {

        var count by remember { mutableStateOf(0) }

        if (count > 0) {
            var showTask by remember { mutableStateOf(true) }
            if (showTask) {
                WellnessTaskItem(taskName = "Have you taken your 15 minute walk today?",
                    onClose = {
                        showTask = false
                    },
                    checked = true,
                    onCheckedChange = {})
            }
            Text(text = "You've had $count glasses.")
        }

        Row(Modifier.padding(16.dp)) {

            Button(
                onClick = { count++ },
                modifier = Modifier
                    .padding(8.dp),
                enabled = count < 10
            ) {
                Text(text = "Add One")
            }


            Button(
                onClick = { count = 0 },
                Modifier.padding(8.dp),
            ) {
                Text(text = "Clear Water Count")

            }

        }
    }
}


@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    listOfTasks: SnapshotStateList<WellnessTask>,
    onClose: (WellnessTask) -> Unit
) {
    LazyColumn(modifier) {
        items(
            listOfTasks,
            key = {task-> task.id }
        ) { task ->
            WellnessTaskItem(taskName = task.label, onClose = { onClose(task) })
        }
    }

}


@Composable
fun WellnessTaskItem(
    taskName: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var checkedState by rememberSaveable() { mutableStateOf(false) }

    WellnessTaskItem(
        taskName = taskName,
        checked = checkedState,
        onCheckedChange = { newValue ->
            checkedState = newValue
        },
        onClose = { onClose() },
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun WellnessTaskItemPreview() {
    WellnessTaskItem(
        taskName = "Shakib",
        onClose = {},
        checked = true,
        onCheckedChange = {}
    )
}

@Composable
fun WellnessTaskItem(
    taskName: String,
    onClose: () -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = taskName, modifier = Modifier
                .weight(1F)
                .padding(start = 8.dp)
        )

        Checkbox(
            checked = checked, onCheckedChange = onCheckedChange, modifier = Modifier
                .weight(.5F)
                .padding(start = 8.dp)
        )

        IconButton(onClick = { onClose() }) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close",
            )
        }

    }

}


@Composable
fun StateLessComposable(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier) {
        if (count > 0) {
            Text(text = "You've had $count Glasses.")
        }

        Button(
            onClick = { onIncrement.invoke() },
            modifier = Modifier.padding(top = 8.dp),
            enabled = count < 10
        ) {
            Text(text = "Add one")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StateFullComposable(modifier: Modifier = Modifier) {

    var waterCount by remember { mutableStateOf(0) }
    var juiceCount by remember { mutableStateOf(0) }

    Column {

        StateLessComposable(count = waterCount, onIncrement = { waterCount++ }, modifier = modifier)
        StateLessComposable(count = juiceCount, onIncrement = { juiceCount++ }, modifier = modifier)
    }

}