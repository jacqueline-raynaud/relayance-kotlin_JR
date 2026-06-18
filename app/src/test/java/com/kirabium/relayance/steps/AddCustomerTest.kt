package com.kirabium.relayance.steps

import com.kirabium.relayance.domain.usecases.AddCustomerUseCase
import com.kirabium.relayance.domain.validator.EmailValidator
import com.kirabium.relayance.ui.customeradd.AddCustomerViewModel
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.fr.Alors
import io.cucumber.java.fr.Et
import io.cucumber.java.fr.Quand
import io.cucumber.java.fr.Soit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat


class AddCustomerTest {
    private lateinit var repository: FakeCustomerRepository
    private lateinit var viewModel: AddCustomerViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = FakeCustomerRepository()
        viewModel = AddCustomerViewModel(
            AddCustomerUseCase(repository, EmailValidator())
            )
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Soit("un formulaire d'ajout de client vide")
    fun formulaire_vide() { // etat initial
    }

    @Quand("je saisis le nom {string} et l'email {string}")
    fun je_saisie(nom: String, email: String) {
        viewModel.onNameChange(nom)
        viewModel.onEmailChange(email)
    }

    @Et("je valide l'ajout")
    fun je_valide() {
        viewModel.save()
    }

    @Alors("le client {string} est présent dans la liste des clients")
    fun client_est_present(nom: String) {
        assertThat(repository.customers.map { it.name }).contains(nom)
    }

    @Alors("aucune erreur d'email n'est affichée")
    fun aucune_erreur() {
        assertThat(viewModel.uiState.value.emailError).isFalse()
    }

    @Alors("une erreur d'email est affichée")
    fun une_erreur_affichee() {
        assertThat(viewModel.uiState.value.emailError).isTrue()
    }

    @Alors("la liste des clients reste inchangée")
    fun liste_clients_inchangee() {
        assertThat(repository.customers).isEmpty()
    }
}
