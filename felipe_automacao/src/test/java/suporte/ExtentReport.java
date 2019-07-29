package suporte;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

class ExtentReport {

    private static ExtentReports extent = null;
    private static String nomeCT;
    private static String descricaoCT;
    private static ExtentTest extentTest = null;
    private static ExtentTest feature = null;
    private static ExtentTest scenario = null;
    private static ExtentTest childTest = null;
    private static String autor;
    private static String categoria;

    void createInstance() {
        String fileName = Ambiente.detailedHtmlReportFileLoc;
        File pastaAnexo = new File(Ambiente.anexosPath);
        System.setProperty("file.encoding","UTF-8");
        Field charset = null;
        try {
            charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null,null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (!pastaAnexo.mkdirs()) {
            System.out.println("Não foi possível criar o diretório: "+Ambiente.anexosPath);
        }
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Relatório de Testes");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Relatório de Testes");
        htmlReporter.config().setTimeStampFormat("yyyy/MM/dd - HH:mm:ss.SSS");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Sistema operacional", System.getProperty("os.name"));
        extent.setSystemInfo("Arquitetura do OS", System.getProperty("os.arch"));
        extent.setSystemInfo("Versão do OS", System.getProperty("os.version"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Usuário de execução", System.getProperty("user.name"));
    }


    ExtentReport setNomeCTDescricao(String nome, String descricao){
        nomeCT = nome;
        descricaoCT = descricao;
        return this;
    }

    void setAuthorAndCategory(String author, String category){
        autor = author;
        categoria = category;
    }


    ExtentTest getExtentTest(){
        if (childTest==null) {
            return extentTest;
        }else {
            return childTest;
        }
    }

    void createFeature(String nomeArquivo){
        feature = extent.createTest(Feature.class,nomeArquivo);
    }


    void createScenario(String nomeChild){
        scenario = feature.createNode(Scenario.class, nomeChild);
        scenario.assignAuthor(autor);
        scenario.assignCategory(categoria);
    }

    void createAcao(Class<? extends com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel> classe, String descricao){
        childTest = scenario.createNode(classe, descricao);
    }

    ExtentReport setError(Throwable error){
        if (childTest==null) {
            extentTest.fail(error);
        }else{
            childTest.fail(error);
        }
        return this;
    }

    void setIgnore(String error){
        if (childTest==null) {
            extentTest.log(Status.SKIP, error);
        }else {
            childTest.log(Status.SKIP, error);
        }
    }

    ExtentReport setInfo(String info){
        if (childTest==null) {
            extentTest.log(Status.INFO, info);
        }else {
            childTest.log(Status.INFO, info);
        }
        return this;
    }

    void flushreport() {
        if (!String.valueOf(Web.navegador).contains("null")) {
            Capabilities cap = ((RemoteWebDriver) Web.navegador).getCapabilities();
            extent.setSystemInfo("Navegador", cap.getBrowserName());
            extent.setSystemInfo("Versão do webdriver", cap.getVersion());
        }
        if (extent!=null) {
            extent.flush();
        }
    }
}
