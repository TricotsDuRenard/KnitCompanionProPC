package com.example

import javafx.beans.property.BooleanProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*


var compteurActif = mutableListOf<Compteur>().asObservable()

//@Serializable
class Compteur(
    var nom: String,
    var compteur: Int,
    var isRepetitif: Boolean = false,
    var compteursRepetitif: MutableList<CompteurRepetitif>,
    var alerte: Boolean = false,
    var compteAlerte: Int,
    var nomAlerte: String
) {


    fun incremente() {
        compteur++
        if (isRepetitif) {
            for (i in 0 until compteursRepetitif.size) {
                compteursRepetitif[i].compteur++
                if (compteursRepetitif[i].compteur == compteursRepetitif[i].compteRAZ + 1) compteursRepetitif[i].compteur =
                    1
            }
        }
    }

    fun decremente() {
        compteur--
        if (compteur < 0) compteur = 0
        if (isRepetitif) {
            for (i in 0 until compteursRepetitif.size) {
                compteursRepetitif[i].compteur--
                if (compteursRepetitif[i].compteur == 0) compteursRepetitif[i].compteur = 1
            }
        }
    }


    companion object {
        var nom: String? = null
        var compteur: Int = 0
        var repetitif: Boolean = false
        var compteursRepetitif = mutableListOf(CompteurRepetitif)
        var alerte: Boolean = false
        var compteAlerte: Int = 0
        var nomAlerte: String? = ""


    }

}

//@Serializable
class CompteurRepetitif(
    var nom: String,
    var compteur: Int,
    var compteRAZ: Int,
    var alerte: Boolean,
    var compteAlerte: Int,
    var nomAlerte: String
) {

    companion object {
        var nom: String? = null
        var compteur: Int = 0
        var compteRAZ: Int = 0
        var alerte: Boolean = false
        var compteAlerte: Int = 0
    }
}

class CompteurModel : ItemViewModel<Compteur>() {
    var nom = bind(Compteur::nom)
    var compteur = bind(Compteur::compteur) as IntegerProperty
    var alerte = bind(Compteur::alerte) as BooleanProperty
    var compteAlerte = bind(Compteur::compteAlerte) as IntegerProperty
    var nomAlerte = bind(Compteur::nomAlerte)
}

