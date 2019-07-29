package suporte;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

class Ambiente {
    private static String resourcesPath = System.getProperty("user.dir") + "/src/test/resources/";
    private static String jenkinsPath = resourcesPath+"jenkins/";
    private static String detailedHtmlReportFileName = "Relatorio_detalhado_testes.html";

    static String chromeDriverLocation = resourcesPath+"drivers/chromedriver.exe";
    static String anexosPath = jenkinsPath+"anexos/";
    static String detailedHtmlReportFileLoc = anexosPath + detailedHtmlReportFileName;
    static String jenkinsEvidenciasPath = jenkinsPath+"evidencias/";
    static String evidenciasPath = resourcesPath+"evidencias/";
    static String caminhoDeEvidenciasPeloNomeDaClasse;
    static String nomeDaEvidenciaDeTeste;

    void removeLog4J()
    {
        List<Logger> loggers = Collections.<org.testng.log4testng.Logger>list(LogManager.getCurrentLoggers());
        loggers.add(LogManager.getRootLogger());
        for (org.apache.log4j.Logger logger : loggers) {
            logger.setLevel(Level.OFF);
        }
    }
}
