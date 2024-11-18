package com.example.ficharpg1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ficharpg1.model.dao.FichaDao
import com.example.ficharpg1.model.entity.Ficha
import kotlinx.coroutines.launch
import com.example.ficharpg1.model.Validation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FichaViewModel(private val fichaDao: FichaDao) : ViewModel() {

    private val _listaFichas = MutableStateFlow<List<Ficha>>(emptyList())
    val listaFichas: StateFlow<List<Ficha>> = _listaFichas

    init {
        carregarFichas()
    }

    // Agora é público
    fun carregarFichas() {
        viewModelScope.launch {
            _listaFichas.value = fichaDao.buscarTodos()
        }
    }

    fun salvarFicha(nome: String, classe: String, nivel: String, raca: String, xp: String, antecedencia: String,
                    tendencia: String, forca: String, destreza: String, constituicao: String, inteligencia:
                    String, sabedoria: String, carisma: String, proficiencia: String, ca: String, deslocamento: String, pv: String): String {
        if (!Validation.validarCampos(nome, classe, nivel, raca, xp, antecedencia, tendencia,
                forca, destreza, constituicao, inteligencia, sabedoria, carisma, proficiencia, ca, deslocamento, pv)) {
            return "em branco"
        }

        // Converte strings para inteiros
        val nivelInt = nivel.toIntOrNull() ?: 0
        val xpInt = xp.toIntOrNull() ?: 0
        val forcaInt = forca.toIntOrNull() ?: 0
        val destrezaInt = destreza.toIntOrNull() ?: 0
        val constituicaoInt = constituicao.toIntOrNull() ?: 0
        val inteligenciaInt = inteligencia.toIntOrNull() ?: 0
        val sabedoriaInt = sabedoria.toIntOrNull() ?: 0
        val carismaInt = carisma.toIntOrNull() ?: 0
        val caInt = proficiencia.toIntOrNull() ?: 0
        val deslocamentoInt = proficiencia.toIntOrNull() ?: 0
        val pvInt = proficiencia.toIntOrNull() ?: 0
        val proficienciaInt = proficiencia.toIntOrNull() ?: 0

        val ficha = Ficha(nome = nome,
            classe = classe,
            nivel = nivelInt,
            raca = raca,
            xp = xpInt,
            antecedencia = antecedencia,
            tendencia = tendencia,
            forca = forcaInt,
            destreza = destrezaInt,
            constituicao = constituicaoInt,
            inteligencia = inteligenciaInt,
            sabedoria = sabedoriaInt,
            carisma = carismaInt,
            proficiencia = proficienciaInt, ca = caInt, deslocamento = deslocamentoInt, pv = pvInt, anotacao = "", equipamentos = "")

        viewModelScope.launch {
            fichaDao.inserir(ficha)
            carregarFichas()
        }

        return "Ficha salva com sucesso"
    }

    fun excluirFicha(ficha: Ficha) {
        viewModelScope.launch {
            fichaDao.deletar(ficha)
            carregarFichas()
        }
    }

    fun atualizarFicha(ficha: Ficha): String {

        if (!Validation.validarCampos(ficha.nome, ficha.classe, ficha.nivel.toString(), ficha.raca, ficha.xp.toString(), ficha.antecedencia, ficha.tendencia,
                ficha.forca.toString(), ficha.destreza.toString(), ficha.constituicao.toString(), ficha.inteligencia.toString(), ficha.sabedoria.toString(), ficha.carisma.toString(), ficha.proficiencia.toString(), ficha.ca.toString(), ficha.deslocamento.toString(), ficha.pv.toString())) {

            return "em branco"
        }

        val fichaId = listaFichas.value.find { it.id == ficha.id } ?: return "Erro ao atualizar ficha"

        val fichaAtualizada = fichaId.copy(
            nome = ficha.nome,
            classe = ficha.classe,
            nivel = ficha.nivel,
            raca = ficha.raca,
            xp = ficha.xp,
            antecedencia = ficha.antecedencia,
            tendencia = ficha.tendencia,
            forca = ficha.forca,
            destreza = ficha.destreza,
            constituicao = ficha.constituicao,
            inteligencia = ficha.inteligencia,
            sabedoria = ficha.sabedoria,
            carisma = ficha.carisma,
            proficiencia = ficha.proficiencia,
            ca = ficha.ca,
            deslocamento = ficha.deslocamento,
            pv = ficha.pv,
            anotacao = ficha.anotacao,
            equipamentos = ficha.equipamentos
        )
        viewModelScope.launch {
            fichaDao.atualizar(fichaAtualizada)
            carregarFichas()
        }

        return "Ficha atualizada!"
    }
}
