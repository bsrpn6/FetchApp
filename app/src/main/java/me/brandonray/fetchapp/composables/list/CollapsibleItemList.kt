package me.brandonray.fetchapp.composables.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import me.brandonray.fetchapp.models.Item

@Composable
fun CollapsibleItemList(items: List<Item>, modifier: Modifier = Modifier) {
    val (expandedListId, setExpandedListId) = remember { mutableStateOf<Int?>(null) }
    val groupedItems = items.groupBy { it.listId }

    Column(modifier = modifier) {
        groupedItems.forEach { (listId, listItems) ->
            ListHeader(
                listId = listId,
                isExpanded = expandedListId == listId,
                onHeaderClick = {
                    setExpandedListId(if (expandedListId == listId) null else listId)
                }
            )

            if (expandedListId == listId) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false)
                ) {
                    ScrollableItemList(listItems)
                }
            }
        }
        if (expandedListId == null) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}