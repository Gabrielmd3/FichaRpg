package com.example.ficharpg1.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ficharpg1.model.database.AppDatabase
import com.example.ficharpg1.model.entity.Ficha
import com.example.ficharpg1.view.ui.theme.FichaRPG1Theme
import com.example.ficharpg1.viewmodel.FichaViewModel
import com.example.ficharpg1.viewmodel.FichaViewModelFactory

class CriacaoActivity : ComponentActivity() {

    private val fichaViewModel: FichaViewModel by viewModels {
        val dao = AppDatabase.getDatabase(applicationContext).fichaDao()
        FichaViewModelFactory(dao)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ficha = intent.getSerializableExtra("ficha") as Ficha
        val editar = ficha.id != 0

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
                                title = {
                                    Text(
                                        text = if (editar) "Editar Ficha" else "Criar Ficha",
                                        color = Color.White
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { finish() }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Voltar",
                                            tint = Color.White
                                        )
                                    }
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        },
                        containerColor = MaterialTheme.colorScheme.background
                    ) { innerPadding ->
                        CriacaoScreen(
                            modifier = Modifier.padding(innerPadding),
                            fichaViewModel = fichaViewModel,
                            ficha = ficha,
                            editar = editar
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CriacaoScreen(
    modifier: Modifier = Modifier,
    fichaViewModel: FichaViewModel,
    ficha: Ficha,
    editar: Boolean
) {
    var nome by remember { mutableStateOf(ficha.nome) }
    var classe by remember { mutableStateOf(ficha.classe) }
    var nivel by remember { mutableStateOf(ficha.nivel.toString()) }
    var raca by remember { mutableStateOf(ficha.raca) }
    var xp by remember { mutableStateOf(ficha.xp.toString()) }
    var antecedencia by remember { mutableStateOf(ficha.antecedencia) }
    var tendencia by remember { mutableStateOf(ficha.tendencia) }
    var forca by remember { mutableStateOf(ficha.forca.toString()) }
    var destreza by remember { mutableStateOf(ficha.destreza.toString()) }
    var constituicao by remember { mutableStateOf(ficha.constituicao.toString()) }
    var inteligencia by remember { mutableStateOf(ficha.inteligencia.toString()) }
    var sabedoria by remember { mutableStateOf(ficha.sabedoria.toString()) }
    var carisma by remember { mutableStateOf(ficha.carisma.toString()) }
    var proficiencia by remember { mutableStateOf(ficha.proficiencia.toString()) }
    var ca by remember { mutableStateOf(ficha.ca.toString()) }
    var deslocamento by remember { mutableStateOf(ficha.deslocamento.toString()) }
    var pv by remember { mutableStateOf(ficha.pv.toString()) }
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Informações Gerais",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = classe,
            onValueChange = { classe = it },
            label = { Text("Classe") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = nivel,
            onValueChange = { if (it.all { char -> char.isDigit() }) nivel = it },
            label = { Text("Nível") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = raca,
            onValueChange = { raca = it },
            label = { Text("Raça") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = xp,
            onValueChange = { if (it.all { char -> char.isDigit() }) xp = it },
            label = { Text("XP") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = antecedencia,
            onValueChange = { antecedencia = it },
            label = { Text("Antecedência") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = tendencia,
            onValueChange = { tendencia = it },
            label = { Text("Tendência") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "Atributos",
            fontSize = 22.sp,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = forca,
                onValueChange = { if (it.all { char -> char.isDigit() }) forca = it },
                label = { Text("Força") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = destreza,
                onValueChange = { if (it.all { char -> char.isDigit() }) destreza = it },
                label = { Text("Destreza") },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = constituicao,
                onValueChange = { if (it.all { char -> char.isDigit() }) constituicao = it },
                label = { Text("Constituição") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = inteligencia,
                onValueChange = { if (it.all { char -> char.isDigit() }) inteligencia = it },
                label = { Text("Inteligência") },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = sabedoria,
                onValueChange = { if (it.all { char -> char.isDigit() }) sabedoria = it },
                label = { Text("Sabedoria") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = carisma,
                onValueChange = { if (it.all { char -> char.isDigit() }) carisma = it },
                label = { Text("Carisma") },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }

        TextField(
            value = proficiencia,
            onValueChange = { if (it.all { char -> char.isDigit() }) proficiencia = it },
            label = { Text("Proficiência") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        TextField(
            value = ca,
            onValueChange = { if (it.all { char -> char.isDigit() }) ca = it },
            label = { Text("CA") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        TextField(
            value = deslocamento,
            onValueChange = { if (it.all { char -> char.isDigit() }) deslocamento = it },
            label = { Text("Deslocamento") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        TextField(
            value = pv,
            onValueChange = { if (it.all { char -> char.isDigit() }) pv = it },
            label = { Text("PV") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = {
                val sucesso = if (editar) {
                    ficha.nome = nome
                    ficha.classe = classe
                    ficha.nivel = nivel.toInt()
                    ficha.raca = raca
                    ficha.xp = xp.toInt()
                    ficha.antecedencia = antecedencia
                    ficha.tendencia = tendencia
                    ficha.forca = forca.toInt()
                    ficha.destreza = destreza.toInt()
                    ficha.constituicao = constituicao.toInt()
                    ficha.inteligencia = inteligencia.toInt()
                    ficha.sabedoria = sabedoria.toInt()
                    ficha.carisma = carisma.toInt()
                    ficha.proficiencia = proficiencia.toInt()
                    ficha.ca = ca.toInt()
                    ficha.deslocamento = deslocamento.toInt()
                    ficha.pv = pv.toInt()

                    fichaViewModel.atualizarFicha(ficha)
                } else {
                    fichaViewModel.salvarFicha(
                        nome, classe, nivel, raca, xp, antecedencia, tendencia,
                        forca, destreza, constituicao, inteligencia, sabedoria, carisma, proficiencia, ca, deslocamento, pv
                    )
                }

                if (sucesso == "em branco") {
                    Toast.makeText(context, "Erro, digite todas as informações!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        if (editar) "Ficha atualizada com sucesso" else "Ficha criada com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    context.startActivity(Intent(context, MainActivity::class.java))
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = if (editar) Icons.Filled.CheckCircle else Icons.Filled.AddCircle,
                contentDescription = if (editar) "Editar" else "Criar"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (editar) "Salvar Alterações" else "Criar Ficha")
        }
    }
}
