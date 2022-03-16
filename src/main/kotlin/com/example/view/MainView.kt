package com.example.view

import com.example.Compteur
import com.example.CompteurModel
import com.example.Styles
import com.example.compteurActif
import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.TabPane
import javafx.scene.text.FontWeight
import javafx.stage.Modality
import javafx.stage.StageStyle
import tornadofx.*

class MainView : View("KnitCompanion Pro") {
    override val root = borderpane {
        top {
            menubar {
                menu("Fichier") {
                    item("Nouveau projet")
                    item("Nouveau compteur").action {
                        find<NouveauCompteur>().openModal(
                            StageStyle.DECORATED,
                            Modality.APPLICATION_MODAL
                        )
                    }
                    separator()
                    item("Enregistrer", "Shortcut+S")
                    item("Quitter", "Shortcut+Q").action { close() }
                }
                menu("Aide") {
                    item("Aide en ligne", "F1")
                    item("A propos")
                }
            }
        }
        center {
            tabpane {
                tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                tab("Compteurs") {
                    listview(compteurActif) {

                        cellFragment(CompteurCellFragment::class)
                    }


                }

                tab("Diagramme") {
                    vbox {
                        label("Diagramme")
                    }
                }
            }
        }
    }
}

class CompteurCellFragment : ListCellFragment<Compteur>() {
    val compteurModel = CompteurModel().bindTo(this)


    override val root =

        form {
            fieldset {
                field("Nom du compteur") {
                    style {
                        fontSize = 20.px
                        fontWeight = FontWeight.BOLD
                    }
                    label(compteurModel.nom) {
                        style {
                            fontSize = 20.px
                            fontWeight = FontWeight.BOLD
                        }
                    }
                }
                field("Départ à") { label(compteurModel.compteur.value.toString()) }
                field("Alerte")
                {
                    label(compteurModel.alerte.value.toString())
                    togglebutton {
                        action {
                            if (compteurModel.alerte.value) {
                                text = "ON"
                                isSelected = true
                            } else {
                                text = "OFF"
                                isSelected = false
                            }
                        }
                    }
                }
                field("Nom de l'alerte") { label(compteurModel.nomAlerte) }
                field("Alerte au rang") { label(compteurModel.compteAlerte.value.toString()) }
            }
        }
}

