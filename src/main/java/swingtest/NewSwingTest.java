package swingtest;

import core.Log4RQ;
import net.sourceforge.marathon.javaagent.JavaElement;
import net.sourceforge.marathon.javadriver.JavaDriver;
import net.sourceforge.marathon.javadriver.JavaProfile;
import org.apache.commons.exec.OS;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewSwingTest {
    private RemoteWebDriver driver;
    private JavaDriver jDriver;


    private String getText(WebElement wElem){
        String szRet = null;
        try{
            szRet = wElem.getText();
        } catch(Exception e){

        }

        return szRet;
    }

    private String getTagName(WebElement wElem){
        String szRet = null;
        try{
            szRet = wElem.getTagName();
        } catch(Exception e){

        }

        return szRet;
    }

    private String getClass(WebElement wElem){
        Class szRet = null;
        try{
            szRet = wElem.getClass();
        } catch(Exception e){

        }

        return szRet.toString();
    }

    private String getAttribute(WebElement wElem, String attrib){
        String szRet = null;
        try{
            szRet = wElem.getAttribute(attrib);
        } catch(Exception e){

        }

        return szRet;
    }

    private String getCssValue(WebElement wElem, String s){
        String szRet = null;
        try{
            szRet = wElem.getAttribute(s);
        } catch(Exception e){
        }
        return szRet;
    }

    private Dimension getDimension(WebElement wElem){
        Dimension dimRet = null;
        try{
            dimRet = wElem.getSize();
        } catch(Exception e){
        }
        return dimRet;
    }

    private Point getLocation(WebElement wElem){
        Point ptRet = null;
        try{
            ptRet = wElem.getLocation();
        } catch(Exception e){

        }
        return ptRet;
    }

    private List<String> listComboOptions(WebElement weCombo){
        List<String> lOptions = new ArrayList<String>();

        try{
            List<WebElement> presets = weCombo.findElements(By.cssSelector(".::all-options"));
            for(WebElement preset: presets){
                lOptions.add(preset.getText());
                System.out.println(preset.getText());
            }

        } catch(Exception e){
            lOptions.clear();
        }
        //WebElement comboPresets = driver.findElement(By.cssSelector("combo-box"));


        return lOptions;
    }

    private WebElement findWebElementByClass(RemoteWebDriver driver, String szCss){

        try{
            return driver.findElement(By.cssSelector(szCss));
        } catch(Exception e){
            return null;
        }

    }

    private void printWebElementInfo(WebElement elem){
        //String className = this.getClass(tab);
        String className = "";
        String szText = this.getText(elem);
        String szTagName = this.getTagName(elem);
        Point pt = this.getLocation(elem);
        Dimension dm = this.getDimension(elem);
        String att1 = this.getAttribute(elem, "visible");
        String cssVal1 = this.getCssValue(elem, "visible");
        String name = this.getAttribute(elem, "name");
        String id = this.getAttribute(elem, "id");
        String szType = this.getAttribute(elem, "type");
        String parent = this.getAttribute(elem, "parent");
        String childs = this.getCssValue(elem, "childs");
        String CText = this.getCssValue(elem, "CText");
        String accessibleName = this.getCssValue(elem, "accessibleName");
        String fieldName = this.getCssValue(elem, "fieldName");
        String ctxAccessibleName = this.getCssValue(elem, "accessibleContext.accessibleName");
        System.out.println(
                "   ~ NAME: " + className +
                        "   ~ Text: " + szText +
                        "   ~ TagName: " + szTagName  +
                        "   ~ Visible: " + att1 + "(" + cssVal1 + ")" +
                        "   ~ point: " + pt.toString() +
                        "   ~ Dimension: " + dm.toString() +
                        "   ~ name: " + name +
                        "   ~ CText: " + CText +
                        "   ~ accessibleName: " + accessibleName +
                        "   ~ fieldName: " + fieldName +
                        "   ~ ctxAccessibleName: " + ctxAccessibleName +
                        "   ~ parent: " + parent);

    }

    private int printOutChildsInfo(List<WebElement> lElems){
        int i = 0;
        for(WebElement elem : lElems){
            this.printWebElementInfo(elem);
            i++;
        }
        return lElems.size();
    }

    private void log(String sz){
        System.out.println(Log4RQ.ANSI_BLUE + sz + Log4RQ.ANSI_RESET);
    }


    private WebElement getParent(WebElement wElem){
        WebElement weRet = null;
        try{
            weRet = (WebElement) driver.executeScript("return $1.getParent();",wElem);

        } catch(Exception e){
              Log4RQ.logDebug("Failed to access the parent object");
        }

        return weRet;
    }

    public static void main(String[] args) {

        String szURL = "127.0.0.1:44444";
        if (args.length > 0){
            System.out.println("IP to connect to is " + args[0]);
            szURL = args[0];
        }

        NewSwingTest app = new NewSwingTest();

//        app.createDriverJava();

        try {
            app.javaDriverDemo(szURL);
        } catch(Exception exp){
            System.out.println("Exception occurred while connecting to JavaDriver [ "
                    + exp.getMessage() + " ]");
        }
    }

    public void javaDriverDemo(String url) throws MalformedURLException {
        driver = new RemoteWebDriver(
                new URL("http://" + url),
                new DesiredCapabilities("java", "1.0", Platform.ANY)
        );

        String title = driver.getTitle();
        log("Found title is " + title);

//        List<WebElement> listret = driver.findElements(By.cssSelector("*"));
        //this.printOutChildsInfo(listret);

        JavascriptExecutor js;
        Object ret = null;

        WebElement myPanel = driver.findElement(By.id("ZenPanel01"));
        WebElement weParent = this.getParent(myPanel);
        String sRet = weParent.getAttribute("name");
        this.printWebElementInfo(weParent);

        myPanel = driver.findElement(By.id("ZenPanel01"));
        List<WebElement> lElem = myPanel.findElements(By.cssSelector("."));
//        this.printOutChildsInfo(lElem);

        String sz = "";
        sz = myPanel.getAttribute("parent");



        // Second elem
        myPanel = driver.findElement(By.id("ZenPanel02"));
        sz = myPanel.getAttribute("parent");


        List<WebElement> listAll = driver.findElements(By.cssSelector("*"));
        this.printOutChildsInfo(listAll);


        jDriver = (JavaDriver) driver;
        WebElement jElems = jDriver.findElementByCssSelector("*");



        int i = 100;


    }

    // Java 1.8 only
    public void createDriverJava() {
        JavaProfile profile = new JavaProfile(JavaProfile.LaunchMode.COMMAND_LINE);
        File f = new File("/home/mroy/myrun.sh");
        profile.setCommand(f.getAbsolutePath());
        profile.addApplicationArguments("Argument1");
        DesiredCapabilities caps = new DesiredCapabilities("java", "1.5", Platform.ANY);
        driver = new JavaDriver(profile, caps, caps);
    }



}
