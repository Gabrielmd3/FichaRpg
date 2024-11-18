package com.example.ficharpg1.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ficharpg1.model.entity.Ficha
import com.example.ficharpg1.view.ui.theme.FichaRPG1Theme

class VisualizacaoActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
                                title = { Text("Visualização da Ficha", color = Color.White) },
                                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                                navigationIcon = {
                                    IconButton(onClick = {
                                        Intent(this, MainActivity::class.java).apply {
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
                                }
                            )
                        },
                        bottomBar = {
                            Button(
                                onClick = {
                                    // Vai para anotações/equipamentos
                                    val intent = Intent(this, EquipAnotActivity::class.java).apply {
                                        putExtra("ficha", ficha)
                                    }
                                    startActivity(intent)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .background(MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Anotações/Equipamentos")
                            }
                        }
                    ) { innerPadding ->
                        FichaView(ficha = ficha, modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaView(ficha: Ficha, modifier: Modifier = Modifier) {
    // Usando a rolagem vertical
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Permite rolagem
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Info", fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        FichaRowInfo(firstTitle = "Nome", firstValue = ficha.nome, secondTitle = "Classe", secondValue = "${ficha.classe} (Nível ${ficha.nivel})")
        FichaRowInfo(firstTitle = "Raça", firstValue = ficha.raca, secondTitle = "XP", secondValue = ficha.xp.toString())
        FichaRowInfo(firstTitle = "Antecedência", firstValue = ficha.antecedencia, secondTitle = "Tendência", secondValue = ficha.tendencia)

        Divider()

        Text("Atributos", fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        FichaRowInfo(firstTitle = "Força", firstValue = ficha.forca.toString(), secondTitle = "Destreza", secondValue = ficha.destreza.toString())
        FichaRowInfo(firstTitle = "Constituição", firstValue = ficha.constituicao.toString(), secondTitle = "Inteligência", secondValue = ficha.inteligencia.toString())
        FichaRowInfo(firstTitle = "Sabedoria", firstValue = ficha.sabedoria.toString(), secondTitle = "Carisma", secondValue = ficha.carisma.toString())

        Divider()

        FichaRowInfo(firstTitle = "CA", firstValue = ficha.ca.toString(), secondTitle = "PV", secondValue = ficha.pv.toString())
        FichaRowInfo(firstTitle = "Deslocamento", firstValue = "${ficha.deslocamento} m", secondTitle = "Proficiência", secondValue = "+${ficha.proficiencia}")
    }
}

@Composable
fun FichaRowInfo(firstTitle: String, firstValue: String, secondTitle: String, secondValue: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = firstTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = firstValue,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = secondTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = secondValue,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
