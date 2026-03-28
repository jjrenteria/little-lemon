package com.example.littlelemon.composable

import Header
import android.content.Context.MODE_PRIVATE
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.R
import com.example.littlelemon.navigation.Home
import com.example.littlelemon.ui.theme.PrimaryGreen
import kotlin.getValue
import androidx.core.content.edit
import com.example.littlelemon.navigation.Onboard
import com.example.littlelemon.ui.theme.KarlaFontFamily
import com.example.littlelemon.ui.theme.LittleLemonColor
import com.example.littlelemon.ui.theme.c1

@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences by lazy { context.getSharedPreferences("LittleLemon", MODE_PRIVATE) }
    val firstName  = sharedPreferences.getString("firsName", "") ?: ""
    val lastName = sharedPreferences.getString("lastName", "") ?: ""
    val email = sharedPreferences.getString("email","") ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 20.dp)
    ) {
        Header()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(150.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.personal_info),
                color = PrimaryGreen,
                fontWeight = FontWeight.Medium,
                fontFamily = KarlaFontFamily,
                fontSize = 40.sp
            )
        }
        Text(text = stringResource(R.string.first_name), color = c1 )
        OutlinedText(text = firstName)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = stringResource(R.string.last_name), color = c1 )
        OutlinedText(text = lastName)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = stringResource(R.string.email), color = c1 )
        OutlinedText(text = email)
        Spacer(modifier = Modifier.fillMaxHeight(0.8f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            onClick = {
                sharedPreferences.edit(commit = true) { clear() }
                navController.navigate(Onboard.route)

            }
        ) {
            Text(
                text = stringResource(R.string.logout),
                fontFamily = KarlaFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = LittleLemonColor.charcoal
            )
        }
    }
}

@Composable
fun OutlinedText(text: String) {
    Text(text = text,
        fontFamily = KarlaFontFamily,
        fontWeight = FontWeight.Bold,
        color = c1,
        modifier = Modifier.fillMaxWidth()
            .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = c1)
            .padding(all = 10.dp)
    )
}

