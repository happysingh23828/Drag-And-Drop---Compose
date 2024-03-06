package com.example.draganddrop_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.draganddrop_compose.ui.theme.DragAndDropComposeTheme
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

class MainActivity : ComponentActivity() {

    private val viewModel: DragAndDropViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DragAndDropComposeTheme {
                DragAndDropHorizontal(vm = viewModel)
            }
        }
    }
}


@Composable
private fun DragAndDropHorizontal(
    modifier: Modifier = Modifier,
    vm: DragAndDropViewModel,
) {
    val state =
        rememberReorderableLazyListState(onMove = vm::moveDog, canDragOver = vm::isDogDragEnabled)
    LazyRow(
        state = state.listState,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .reorderable(state)
            .fillMaxWidth()
    ) {

        items(vm.dogs, { item -> item.key }) { item ->
            if (item.isLocked) {
                Column(
                    modifier = Modifier
                        .width(65.dp)
                        .padding(horizontal = 6.dp)
                        .height(80.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Text(
                        text = item.text,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                ReorderableItem(state, item.key) { dragging ->
                    val elevation = animateDpAsState(if (dragging) 8.dp else 0.dp, label = "")
                    Column(
                        modifier = Modifier
                            .detectReorderAfterLongPress(state)
                            .shadow(elevation.value)
                            .border(
                                3.dp,
                                if (dragging) Color.Blue else Color.LightGray,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 6.dp)
                            .width(65.dp)
                            .height(80.dp)
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Text(
                            text = item.text,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
