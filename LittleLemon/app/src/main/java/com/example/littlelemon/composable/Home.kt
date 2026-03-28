package com.example.littlelemon.composable

import android.R.attr.enabled
import android.util.Log
import androidx.collection.emptyLongSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.littlelemon.AppDatabase
import com.example.littlelemon.Menu
import com.example.littlelemon.R
import com.example.littlelemon.navigation.Profile
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.littlelemon.ui.theme.KarlaFontFamily
import com.example.littlelemon.ui.theme.LittleLemonColor
import com.example.littlelemon.ui.theme.MarkaziFontFamily
import kotlin.collections.emptyList
import kotlin.collections.mutableListOf


@Composable
fun Home(navController: NavHostController, database: AppDatabase) {
    val menuItemsDb by database.menuDao().getAll().observeAsState(emptyList())
    val categoriesDb by database.menuDao().getCategories().observeAsState(emptyList())
    var menuItems = menuItemsDb  // Esta es la lista que se puede filtrar u ordenar
    var searchPhrase by remember { mutableStateOf("") }
    var categoryItems by remember { mutableStateOf(emptyList<CategoryItem>()) }
    var selectedCategory by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableStateOf( -1 )}
    categoryItems = categoriesDb.map{
        CategoryItem(it, false )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeaderHome(navController)
        Hero(searchPhrase, onChangeValue = { searchPhrase = it })
        if (selectedCategory.isNotEmpty()) {  // filtro por categoria
            menuItems = menuItemsDb.filter { menu -> menu.category == selectedCategory }
        } else if (searchPhrase.isNotEmpty()) {
            menuItems = menuItemsDb.filter { it.title.contains(searchPhrase, ignoreCase = true) }
        } else {
            menuItems = menuItemsDb
        }

        CategoyItems(categoryItems, { it, index -> selectedCategory = it
                                    selectedIndex = index}, selectedIndex)
        HorizontalDivider(
            thickness = 1.dp,
            color = LittleLemonColor.cloud
        )
        AllMenu(menuItems)
    }
}


@Composable
fun Hero(searchPhrase: String, onChangeValue: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LittleLemonColor.green, shape = RectangleShape)
            .padding(all = 10.dp)
    ) {

        Text(
            text = stringResource(R.string.app_name),
            color = LittleLemonColor.yellow,
            fontWeight = FontWeight.Medium,
            fontFamily = MarkaziFontFamily,
            fontSize = 64.sp,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column( verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = stringResource(R.string.city),
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontFamily = MarkaziFontFamily,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = stringResource(id = R.string.short_description),
                    color = LittleLemonColor.cloud,
                    fontWeight = FontWeight.Medium,
                    fontFamily = MarkaziFontFamily,
                    fontSize = 26.sp,
                    modifier = Modifier.fillMaxWidth(0.6f)
                )
            }
            Image(
                painter = painterResource(R.drawable.hero_image),
                contentDescription = "hero image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

        }
        // Texto para la busqueda
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextField(
                value = searchPhrase,
                onValueChange = { onChangeValue(it) },
                placeholder = { Text(text = stringResource(R.string.enter_search_phrase)) },
                shape = RoundedCornerShape(10.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.search_24px),
                        contentDescription = "search icon",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
    //            trailingIcon = {
    //                if ( searchPhrase.isNotEmpty() ) {
    //                    IconButton( { onChangeValue("") } ) {
    //                        Icon( imageVector = Icons.de , contentDescription = "Delete text")
    //                    }
    //                }
    //            },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                modifier = Modifier.weight(1.0f)
            )
        }
    }
}

@Composable
fun HeaderHome(navController: NavHostController) {
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

        IconButton(
            onClick = {
                navController.navigate(Profile.route)
            }, modifier = Modifier
                .size(70.dp)
                .align(Alignment.CenterEnd)
        ) {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "Profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(64.dp)
            )
        }
    }
}

@Composable
fun CategoyItems(categoryItems: List<CategoryItem>, onSelect: (cat: String, ind: Int) -> Unit, selectedIndex: Int) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        itemsIndexed(
            items = categoryItems,
            key = { index, _ -> index },
        ) { index, item ->
            val backColor = if (index == selectedIndex)  Color(0xFFAFAFAF) else LittleLemonColor.cloud
            Text( text = item.category,
                fontFamily = KarlaFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xE6574949),
                fontSize = 20.sp,
                modifier = Modifier.background(backColor, shape = RoundedCornerShape(16.dp))
                    .clickable(enabled = true,
                    onClick = {
                        if (index == selectedIndex) {
                            onSelect("", -1)
                        } else {
                            onSelect(item.category, index)
                        }
                    })
                    .padding(8.dp)
                    )
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AllMenu(menuItems: List<Menu>) {  // parametro lista de items
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        items(items = menuItems, key = { item -> item.id }) { item ->
            var imgUrl = item.image.substringBefore("?")
            imgUrl = imgUrl.replace("blob", "refs/heads")
            imgUrl = imgUrl.replace("github", "raw.githubusercontent")
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight()
                ) {
                    Text(text = item.title, fontFamily = KarlaFontFamily,
                        fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = item.description, fontFamily = KarlaFontFamily,
                        fontSize = 16.sp, fontWeight = FontWeight.Normal)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "$%.2f".format(item.price), fontFamily = KarlaFontFamily,
                        fontSize = 20.sp, fontWeight = FontWeight.Bold)

                }
                GlideImage(
                    model = imgUrl, "imagen del menu",
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp),
                    contentScale = ContentScale.Fit,
                    failure = placeholder(R.drawable.logo)
                )
            }

            HorizontalDivider( // The modern equivalent of the deprecated Divider
                modifier = Modifier.padding(horizontal = 10.dp),
                thickness = 1.dp,
                color = LittleLemonColor.cloud
            )

        }
    }

}

data class CategoryItem(val category: String, val isSelected: Boolean = false)