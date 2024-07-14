package com.balaban.patientmonitoring

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun HorizontalScreenItem (){
    LazyColumn{
        items(list) {
            ImageItems(it)
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )
        }
    }
}



@Composable
private fun ImageItems(imageUrl: String) {
    AsyncImage(model = imageUrl, contentDescription = "image", modifier = Modifier.fillMaxWidth())
}