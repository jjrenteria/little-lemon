import android.content.Context
import android.util.Log
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.PrimaryGreen
import com.example.littlelemon.navigation.Home
import com.example.littlelemon.ui.theme.KarlaFontFamily
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Onboarding(navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(LittleLemonColor.green)
                .padding(all = 20.dp)

        ) {
            Text(
                text = stringResource(R.string.know_you),
                color = LittleLemonColor.cloud,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontFamily = KarlaFontFamily,
                fontSize = 40.sp,
//                style = MaterialTheme.typography.headlineSmall
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.personal_info),
            fontWeight = FontWeight.Medium,
            fontFamily = KarlaFontFamily,
            fontSize = 30.sp,
            color = LittleLemonColor.charcoal,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { it -> firstName = it },
            label = { Text(text = stringResource(R.string.first_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { it -> lastName = it },
            label = { Text(text = stringResource(R.string.last_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            )
        )

        OutlinedTextField(
            value = email,
            onValueChange = { it -> email = it },
            label = { Text(text = stringResource(R.string.email)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 40.dp, bottom = 40.dp),
            onClick = {
                if (validateUserInfo(firstName, lastName, email)) {
                    saveUserInfo(firstName, lastName, email, context)
                    Toast.makeText(context, R.string.register_successful, Toast.LENGTH_SHORT).show()
                    navController.navigate(Home.route)
                } else {
                    Toast.makeText(context, R.string.register_nok, Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text(
                text = stringResource(R.string.register),
                fontFamily = KarlaFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = LittleLemonColor.charcoal
            )
        }
    }

}

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Little lemon logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight()
                .align(Alignment.Center)
        )
    }
}

fun validateUserInfo(first: String, last: String, email: String): Boolean {
    val valido = !(first.isEmpty() || last.isEmpty() || email.isEmpty())
    return valido
}

fun saveUserInfo(first: String, last: String, email: String, context: Context) {

    context.getSharedPreferences("LittleLemon", MODE_PRIVATE).edit() {
        putString("firsName", first)
        putString("lastName", last)
        putString("email", email)
        commit()
    }
}

