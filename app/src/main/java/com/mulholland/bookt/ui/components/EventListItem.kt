package com.mulholland.bookt.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mulholland.bookt.ui.models.DailyAvailability
import com.mulholland.bookt.ui.models.Event
import com.mulholland.bookt.ui.models.IdStatePair
import com.mulholland.bookt.ui.theme.BooktTheme

@Composable
fun EventListItem(
    event: Event? = null,
    onEditPressed: () -> Unit = {}
) {
    Surface(
        elevation = 2.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Column (){
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = onEditPressed) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit button")
                }
                if (event != null) {
                    Text(text = event.title ?: "", modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(), textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview
@Composable
fun TodoListItemPreview() {
    BooktTheme {
        EventListItem(event = Event(
            title = "Go pick up grandma",
            id = "fcs3k3lkm2309mgfs",
            availabilities = ArrayList<DailyAvailability>(0),
            userIds = ArrayList<String>(0)
        ))
    }
}