package suporte;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Date;

import static org.junit.Assert.assertEquals;


public class Web {
    protected static WebDriver navegador = null;
    private ExtentReport ex = new ExtentReport();
    private static String proximaPagina, pararProximaPagina;
    private static boolean pularTestes = true;
    private static Date date = new Date();
    private static int tempoMaximoDeEsperaFixo = 10000;


    private static Actions a =null;


    private void abrirNavegadorChrome(){
        System.setProperty("webdriver.chrome.driver", Ambiente.chromeDriverLocation);
        navegador = new ChromeDriver();
        navegador.manage().window().maximize();
    }

    protected void navegar(String url){
        if (!pularTestes) {
            if (String.valueOf(navegador).contains("null")){
                abrirNavegadorChrome();
            }
            navegador.get(url);
        }
    }

    protected void click(String xpath){
        if (!pularTestes) {
            tratarErro(xpath, ()-> navegador.findElement(By.xpath(xpath)).click());
        }
    }

    protected void send(String xpath, String text){
        if (!pularTestes) {
            tratarErro(xpath, ()-> navegador.findElement(By.xpath(xpath)).sendKeys(text));
        }
    }

    protected boolean existe(String xpath){
        final boolean[] existe = {false};
        if (!pularTestes) {
            try{tratarErro(xpath, ()->{
                navegador.findElement(By.xpath(xpath));
                existe[0] = true;
            });
            }
            catch (Exception ignored){
            }
        }
        return existe[0];
    }


    protected void fecharNavegador(){
        navegador.quit();
    }

    private void tratarErro(String xpath, Runnable runnable){
        try{
            runnable.run();
        }catch (org.openqa.selenium.WebDriverException e){
            int tempoMaximoDeEspera = tempoMaximoDeEsperaFixo;
            while (tempoMaximoDeEspera > 0) {
                try{
                    runnable.run();
                    break;}
                catch (Exception ignored){}
                if (e.getMessage().contains("element not visible"))
                {
                    JavascriptExecutor js = (JavascriptExecutor) navegador;
                    js.executeScript("arguments[0].scrollIntoView()", navegador.findElement(By.xpath(xpath)));
                    js.executeScript("window.scrollBy(0,-150)");
                }
                try{
                    navegador.findElement(By.xpath(pararProximaPagina));
                    throw new WebDriverException("O elemento '"+xpath+"' não foi encontrado");}
                catch (Exception ignored){}
                try{
                    navegador.findElement(By.xpath(proximaPagina)).click(); }
                catch (Exception ignored){}
                tempoMaximoDeEspera = tempoMaximoDeEspera - 100;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    void definindoNome(String caminho, String nome) {
        Ambiente.caminhoDeEvidenciasPeloNomeDaClasse = caminho;
        Ambiente.nomeDaEvidenciaDeTeste = nome;
    }

    void clearStart(){
        //pularTestes = false;
    }

    void print(){
        new Screenshot().print(false, date);
    }

}
