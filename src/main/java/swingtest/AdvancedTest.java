package swingtest;

import core.Log4RQ;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class AdvancedTest {

    private WebDriver driver;


    public void launchApplication(String url) throws MalformedURLException {
        driver = new RemoteWebDriver(
                new URL("http://" + url),
                new DesiredCapabilities("java", "1.0", org.openqa.selenium.Platform.ANY)
        );

        String title = driver.getTitle();
        log("Found title is " + title);

        List<WebElement> listElem = driver.findElements(By.cssSelector("button"));
        WebElement b1 = listElem.get(0);
        String text = b1.getText();
        String sRet = b1.getAttribute("enabled");
        sRet = b1.getAttribute("uiClassID");
        log("uiClassID = " + sRet);

        sRet = b1.getAttribute("width");
        log("Width = " + sRet);


        WebElement wElem = driver.findElement(By.cssSelector("text-area"));
        wElem.sendKeys("The Rolling Stones rocked the world!");

        WebElement wElem2 = driver.findElement(By.cssSelector("button[text='Reset']"));
        String id1 = ((RemoteWebElement) wElem2).getId();
        sRet = wElem2.getAttribute("uiClassID");
        log("uiClassID = " + sRet);

        sRet = wElem2.getAttribute("width");
        log("Width = " + sRet);
        //wElem2.click();

        WebElement wElem3 = driver.findElement(By.cssSelector("text-field"));
        wElem3.sendKeys("test de text");


        String szMyText = wElem3.getText();
        System.out.println("CAPTURED TEXT IS: " + szMyText);
        
    }

    private void log(String sz){
        System.out.println(Log4RQ.ANSI_BLUE + sz + Log4RQ.ANSI_RESET);
    }


    public static void main(String[] args) {
        String szURL = "127.0.0.1:44444";
        if (args.length > 0){
            System.out.println("IP to connect to is " + args[0]);
            szURL = args[0];
        }

        AdvancedTest app = new AdvancedTest();
        try {
            app.launchApplication(szURL);
        } catch(Exception exp){
            System.out.println("Exception occurred while connecting to JavaDriver [ "
                    + exp.getMessage() + " ]");
        }
    }


}
