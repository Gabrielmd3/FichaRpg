package com.example.ficharpg1.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.ficharpg1.model.database.AppDatabase
import com.example.ficharpg1.model.entity.Ficha
import com.example.ficharpg1.viewmodel.FichaViewModel
import com.example.ficharpg1.viewmodel.FichaViewModelFactory

class MainActivity : ComponentActivity() {
    private val fichaViewModel: FichaViewModel by viewModels {
        val dao = AppDatabase.getDatabase(applicationContext).fichaDao()
        FichaViewModelFactory(dao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InicioLayout(fichaViewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        // Recarrega os dados ao retornar à atividade principal
        fichaViewModel.carregarFichas()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioLayout(fichaViewModel: FichaViewModel) {
    // Observa o fluxo de lista de fichas do ViewModel
    val listaFichas = fichaViewModel.listaFichas.collectAsState(emptyList()).value
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Faixa de título
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "FICHAS",
                fontSize = 25.sp,
                color = White
            )
        }

        // Lista de fichas
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            items(listaFichas) { ficha ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "${ficha.nome} (${ficha.classe})",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable {
                            val intent = Intent(context, VisualizacaoActivity::class.java).apply {
                                putExtra("ficha", ficha)
                            }
                            context.startActivity(intent)
                        }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                val intent = Intent(context, CriacaoActivity::class.java).apply {
                                    putExtra("ficha", ficha)
                                }
                                context.startActivity(intent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Editar",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(
                            onClick = { fichaViewModel.excluirFicha(ficha) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Excluir",
                                tint = Color.Red
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Botão para adicionar nova ficha
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, CriacaoActivity::class.java).apply {
                        putExtra("ficha", Ficha())
                    }
                    context.startActivity(intent)
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("+", fontSize = 18.sp, color = White)
            }
        }
    }
}
