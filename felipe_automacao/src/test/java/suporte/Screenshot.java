package suporte;

import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.sikuli.script.ScreenImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot {
    ExtentReport ex = new ExtentReport();

    private static String nomeComplementarDeScreenshotParaErros = "";


    void setNomeComplementarDeScreenshotParaErros(String nomeComplementarDeScreenshotParaErros) {
        Screenshot.nomeComplementarDeScreenshotParaErros = nomeComplementarDeScreenshotParaErros;
    }

    void print(boolean pularTestes, Date date){
        if (!pularTestes) {
            File screenshot = null;

            try {
                ex.getExtentTest().log(Status.PASS, "Tirando uma evidência");
            } catch (Exception ignored) {
            }
            if (Web.navegador != null) {
                screenshot = ((TakesScreenshot) Web.navegador).getScreenshotAs(OutputType.FILE);
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
            String formattedDate = df.format(date);
            String[] datesplit = formattedDate.split("-");
            String[] horas = datesplit[1].split("_");
            String path = Ambiente.evidenciasPath + Ambiente.caminhoDeEvidenciasPeloNomeDaClasse + datesplit[0] + "/" + horas[0] + "h" + horas[1] + "m" + horas[2] + "s" + "/";
            String arquivoSemNumeracao = path + Ambiente.nomeDaEvidenciaDeTeste + "/" + Ambiente.nomeDaEvidenciaDeTeste + "_screenshot_";
            int arquivo = 1;
            if (screenshot != null) {
                while (true) {
                    File f = new File(arquivoSemNumeracao + arquivo + ".png");
                    if (!f.exists()) {
                        break;
                    }
                    arquivo++;
                }
                try {
                    FileUtils.copyFile(screenshot, new File(arquivoSemNumeracao + arquivo + nomeComplementarDeScreenshotParaErros + ".png"));
                    File pathEvidenciasReport = new File(Ambiente.jenkinsEvidenciasPath);
                    if (pathEvidenciasReport.mkdirs()) {
                        System.out.println("Diretório '" + pathEvidenciasReport + "' criado");
                    }
                    FileUtils.copyFile(screenshot, new File(pathEvidenciasReport + "/" + Ambiente.nomeDaEvidenciaDeTeste + "_screenshot_" + arquivo + nomeComplementarDeScreenshotParaErros + ".png"));
                    try {
                        ex.getExtentTest().addScreenCaptureFromPath(Ambiente.jenkinsEvidenciasPath + "/" + Ambiente.nomeDaEvidenciaDeTeste + "_screenshot_" + arquivo + nomeComplementarDeScreenshotParaErros + ".png");
                    } catch (Exception ignored) {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
