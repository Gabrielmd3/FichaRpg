package com.example.ficharpg1.model

class Validation {

    companion object {


        fun validarCampos(
            nome: String,
            classe: String,
            nivel: String,
            raca: String,
            xp: String,
            antecedencia: String,
            tendencia: String,
            forca: String,
            destreza: String,
            constituicao: String,
            inteligencia: String,
            sabedoria: String,
            carisma: String,
            proficiencia: String,
            ca: String,
            deslocamento: String,
            pv: String
        ): Boolean {

            return nome.isNotBlank() && classe.isNotBlank() && nivel.isNotBlank() && raca.isNotBlank() &&
                    xp.isNotBlank() && antecedencia.isNotBlank() && tendencia.isNotBlank() && forca.isNotBlank() &&
                    destreza.isNotBlank() && constituicao.isNotBlank() && inteligencia.isNotBlank() &&
                    sabedoria.isNotBlank() && carisma.isNotBlank() && proficiencia.isNotBlank() && ca.isNotBlank() && deslocamento.isNotBlank() && pv.isNotBlank()

        }
    }
}