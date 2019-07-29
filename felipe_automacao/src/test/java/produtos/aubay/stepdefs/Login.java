package produtos.aubay.stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.asserts.SoftAssert;
import suporte.Web;

import static produtos.aubay.objetos.Login.*;
import static produtos.aubay.objetos.Main.*;

public class Login extends Web {

    @Given("given! O usuário está no portal {string}")
    public void o_usuário_está_no_portal(String string) {
        navegar(string);
    }

    @Then("then! Os inputs não exibem mensagem de erro")
    public void os_inputs_não_exibem_mensagem_de_erro() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(existe(labelErroPassword), false);
        softAssert.assertEquals(existe(labelErroUser), false);
        softAssert.assertAll();
    }


    @When("and! O usuário insere a senha {string}")
    public void o_usuário_insere_a_senha_a(String string) {
        send(inputPassword, string);
    }

    @When("when! O usuário insere o usuário {string}")
    public void o_usuário_insere_o_usuário(String string) {
        send(inputUser, string);
    }

    @Then("then! Os inputs exibem mensagem de erro")
    public void osInputsExibemMensagemDeErro() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(existe(labelErroPassword), true);
        softAssert.assertEquals(existe(labelErroUser), true);
        softAssert.assertAll(); //vai dar erro por ser testes mockados
    }

    @Then("then! A página de login efetuado com sucesso é exibida")
    public void aPáginaDeLoginEfetuadoComSucessoÉExibida() {
        existe(h4LoginComSucesso);
    }

    @And("and! O usuário clica em realizar login")
    public void oUsuárioClicaEmRealizarLogin() {
        click(buttonLogin);
    }
}
