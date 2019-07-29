package produtos.aubay.suites;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/produtos/aubay/features",
        glue={"produtos.aubay.stepdefs"},
        plugin = { "suporte.ListenerCucumber" }//,
        //tags = {"@InputPositivo"}
)

public class Runner {

}

