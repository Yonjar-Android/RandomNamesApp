package com.example.randomnamesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity
import com.example.randomnamesapp.ui.theme.RandomNamesAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomNamesAppTheme {

                val randomName by viewModel.name.collectAsStateWithLifecycle()

                val genders by viewModel.genders.collectAsStateWithLifecycle()
                val origins by viewModel.origins.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .windowInsetsPadding(
                                    WindowInsets.systemBars
                                )
                        )
                        {
                            Button(
                                onClick = { viewModel.getRandomName() },
                                modifier = Modifier.padding(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0XFF0d80f2)
                                )
                            ) {
                                Text(
                                    "Get Random Name", fontSize = 24.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Name Generator", fontSize = 28.sp, fontWeight = SemiBold)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(randomName, fontSize = 24.sp, fontWeight = SemiBold)

                        Spacer(modifier = Modifier.height(16.dp))

                        if (genders.isNotEmpty()) GenderSelection(genders) // Radio Buttons for gender selection

                        Spacer(modifier = Modifier.height(16.dp))

                        if (origins.isNotEmpty()) CategorySelection(origins) // Checkbox for category selection

                    }
                }
            }
        }
    }
}

@Composable
fun GenderSelection(
    genders: List<GenderEntity>,
) {
    var selectedGender by remember { mutableStateOf(genders[0].id) }

    Text("Gender", fontSize = 20.sp, fontWeight = SemiBold)

    FlowRow(
        maxItemsInEachRow = 2,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        genders.forEach { gender ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(4.dp)
            ) {
                RadioButton(
                    selected = selectedGender == gender.id,
                    onClick = { selectedGender = gender.id }
                )

                Text(
                    text = gender.label, fontSize = 18.sp,
                    modifier = Modifier.clickable(
                        indication = null,              // ⛔ No ripple effect
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        selectedGender = gender.id
                    })
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategorySelection(
    origins: List<OriginEntity>
) {
    Text("Categories", fontSize = 20.sp, fontWeight = SemiBold)

    Spacer(modifier = Modifier.height(16.dp))

    FlowRow(
        maxItemsInEachRow = 2,
        modifier = Modifier.fillMaxWidth()
    ) {
        origins.forEach {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f) // Cada checkbox ocupará la mitad del ancho
                    .padding(vertical = 4.dp) // Espacio entre filas
            ) {
                CheckboxDefault(text = it.name)
            }
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
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it }
        )
        Text(
            text = text,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


