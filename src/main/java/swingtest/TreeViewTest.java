package swingtest;

import core.Log4RQ;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class TreeViewTest {

    private WebDriver driver;


    public void launchApplication(String url) throws MalformedURLException {
        driver = new RemoteWebDriver(
                new URL("http://" + url),
                new DesiredCapabilities("java", "1.0", org.openqa.selenium.Platform.ANY)
        );

        String title = driver.getTitle();
        log("Found title is " + title);

        WebElement wTree = driver.findElement(By.cssSelector("tree"));

        WebElement root = wTree.findElement(By.cssSelector(".::root"));
        String szName = root.getText();
        WebElement wElem1 = wTree.findElement(By.cssSelector(".::nth-node(2)"));
        new Actions(driver).doubleClick(wElem1).perform();

        String sRet = wTree.getAttribute("rowCount");

        String szEditable = wTree.getAttribute("editable");

        WebElement wElem3 = wTree.findElement(By.cssSelector(".::nth-node(3)"));
        wElem3.click();


        if(szEditable.compareTo("true") == 0){
            WebElement wElem2 = wElem1.findElement(By.cssSelector(".::editor"));
        }


        List<WebElement> nodes = wTree.findElements(By.cssSelector(".::all-nodes"));
        int i = 10;
        i += 10;
        
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

        TreeViewTest app = new TreeViewTest();
        try {
            app.launchApplication(szURL);
        } catch(Exception exp){
            System.out.println("Exception occurred while connecting to JavaDriver [ "
                    + exp.getMessage() + " ]");
        }
    }


}
