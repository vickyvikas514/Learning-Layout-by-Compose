/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelab.basiclayouts

import android.app.Notification.Style
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MySootheApp() }
    }
}

// Step: Search bar - Modifiers
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    // Implement composable here
    //Text field give all the functionality like editing text etc..
    //we can use different modifiers in text field also..
    TextField(value = "",
        onValueChange ={}
        ,
        //used leading icon to make icon at the starting.

        leadingIcon = {
            androidx.compose.material.Icon(Icons.Default.Search, contentDescription = null )
        }
                ,
        //placeholder itself may be behave as
        //leading icon we put Text"content dissription" of icon 
        //in place holder
        placeholder = {
                      Text(stringResource(id = R.string.placeholder_search))
        },
        //Adapting background color for this
        colors = TextFieldDefaults.textFieldColors(
        //TextFieldDefaults have all the default values
        //in this case we change or overwrite color of background
        //eg;- white T shirt is default(come under TextFieldDefaults) and we make it black
        //we do not change jeans or caps.
        backgroundColor = MaterialTheme.colors.surface
        ),
        //min is used to set minimum height not fixed height
        modifier = modifier
            .heightIn(min = 56.dp)

            .fillMaxWidth(1f)

    )
}

// Step: Align your body - Alignment
@Composable
fun AlignYourBodyElement(
    //by using DrwableRes we can actually pass any image
    // to this function from the outside of the composable for eg:- from my phone.
    @DrawableRes drawble: Int,
    //same for StringRes
    @StringRes text:Int,
    modifier: Modifier = Modifier

) {
    // Implement composable here
    //use column as image and a text are present in coulmn
    Column(
        //it alling all the children of column horizontally.
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier) {

        //put a image
        Image(painterResource( id = drawble) ,
            contentDescription = null,
            contentScale = ContentScale.Crop
            //fix the size
            //clip modifer help to shapen the image.
                // by only using a clip it does not become fully circular,
            // it is cut from up and down. To make it fully circular We Use
            //image content scale parameter(uper dekh)
        , modifier = Modifier
                .size(88.dp)
                //to create shape of circle
                .clip(CircleShape)
        )

        Text(
            //put the text
            stringResource(id = text,
            ),
            //styling the text
            style = MaterialTheme.typography.h3,
            //adding space to the the text from base line
            // base line is a line on which text is suppose to be placed.
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp)
        )
    }
    
}

// Step: Favorite collection card - Material Surface
@Composable
fun FavoriteCollectionCard(


    @DrawableRes drawble: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    //we use surface because it create seperate surface for image and text so for eample
    // we can change color of them
    //like chatai(mat)
    Surface(
        //use shape parameter to bulid and define rounded corners should be small
        shape = MaterialTheme.shapes.small,
        modifier = modifier.clip(RoundedCornerShape(2.dp))
    ,
    ) {
        Row(
            //vertically aling the body
            verticalAlignment = Alignment.CenterVertically
            ,
            //fixing the width of box.
            modifier = Modifier.width(192.dp)
        ) {
            Image(painterResource(id = drawble),
                contentDescription = null
            ,
                contentScale = ContentScale.Crop
                ,

                //resize the image
            modifier = Modifier.size(56.dp))

            Text(stringResource(id = text)
                ,
                style   = MaterialTheme.typography.h3,
                //simple paddingn from horizontal.
                modifier = Modifier.padding(horizontal = 16.dp)
            )

        }

    }
}

// Step: Align your body row - Arrangements
@Composable
fun AlignYourBodyRow(

    modifier: Modifier = Modifier
) {
    //made a scrollable row view.
    LazyRow(
        //it create a 8dp gap between each component horizontally.
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        //content padding adds a extra gap in starting and end of scrollable list
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 5.dp),
        modifier = modifier){
        //allingyourbodydata is a mapping of allingyourbody element's image to text so we use item to call .
        //move your cursor on alignYourBodyData to see.
        items(alignYourBodyData){
            item -> AlignYourBodyElement(drawble = item.drawable, text =item.text )
        }
    }
}

