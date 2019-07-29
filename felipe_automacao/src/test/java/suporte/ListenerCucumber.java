package suporte;


import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import cucumber.api.PickleStepTestStep;

import cucumber.api.event.*;
import java.util.*;


public class ListenerCucumber implements ConcurrentEventListener {
    private static boolean jacomecou = false;


    private final EventHandler<TestStepStarted> testStepStartedHandler = new EventHandler<TestStepStarted>() {
        @Override
        public void receive(TestStepStarted event) {
            if (event.testStep instanceof PickleStepTestStep) {
                PickleStepTestStep testStep = (PickleStepTestStep) event.testStep;
                Class<? extends com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel> classe =
                        testStep.getStepText().toLowerCase().contains("given") ? Given.class :
                        testStep.getStepText().toLowerCase().contains("when") ? When.class :
                        testStep.getStepText().toLowerCase().contains("then") ? Then.class : And.class;
                System.out.println("step: "+testStep.getStepText());
                String[] passo = testStep.getStepText().split("!");

                new ExtentReport().createAcao(classe, passo[1]);
            }
        }
    };

    private final EventHandler<TestStepFinished> testStepFinishedHandler = new EventHandler<TestStepFinished>() {
        @Override
        public void receive(TestStepFinished event) {
            if (event.testStep instanceof PickleStepTestStep) {
                if (event.result.getStatus().toString().equals("FAILED")){
                    new Screenshot().setNomeComplementarDeScreenshotParaErros("_erro");
                    new ExtentReport().setInfo("Ocorreu uma falha, tentativa de evidência de erro foi feita!");
                    new ExtentReport().setError(event.result.getError());
                    new Web().print();
                }
                if (event.result.getStatus().toString().equals("SKIPPED")){
                    new ExtentReport().setIgnore("Ação ignorada");
                }
            }
        }
    };

    private final EventHandler<TestCaseStarted> testCaseStartedHandler = new EventHandler<TestCaseStarted>() {
        @Override
        public void receive(TestCaseStarted event) {
            nomeCt.setLength(0);
            setCaminhoEvidenciaNomeArquivo(event);
            setAutorCategoriaENome(event.testCase.getName().split("(?<=,)"));
            new Web().definindoNome(caminhoEvidencia, nomeArquivo);
            new Web().clearStart();
            new ExtentReport()
                    .setNomeCTDescricao(nomeCt.toString(), nomeCt.toString())
                    .setAuthorAndCategory(autor, categoria)
            ;
            if (!jacomecou) {
                new ExtentReport().createInstance();
                new Ambiente().removeLog4J();
                new ExtentReport().createFeature(nomeArquivo);
                jacomecou = true;
            }

            new ExtentReport().createScenario(nomeCt.toString());
        }
    };

    private static String caminhoEvidencia = "";
    private static String nomeArquivo = "";

    private void setCaminhoEvidenciaNomeArquivo(TestCaseStarted event){
        List<String> tt = Arrays.asList(event.testCase.getUri().split("/"));
        caminhoEvidencia = event.testCase.getUri().replace("file:src/test/java/", "").replace(tt.get(tt.size()-1), "");
        nomeArquivo = tt.get(tt.size()-1).replace(".feature", "");
    }


    private static String autor="";
    private static String categoria="";
    private static StringBuilder nomeCt = new StringBuilder();

    private void setAutorCategoriaENome(String[] nomeCompleto){
        for (String parte:nomeCompleto) {
            if (parte.toLowerCase().contains("autor")){
                autor = parte.replace("autor", "").replace("Autor", "").replace(":", "").replace(",", "").trim();
            }
            if (parte.toLowerCase().contains("categoria")){
                categoria = parte.replace("categoria", "").replace("Categoria", "").replace(":", "").replace(",", "").trim();
            }
            if (!parte.toLowerCase().contains("categoria")&&!parte.toLowerCase().contains("autor")){
                nomeCt.append(parte);
            }
        }
        if (nomeCt.toString().endsWith(",")){
            nomeCt.delete(nomeCt.length()-1, nomeCt.length());
        }
    }

    private final EventHandler<TestCaseFinished> testCaseFinishedHandler = new EventHandler<TestCaseFinished>() {
        @Override
        public void receive(TestCaseFinished event) {
        }
    };

    private EventHandler<TestRunFinished> runFinishHandler = new EventHandler<TestRunFinished>() {

        @Override
        public void receive(TestRunFinished event) {
            new ExtentReport().flushreport();
        }
    };


    @Override
    public void setEventPublisher(final EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, testCaseStartedHandler);
        publisher.registerHandlerFor(TestCaseFinished.class, testCaseFinishedHandler);
        publisher.registerHandlerFor(TestStepStarted.class, testStepStartedHandler);
        publisher.registerHandlerFor(TestStepFinished.class, testStepFinishedHandler);
        publisher.registerHandlerFor(TestRunFinished.class, runFinishHandler);
    }
}