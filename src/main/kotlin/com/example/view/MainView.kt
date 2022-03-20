package com.example.view

import com.example.Compteur
import com.example.CompteurModel
import com.example.Styles
import com.example.compteurActif
import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.control.ListView
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.scene.text.FontWeight
import javafx.stage.Modality
import javafx.stage.StageStyle
import tornadofx.*
import tornadofx.Stylesheet.Companion.listView

class MainView : View("KnitCompanion Pro") {

    val model: CompteurModel by inject()

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
                    hbox {
                        tableview(compteurActif) {

                            column("Nom du compteur", Compteur::nom)
                            column("Compteur à", Compteur::compteur)
                            column("Alerte", Compteur::alerte)
                            column("Nom de l'alerte", Compteur::nomAlerte)
                            column("Alerte au rang", Compteur::compteAlerte)

                            bindSelected(model)
                        }

                        form {
                            fieldset("Edition du compteur") {
                                field("Nom du compteur") {
                                    style {
                                        fontSize = 20.px
                                        fontWeight = FontWeight.BOLD
                                    }
                                    textfield(model.nom) { validator { if (it.isNullOrBlank()) error("Le champ Nom ne peut pas être vide") else null } }
                                }
                                field("Départ à") {
                                    textfield(model.compteur) {
                                        filterInput { it.controlNewText.isInt() }
                                        validator {
                                            if (it.isNullOrBlank()) error("Le champ Compteur doit être rempli") else null
                                        }
                                    }
                                }
                                field("Alerte")
                                {

                                }
                                field("Nom de l'alerte") {
                                    textfield(model.nomAlerte)
                                }
                                field("Alerte au rang") {
                                    textfield(model.compteAlerte)
                                    {
                                        filterInput { it.controlNewText.isInt() }
                                    }
                                }
                                button("Enregistrer") {
                                    enableWhen(model.dirty)
                                    action {
                                        model.commit()
                                        println("SAuvegarde de" + model.item.nom + model.item.compteur)
                                    }
                                }
                                button("Annuler").action {
                                    model.rollback()
                                }
                            }
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

}


//class CompteurCellFragment : ListCellFragment<Compteur>() {
//    private val compteurModel = CompteurModel().bindTo(this)
//
//
//    override val root =
//
//        form {
//            fieldset {
//                field("Nom du compteur") {
//                    style {
//                        fontSize = 20.px
//                        fontWeight = FontWeight.BOLD
//                    }
//                    label(compteurModel.nom) {
//                        style {
//                            fontSize = 20.px
//                            fontWeight = FontWeight.BOLD
//                        }
//                    }
//                }
//                field("Départ à") { label(stringBinding(compteurModel.compteur){value.toString()}) }
//                field("Alerte")
//                {
//                    val isAlerte = stringBinding(compteurModel.alerte){value.toString()}
//                    val isBoole = (isAlerte == "true")
//                    togglebutton (text = isAlerte, selectFirst = isBoole){
//                        action {
//                            if (isBoole) {
//                                text = "ON"
//                                isSelected = true
//                            } else {
//                                text = "OFF"
//                                isSelected = false
//                            }
//                        }
//                    }
//                }
//                field("Nom de l'alerte") { label(compteurModel.nomAlerte) }
//                field("Alerte au rang") { label(stringBinding(compteurModel.compteAlerte){value.toString()}) }
//            }
//        }
//}
//
