package com.example.randomnamesapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity
import com.example.randomnamesapp.ui.theme.RandomNamesAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            RandomNamesAppTheme {

                val randomName by viewModel.name.collectAsStateWithLifecycle()

                val genders by viewModel.genders.collectAsStateWithLifecycle()
                val origins by viewModel.origins.collectAsStateWithLifecycle()

                val message by viewModel.message.collectAsStateWithLifecycle()

                var selectedGender by remember { mutableIntStateOf(1) }

                val originsSelected = remember { mutableStateListOf<Int>() }

                if (origins.isNotEmpty()) {
                    origins.forEach {
                        originsSelected.add(it.id!!)
                    }
                }

                LaunchedEffect(message) {
                    if (message.isNotEmpty()) {
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                        viewModel.clearMessage()
                    }
                }

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
                                shape = ShapeDefaults.Medium,
                                onClick = {
                                    if (originsSelected.isEmpty()) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Select at least one category",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@Button
                                    }

                                    viewModel.getRandomName(selectedGender, originsSelected)
                                },
                                modifier = Modifier.padding(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0XFF0d80f2)
                                )
                            ) {
                                Text(
                                    "Generate Name", fontSize = 24.sp,
                                    modifier = Modifier.padding(8.dp),
                                    color = Color.White
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

                        Spacer(modifier = Modifier.height(24.dp))

                        Text("Name Generator", fontSize = 28.sp, fontWeight = SemiBold)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(randomName, fontSize = 24.sp, fontWeight = SemiBold)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Radio Buttons for gender selection
                        if (genders.isNotEmpty()){
                            GenderSelection(
                                genders = genders,
                                onGenderSelected = {
                                    selectedGender = it
                                })
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (origins.isNotEmpty()) {
                                CategorySelection( // Checkbox for category selection
                                    origins = origins,
                                    originsSelected = originsSelected,
                                    modifyOriginSelected = {
                                            id, isChecked ->
                                        if (isChecked) {
                                            originsSelected.add(id)
                                        } else {
                                            originsSelected.remove(id)
                                        }
                                    },
                                    modifyAllOriginsSelected = { noneSelected ->
                                        if (noneSelected) {
                                            originsSelected.clear()
                                            origins.forEach {
                                                originsSelected.add(it.id!!)
                                            }
                                        } else {
                                            originsSelected.clear()
                                        }
                                    })
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun CategorySelection(
        origins: List<OriginEntity>,
        originsSelected: List<Int>,
        modifyAllOriginsSelected: (Boolean) -> Unit,
        modifyOriginSelected: (id: Int,Boolean) -> Unit
    ) {
        Text("Categories or Origins", fontSize = 20.sp, fontWeight = SemiBold)

        Spacer(modifier = Modifier.height(16.dp))

        ButtonsSelector(
            selectAllFunc = {
                if (originsSelected.size == origins.size) return@ButtonsSelector // If all categories are already selected so don't execute the for
                modifyAllOriginsSelected(true)
            },
            unselectAllFunc = {
                if (originsSelected.isEmpty()) return@ButtonsSelector // If all categories are already unselected so don't execute the for
                modifyAllOriginsSelected(false)
            }
        )

        FlowRow(
            maxItemsInEachRow = 2,
            modifier = Modifier.fillMaxWidth(),
        ) {
            origins.forEach {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f) // Cada checkbox ocupará la mitad del ancho
                        .padding(vertical = 4.dp) // Espacio entre filas
                ) {
                    CheckboxDefault(
                        text = it.name,
                        checked = it.id in originsSelected,
                        onCheckedChange = { isChecked ->
                            modifyOriginSelected(it.id!!, isChecked)
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun CheckboxDefault(
        text: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(it)
                }
            )
            Text(
                text = text,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ButtonsSelector(
    selectAllFunc: () -> Unit,
    unselectAllFunc: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()

    ) {
        Button(
            shape = ShapeDefaults.Medium,
            onClick = {
                selectAllFunc.invoke()
            },
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0XFFe7edf4)
            )
        ) {
            Text(
                "Select All", color = Color.Black,
                fontWeight = FontWeight.Bold, fontSize = 16.sp
            )
        }

        Button(
            shape = ShapeDefaults.Medium,
            onClick = {
                unselectAllFunc.invoke()
            },
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0XFFe7edf4)
            )
        ) {
            Text(
                "Unselect All", color = Color.Black,
                fontWeight = FontWeight.Bold, fontSize = 16.sp
            )
        }
    }
}

@Composable
fun GenderSelection(
    genders: List<GenderEntity>,
    onGenderSelected: (Int) -> Unit
) {
    var selectedGender by remember { mutableStateOf(genders[0].id) }

    Text("Gender", fontSize = 20.sp, fontWeight = SemiBold)

    Spacer(modifier = Modifier.height(16.dp))

    FlowRow(
        //maxItemsInEachRow = 2,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        genders.forEach { gender ->
            if (gender.id == 4) return@forEach
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (selectedGender == gender.id) Color(0XFF0d80f2) else Color.Transparent)
                    .border(
                        1.dp,
                        shape = ShapeDefaults.Medium,
                        color = Color(0XFFcedbe8)
                    )
                    .padding(16.dp)

            ) {
                Text(
                    text = gender.label,
                    fontSize = 18.sp,
                    color = if (selectedGender == gender.id) Color.White else MaterialTheme.colorScheme.onBackground,
                    fontWeight = if (selectedGender == gender.id) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = Modifier.clickable(
                        indication = null,              // ⛔ No ripple effect
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        selectedGender = gender.id
                        onGenderSelected(gender.id ?: 1)
                    })

            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


