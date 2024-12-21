import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jumatexpress.Logbook

//package com.example.jumatexpress.screen.list.components
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun LogbookCard(
//    entry: LogbookEntry,
//    onClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxWidth()
//            .clickable(onClick = onClick),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//        shape = RoundedCornerShape(16.dp) // Add rounded corners with a 16.dp radius
//    ) {
//        Column(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            // Image section - now full width and taller
////            Image(
////                contentDescription = null,
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .height(150.dp),
////                contentScale = ContentScale.Crop
////            )
//
//            // Content section
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Column(
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(
//                            text = entry.date,
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                        Text(
//                            text = entry.activity,
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier.padding(vertical = 4.dp)
//                        )
//                    }
//
//                    StatusChip(status = entry.status)
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(
//                    text = "${entry.workHours} jam",
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
//        }
//    }
//}
//
//@Composable
//private fun StatusChip(status: String) {
//    Surface(
//        modifier = Modifier
//            .padding(4.dp)
//            .height(32.dp), // Slightly increased height for better readability
//        shape = MaterialTheme.shapes.medium, // Rounded shape for a smoother look
//        color = when (status.lowercase()) {
//            "approved" -> MaterialTheme.colorScheme.primaryContainer
//            "rejected" -> MaterialTheme.colorScheme.errorContainer
//            else -> MaterialTheme.colorScheme.secondaryContainer
//        },
//        shadowElevation = 4.dp // Added slight shadow for depth effect
//    ) {
//        Row(
//            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = status,
//                style = MaterialTheme.typography.labelLarge.copy( // Larger text style
//                    fontWeight = FontWeight.SemiBold // Bolder for emphasis
//                ),
//                color = when (status.lowercase()) {
//                    "approved" -> MaterialTheme.colorScheme.onPrimaryContainer
//                    "rejected" -> MaterialTheme.colorScheme.onErrorContainer
//                    else -> MaterialTheme.colorScheme.onSecondaryContainer
//                }
//            )
//        }
//    }
//}

@Composable
fun LogbookCard(logbook: Logbook, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Tanggal: ${logbook.tanggal}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Topik: ${logbook.topik_pekerjaan}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Deskripsi: ${logbook.deskripsi}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
