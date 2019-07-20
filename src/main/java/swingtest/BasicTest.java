package swingtest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class BasicTest {

    private WebDriver driver;


    public void launchApplication(String url) throws MalformedURLException {
        driver = new RemoteWebDriver(
                //new URL("http://10.20.1.176:53611"),
                //new URL("http://10.20.0.204:53611"),
                new URL("http://" + url),
                new DesiredCapabilities("java", "1.0", org.openqa.selenium.Platform.ANY)
        );

        String title = driver.getTitle();
        System.out.println("Found title is " + title);

    }

    public static void main(String[] args) {
        String szURL = "127.0.0.1:53611";
        if (args.length > 0){
            System.out.println("IP to connect to is " + args[0]);
            szURL = args[0];
        }

        BasicTest app = new BasicTest();
        try {
            app.launchApplication(szURL);
        } catch(Exception exp){
            System.out.println("Exception occurred while connecting to JavaDriver [ "
                    + exp.getMessage() + " ]");
        }
    }


}
