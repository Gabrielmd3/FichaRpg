package com.example.ficharpg1.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ficharpg1.model.database.AppDatabase
import com.example.ficharpg1.model.entity.Ficha
import com.example.ficharpg1.view.ui.theme.FichaRPG1Theme
import com.example.ficharpg1.viewmodel.FichaViewModel
import com.example.ficharpg1.viewmodel.FichaViewModelFactory

class EquipAnotActivity : ComponentActivity() {
    private val fichaViewModel: FichaViewModel by viewModels {
        val dao = AppDatabase.getDatabase(applicationContext).fichaDao()
        FichaViewModelFactory(dao)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recebe o objeto da Intent
        val ficha = intent.getSerializableExtra("ficha") as Ficha

        setContent {
            FichaRPG1Theme {
                val colors = MaterialTheme.colorScheme.copy(
                    background = Color.White,
                    surface = Color.White,
                    onBackground = Color.Black,
                    onSurface = Color.Black
                )

                MaterialTheme(colorScheme = colors) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = { Text("Equipamentos e Anotações", color = Color.White) },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        Intent(this, VisualizacaoActivity::class.java).apply {
                                            putExtra("ficha", ficha)
                                            startActivity(this)
                                            finish()
                                        }

                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Voltar",
                                            tint = Color.White
                                        )
                                    }
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary // Roxo padrão
                                )
                            )
                        },
                        containerColor = MaterialTheme.colorScheme.background // Define o fundo geral como branco
                    ) { innerPadding ->
                        EquipAnotScreen(
                            modifier = Modifier.padding(innerPadding),
                            ficha = ficha,
                            fichaViewModel = fichaViewModel
                        )
                    }
                }
            }
        }

    }
}


@Composable
fun EquipAnotScreen(modifier: Modifier = Modifier, ficha: Ficha, fichaViewModel: FichaViewModel) {
    var equipamentos by remember { mutableStateOf(ficha.equipamentos) }
    var anotacoes by remember { mutableStateOf(ficha.anotacao) }

    var isEditingEquipamentos by remember { mutableStateOf(false) }
    var isEditingAnotacoes by remember { mutableStateOf(false) }

    // Scrollable column to allow the whole screen to be scrollable
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState), // Enable scrolling for the entire screen
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Campo para Equipamentos
        Text(text = "Equipamentos", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)

        if (isEditingEquipamentos) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, shape = MaterialTheme.shapes.small)
            ) {
                BasicTextField(
                    value = equipamentos,
                    onValueChange = { equipamentos = it },
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 120.dp) // Reduce the height to fit in limited space
                        .verticalScroll(rememberScrollState()) // Enable scrolling within the text field
                        .padding(8.dp)
                )
            }
            IconButton(onClick = {
                isEditingEquipamentos = false
                ficha.equipamentos = equipamentos
                fichaViewModel.atualizarFicha(ficha)
            }, modifier = Modifier.align(Alignment.End)) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Salvar Equipamentos",
                    tint = Color.Black
                )
            }
        } else {
            Text(
                text = equipamentos,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            )
            IconButton(onClick = {
                isEditingEquipamentos = true
            }, modifier = Modifier.align(Alignment.End)) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar Equipamentos",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Reduced space to fit both fields

        // Campo para Anotações
        Text(text = "Anotações", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)

        if (isEditingAnotacoes) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, shape = MaterialTheme.shapes.small)
            ) {
                BasicTextField(
                    value = anotacoes,
                    onValueChange = { anotacoes = it },
                    textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 120.dp) // Reduced height to allow both fields
                        .verticalScroll(rememberScrollState()) // Enable scrolling within the text field
                        .padding(8.dp)
                )
            }
            IconButton(onClick = {
                isEditingAnotacoes = false
                ficha.anotacao = anotacoes
                fichaViewModel.atualizarFicha(ficha)
            }, modifier = Modifier.align(Alignment.End)) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Salvar Anotações",
                    tint = Color.Black
                )
            }
        } else {
            Text(
                text = anotacoes,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            )
            IconButton(onClick = {
                isEditingAnotacoes = true
            }, modifier = Modifier.align(Alignment.End)) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar Anotações",
                    tint = Color.Black
                )
            }
        }
    }
}

