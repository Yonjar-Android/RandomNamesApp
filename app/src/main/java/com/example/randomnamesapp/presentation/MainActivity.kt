package com.example.randomnamesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomnamesapp.Categories.Arabic
import com.example.randomnamesapp.Categories.English
import com.example.randomnamesapp.Categories.French
import com.example.randomnamesapp.Categories.German
import com.example.randomnamesapp.Categories.Italian
import com.example.randomnamesapp.Categories.Spanish
import com.example.randomnamesapp.Gender
import com.example.randomnamesapp.ui.theme.RandomNamesAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomNamesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                    ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,

                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Random Name", fontSize = 24.sp, fontWeight = SemiBold)

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { },

                            ) {
                            Text(
                                "Generate", fontSize = 24.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        GenderSelection()

                        Spacer(modifier = Modifier.height(16.dp))

                        CategorySelection()
                    }
                }
            }
        }
    }
}

@Composable
fun GenderSelection() {
    val genderList = listOf(Gender.Male, Gender.Female, Gender.Unisex, Gender.All)
    var selectedGender by remember { mutableStateOf(Gender.Male) }

    Text("Gender", fontSize = 20.sp, fontWeight = SemiBold)

    Row {
        genderList.forEach { gender ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(4.dp)
            ) {
                RadioButton(
                    selected = selectedGender == gender,
                    onClick = { selectedGender = gender }
                )

                Text(
                    text = gender.name, fontSize = 18.sp,
                    modifier = Modifier.clickable(
                        indication = null,              // ⛔ No ripple effect
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        selectedGender = gender
                    })
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategorySelection() {
    val categoryList = listOf(German, Spanish, English, French, Arabic, Italian)
    Text("Categories", fontSize = 20.sp, fontWeight = SemiBold)

    Spacer(modifier = Modifier.height(16.dp))

    FlowRow {
        categoryList.forEach {
            CheckboxDefault(text = it.name)
        }
    }
}

@Composable
fun CheckboxDefault(
    text: String
) {
    val checkedState = remember { mutableStateOf(true) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it }
        )
        Text(text = text, fontSize = 18.sp)
    }
}

