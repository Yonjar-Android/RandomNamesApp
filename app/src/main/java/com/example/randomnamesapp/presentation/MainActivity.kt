package com.example.randomnamesapp.presentation

import android.content.Context
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
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.randomnamesapp.R
import com.example.randomnamesapp.data.database.entities.GenderEntity
import com.example.randomnamesapp.data.database.entities.OriginEntity
import com.example.randomnamesapp.ui.theme.RandomNamesAppTheme
import com.example.randomnamesapp.utils.OriginStrings
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

                val selectCatMsg = stringResource(R.string.selectCatStr)

                val windowsSizeClass =
                    currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass

                when (windowsSizeClass) {
                    WindowWidthSizeClass.COMPACT -> {
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
                                                    selectCatMsg,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                return@Button
                                            }

                                            viewModel.getRandomName(
                                                selectedGender, originsSelected,
                                                context = this@MainActivity
                                            )
                                        },
                                        modifier = Modifier.padding(8.dp).testTag("GenerateButton"),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0XFF0d80f2)
                                        )
                                    ) {
                                        Text(
                                            stringResource(R.string.generateStr), fontSize = 24.sp,
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
                                    .padding(innerPadding),
                                horizontalAlignment = Alignment.CenterHorizontally,

                                ) {

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    stringResource(R.string.nameGeneratorStr),
                                    fontSize = 28.sp,
                                    fontWeight = SemiBold
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    if (randomName != "Random Name") randomName else stringResource(
                                        R.string.randomStr
                                    ), fontSize = 24.sp, fontWeight = SemiBold
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Radio Buttons for gender selection
                                if (genders.isNotEmpty()) {
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
                                    if(origins.isNotEmpty()) {
                                        CategorySelection( // Checkbox for category selection
                                            origins = origins,
                                            originsSelected = originsSelected,
                                            context = this@MainActivity,
                                            modifyOriginSelected = { id, isChecked ->
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

                    WindowWidthSizeClass.MEDIUM -> {
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
                                                    selectCatMsg,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                return@Button
                                            }

                                            viewModel.getRandomName(
                                                selectedGender, originsSelected,
                                                context = this@MainActivity
                                            )
                                        },
                                        modifier = Modifier.padding(8.dp).testTag("GenerateButton"),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0XFF0d80f2)
                                        )
                                    ) {
                                        Text(
                                            stringResource(R.string.generateStr), fontSize = 24.sp,
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
                                    .padding(innerPadding),
                                horizontalAlignment = Alignment.CenterHorizontally,

                                ) {

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    stringResource(R.string.nameGeneratorStr),
                                    fontSize = 28.sp,
                                    fontWeight = SemiBold
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    if (randomName != "Random Name") randomName else stringResource(
                                        R.string.randomStr
                                    ), fontSize = 24.sp, fontWeight = SemiBold
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Radio Buttons for gender selection
                                if (genders.isNotEmpty()) {
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
                                            context = this@MainActivity,
                                            modifyOriginSelected = { id, isChecked ->
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

                    WindowWidthSizeClass.EXPANDED -> {
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
                                                    selectCatMsg,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                return@Button
                                            }

                                            viewModel.getRandomName(
                                                selectedGender, originsSelected,
                                                context = this@MainActivity
                                            )
                                        },
                                        modifier = Modifier.padding(8.dp).testTag("GenerateButton"),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0XFF0d80f2)
                                        )
                                    ) {
                                        Text(
                                            stringResource(R.string.generateStr), fontSize = 24.sp,
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
                                    .padding(innerPadding),
                                horizontalAlignment = Alignment.CenterHorizontally,

                                ) {

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.fillMaxSize().weight(1f),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center) {
                                        Text(
                                            stringResource(R.string.nameGeneratorStr),
                                            fontSize = 28.sp,
                                            fontWeight = SemiBold
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Text(
                                            if (randomName != "Random Name") randomName else stringResource(
                                                R.string.randomStr
                                            ), fontSize = 24.sp, fontWeight = SemiBold
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        // Radio Buttons for gender selection
                                        if (genders.isNotEmpty()) {
                                            GenderSelection(
                                                genders = genders,
                                                onGenderSelected = {
                                                    selectedGender = it
                                                })
                                        }

                                        Spacer(modifier = Modifier.height(16.dp))
                                    }

                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .weight(1f)
                                            .verticalScroll(rememberScrollState()),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        if (origins.isNotEmpty()) {
                                            CategorySelection( // Checkbox for category selection
                                                origins = origins,
                                                originsSelected = originsSelected,
                                                context = this@MainActivity,
                                                modifyOriginSelected = { id, isChecked ->
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
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun CategorySelection(
        origins: List<OriginEntity>,
        originsSelected: List<Int>,
        modifyAllOriginsSelected: (Boolean) -> Unit,
        modifyOriginSelected: (id: Int, Boolean) -> Unit,
        context: Context
    ) {
        Text(stringResource(R.string.categoriesOriginsStr), fontSize = 20.sp, fontWeight = SemiBold)

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
                        modifier = Modifier.testTag("Origin${it.id}"),
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
        modifier: Modifier = Modifier,
        text: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit
    ) {
        val value = originName(text)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                modifier = modifier,
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(it)
                }
            )
            Text(
                text = if (value == 0) text else stringResource(id = value),
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
                stringResource(R.string.selectAllStr), color = Color.Black,
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
                stringResource(R.string.unselectAllStr), color = Color.Black,
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

    Text(stringResource(R.string.genderStr), fontSize = 20.sp, fontWeight = SemiBold)

    Spacer(modifier = Modifier.height(16.dp))

    FlowRow(
        //maxItemsInEachRow = 2,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        genders.forEach { gender ->
            if (gender.id == 4) return@forEach

            val value = originGender(gender.label)

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
                    text = if (value == 0) gender.label else stringResource(id = value),
                    fontSize = 18.sp,
                    color = if (selectedGender == gender.id) Color.White else MaterialTheme.colorScheme.onBackground,
                    fontWeight = if (selectedGender == gender.id) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = Modifier.testTag("Gender${gender.id}")
                        .clickable(
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

fun originName(originName: String): Int {
    val resId = OriginStrings.map[originName] ?: 0
    return resId
}

fun originGender(genderName: String): Int {
    val resId = OriginStrings.mapGenders[genderName] ?: 0
    return resId
}


