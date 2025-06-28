package com.example.kmpapp.android.coreui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlin.math.roundToInt

@Composable
fun <T> DropDownMenu(
    modifier: Modifier = Modifier,
    opened: Boolean = false,
    menuItems: List<T>,
    maxDropdownHeight: Dp = Dp.Unspecified,
    widthMultiplier: Float = 1f,
    onStateChange: (Boolean) -> Unit,
    onItemSelected: (Int, T) -> Unit,
    header: @Composable BoxScope.(animation: State<Float>) -> Unit,
    item: @Composable BoxScope.(T) -> Unit,
){
    var openedState = remember{ mutableStateOf(false) }

    var width by remember { mutableIntStateOf(0) }
    val animationProgress = animateFloatAsState(
        targetValue = if (opened) 1f else 0f,
        animationSpec = tween(200)
    )



    val popupVisible by remember{
        derivedStateOf {
            animationProgress.value > 0f
        }
    }

    val clickableHeader: @Composable () -> Unit = {
        Box(modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    val state = !openedState.value
                    openedState.value = state
                    onStateChange.invoke(state)
                }
            )
        ){
            header(animationProgress)
        }
    }

    Box(modifier = modifier
        .onGloballyPositioned {
            if(it.size.width != 0){
                width = it.size.width
            }
        }
    ){
        OutlinedCard {
            clickableHeader()
        }
        if(popupVisible){
            MenuDropDownPopup(
                onDismissRequest = {
                    openedState.value = false
                    onStateChange.invoke(false)
                },
                maxDropdownHeight = maxDropdownHeight,
                widthMultiplier = widthMultiplier,
                animationProgress = animationProgress,
                width = with(LocalDensity.current) { width.toDp() },
                header = clickableHeader,
                menuItems = menuItems,
                onItemSelected = onItemSelected,
                item = item
            )
        }
    }
}

@Composable
fun <T> MenuDropDownPopup(
    onDismissRequest: () -> Unit,
    animationProgress: State<Float>,
    width: Dp,
    maxDropdownHeight: Dp = Dp.Unspecified,
    widthMultiplier: Float = 1f,
    header: @Composable () -> Unit,
    menuItems: List<T>,
    onItemSelected: (Int, T) -> Unit,
    item: @Composable BoxScope.(T) -> Unit,
){
    val widthPx = with(LocalDensity.current){ width.toPx() }
    Popup(
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
        alignment = Alignment.TopEnd
    ) {
        var desiredHeight by remember { mutableStateOf(Dp.Unspecified) }

            Box(modifier = Modifier
                .width(width * widthMultiplier)
                .heightIn(desiredHeight, desiredHeight),
                contentAlignment = Alignment.TopEnd
            ) {
                OutlinedCard(
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                ) {
                val shape = MaterialTheme.shapes.large
                Layout(modifier = Modifier
                    .graphicsLayer {
                        this.shadowElevation = 3.dp.toPx() * animationProgress.value
                        this.shape = shape
                    }
                    .heightIn(max = maxDropdownHeight)
                    .clip(MaterialTheme.shapes.large)
                    .verticalScroll(rememberScrollState())
                    .background(color = MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.large),
                    content = {
                        header()
                        Column {
                            menuItems.forEachIndexed { index, item ->
                                if(index == 0) HorizontalDivider()
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = LocalIndication.current,
                                        role = Role.Button
                                    ) {
                                        onItemSelected(index, item)
                                        onDismissRequest()
                                    },
                                ) {
                                    item(item)
                                }
                                HorizontalDivider()
                            }
                        }
                    }
                ) { measurable, rawConstraints ->
                    val animatedWidth = (widthPx + widthPx * (widthMultiplier - 1f) * animationProgress.value).roundToInt()
                    val constraints = rawConstraints.copy(
                        minWidth = animatedWidth,
                        maxWidth = animatedWidth
                    )
                    val placeables = measurable.map { it.measure(constraints) }
                    val headerHeight = placeables[0].height
                    val listHeight = placeables[1].height
                    if(!desiredHeight.isSpecified){
                        val contentHeight = if(maxDropdownHeight.isSpecified){
                            listHeight.coerceAtMost(maxDropdownHeight.roundToPx())
                        }else listHeight
                        desiredHeight = (headerHeight + contentHeight).toDp()
                    }
                    val animatedHeight = (headerHeight + listHeight*animationProgress.value).roundToInt()
                    layout(width = animatedWidth, height = animatedHeight){
                        placeables.fold(0){ acc, placeable ->
                            placeable.place(0, acc)
                            acc + placeable.height
                        }
                    }
                }
            }
        }

    }
}