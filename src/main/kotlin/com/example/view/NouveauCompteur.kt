package com.example.view

import com.example.Compteur
import com.example.CompteurModel
import com.example.CompteurRepetitif
import com.example.compteurActif
import javafx.geometry.HPos
import javafx.geometry.Pos
import javafx.geometry.VPos
import tornadofx.*
//import java.awt.TextField
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import org.w3c.dom.Text

class NouveauCompteur : Fragment("Nouveau compteur") {

    private val compteurModel : CompteurModel by inject()


    var compteurRepetitif = mutableListOf<CompteurRepetitif>()

    var boxRepetitif : VBox by singleAssign()
    var nomRepetitif : TextField by singleAssign()
    var compteRepetitif : TextField by singleAssign()
    var alerteRepetitif : Boolean = false
    var nomAlerteRepetitif : TextField by singleAssign()
    var compteAlerteRepetitif : TextField by singleAssign()

    override val root = gridpane {
        form {
            style {
                padding = box(10.px)
            }
            fieldset {

                field("Nom du compteur")
                    textfield(compteurModel.nom)

                field("Début du compte")
                textfield(compteurModel.compteur)
                {
                    filterInput { it.controlNewText.isInt() }
                }

                hbox(20) {
                    style { padding = box(10.px) }
                    field("Alerte")
                    togglebutton("OFF", selectFirst = false) {
                        action {
                            if (isSelected) {
                                text = "ON"
                                compteurModel.alerte.value = true
                            } else {
                                text = "OFF"
                                compteurModel.alerte.value = false
                            }
                        }
                    }
                }

                    field("Nom Alerte")
                    textfield(compteurModel.nomAlerte)

                    field("Alerte au rang")
                    textfield(compteurModel.compteAlerte)
                    {
                        filterInput { it.controlNewText.isInt() }
                    }


                        boxRepetitif = vbox {
                            style { isVisible = false }
                            field("Nom compteur récursif")
                        nomRepetitif = textfield()

                        field("Le compteur reviendra à 1 au rang")
                        compteRepetitif = textfield()

                        hbox(20) {
                            style { padding = box(10.px) }
                            field("Alerte")
                            togglebutton("OFF", selectFirst = false) {
                                action {
                                    if (isSelected) {
                                        text = "ON"
                                        alerteRepetitif = true
                                    } else {
                                        text = "OFF"
                                        alerteRepetitif = false
                                    }
                                }
                            }
                        }

                        field("Alerte au rang")
                        compteAlerteRepetitif = textfield()


                        field("Nom alerte")
                        nomAlerteRepetitif = textfield()



                    }
                hbox(20) {
                    style { padding = box(10.px) }
                    field("Ajouter un compteur récursif")
                    button("+") {
                        action {
                            boxRepetitif.isVisible = true

                        }}
                    button("-"){
                        action { boxRepetitif.isVisible = false }
                    }
                }
            }




            hbox(20,Pos.BOTTOM_CENTER) {

                button("OK") {

                    action {
                        compteurModel.commit()
                        compteurActif.add(
                            Compteur(
                                compteurModel.nom.value,
                                compteurModel.compteur.value,
                                isRepetitif = false,
                                compteurRepetitif,
                                compteurModel.alerte.value,
                                compteurModel.compteAlerte.value,
                                compteurModel.nomAlerte.value)


                            )

                        close() }
                }

                button("Annuler").action { close() }


            }
        }
    }
}

