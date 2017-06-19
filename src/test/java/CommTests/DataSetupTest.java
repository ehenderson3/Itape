package CommTests;

import CommPageObjects.PathDetailPage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by ehend on 2/25/2017.
 */

 public class DataSetupTest extends BaseTest {
    private PathDetailPage pathDetail;


    @Before
    public void Setup() {
//        if(host != "localhost"){ // commented out replaced with below  by Allan per IntelliJ compiler tip 2017-05-30-134002 because it wasn't running inside this condition
        if(!host.equals("localhost")) {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            System.out.println("ADDED IMPLICIT TIME OUT TO SETUP() WITHIN TEST 'DataSetupTest' ");
        }
        pathDetail = new PathDetailPage(driver);

    }

    @Rule
    public RetryTest.Retry retry = new RetryTest.Retry(1);

    Random rndNum = new Random();
    int randomNumber = rndNum.nextInt(100000);

    @Test
    public void interTapePOC() {
        boolean siteExist;
        pathDetail.customerChannelTraverse();


    }



 }