// Step: Favorite collections grid - LazyGrid
@Composable
fun FavoriteCollectionsGrid(

    modifier: Modifier = Modifier
) {
    //we use LazyHorizontalGrid to impplement it .
    //GridCell defines the number of rows in grid .
    //if we choose GridCell.Adaptive then it adapts according to height.
    LazyHorizontalGrid(rows = GridCells.Fixed(2),
        //to control height
    modifier = modifier.height(120.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)

    ){
        items(favoriteCollectionsData){
            item -> FavoriteCollectionCard(drawble = item.drawable, text = item.text )
        }
    }

}

// Step: Home section - Slot APIs
@Composable
//adding title to different componenets
fun HomeSection(
    //we provide title to each element
    //sach batao tho zyada nhi pata title ka
    @StringRes title:Int,
    modifier: Modifier = Modifier,
    //we use anykind of composable elements
    content: @Composable () -> Unit
) {
    // we use slot Api which create slots in our UI.
    //for example search bar is a example of this .
    //we fill the slots of icon and text in search bar.

    //we use Column as title and composable element are vertical to each other.
    Column(modifier ) {
        //title is upper case and provoded by calling function
        Text(stringResource(id =  title).uppercase(Locale.getDefault())
        ,
        style = MaterialTheme.typography.h2,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
                .padding(horizontal = 16.dp)

        )

        content()
    }

}

// Step: Home screen - Scrolling
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
    ) {
    // placing all the components in home screen
    
    Column(
        modifier

            //provide vertical scrolling behaviour if screen size is not enough to see all elements
                //dont know about remember scroll state)
            .verticalScroll(rememberScrollState())
    ) {
        //we use spacer to create a empty composable which only take space
        //we use it so that our componennts dont get cut off during scrolling
        Spacer(modifier.height(16.dp))

        SearchBar(Modifier.padding(horizontal = 16.dp))
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow()
        }
        HomeSection(title = R.string.favorite_collections) {
           FavoriteCollectionsGrid()
        }

        Spacer(modifier.height(16.dp))
    }
}

// Step: Bottom navigation - Material
@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    // Implement composable here
}

// Step: MySoothe App - Scaffold
@Composable
fun MySootheApp() {
    // Implement composable here
}

private val alignYourBodyData = listOf(
    R.drawable.ab1_inversions to R.string.ab1_inversions,
    R.drawable.ab2_quick_yoga to R.string.ab2_quick_yoga,
    R.drawable.ab3_stretching to R.string.ab3_stretching,
    R.drawable.ab4_tabata to R.string.ab4_tabata,
    R.drawable.ab5_hiit to R.string.ab5_hiit,
    R.drawable.ab6_pre_natal_yoga to R.string.ab6_pre_natal_yoga
).map { DrawableStringPair(it.first, it.second) }

private val favoriteCollectionsData = listOf(
    R.drawable.fc1_short_mantras to R.string.fc1_short_mantras,
    R.drawable.fc2_nature_meditations to R.string.fc2_nature_meditations,
    R.drawable.fc3_stress_and_anxiety to R.string.fc3_stress_and_anxiety,
    R.drawable.fc4_self_massage to R.string.fc4_self_massage,
    R.drawable.fc5_overwhelmed to R.string.fc5_overwhelmed,
    R.drawable.fc6_nightly_wind_down to R.string.fc6_nightly_wind_down
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun SearchBarPreview() {
    MySootheTheme { SearchBar(Modifier.padding(8.dp)) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AlignYourBodyElementPreview() {
    MySootheTheme {
        AlignYourBodyElement(
            //it for example
            R.drawable.ab1_inversions,
            R.string.ab1_inversions,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FavoriteCollectionCardPreview() {
    MySootheTheme {
        FavoriteCollectionCard(
            R.drawable.fc2_nature_meditations,
            R.string.fc2_nature_meditations,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FavoriteCollectionsGridPreview() {
    MySootheTheme { FavoriteCollectionsGrid(

    ) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AlignYourBodyRowPreview() {
    MySootheTheme { AlignYourBodyRow() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun HomeSectionPreview() {
    MySootheTheme { HomeSection(
        R.string.align_your_body
       //we dont need to pass any modifier
    ){
        AlignYourBodyRow()
    } }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun ScreenContentPreview() {
    MySootheTheme { HomeScreen() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun BottomNavigationPreview() {
    MySootheTheme { SootheBottomNavigation(Modifier.padding(top = 24.dp)) }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MySoothePreview() {
    MySootheApp()
}
