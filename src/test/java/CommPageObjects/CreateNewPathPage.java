package CommPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class CreateNewPathPage extends BasePage {



                            //  - - Three Navigation buttons (1 static, 2 flyout/dropdown)
                            //  - - Eight Text Fields to validate with their warnings,
                            //  - - buttons for Cancel, Apply, Save (Save only appears when Apply validates input)
                            //       By buttonSaveChanges = By.xpath("//*[@id=\"aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id302:btnSRA2container\"]/table/tbody/tr/td[2]/div");
                            //  - - Pop up Yes, No Dialog warning of lost data if  cancelling you edits
                            // - -  GENERAL ERROR warning (in)visible red graphc and text will announce any specific field error,
                            // - -  RED HALO appears around invalid field entries

// 00 CHOOSE BROWSER, OPEN URL
    WebDriver driver;
    String struser = "allanw";
    String strpw = "password";
    String TargetURL = "http://ipg-mdm-mdmdv:8080/bdd";  // This field requires an integer value.
    String expectTitle="Informatica MDM";
    int winwidth = 700;  int winheight = 300;



    //Fields - Password Test 001  // http://ipg-mdm-mdmdv:8080/bdd   // .clear();  SendKeys("password"); Sendkeys("allanw");
    By inputLoginUsername  = By.className("infaField");   // By.cssSelector("input.infaField");
    By inputLoginPassWord = By.className("infaField");  // By.cssSelector("#ifrm1_password > input");          // By.xpath("//div[@id='ifrm1_password']/input")
    By buttonLoginSubmit = By.id("infaButton1");
    By msgLoginWarning =   By.cssSelector("div.infaLoginMsg");

    // Fields - Task Dialog 002
    By  DialogTaskTitle = By.id("ui-id-1");
    By  DialogTaskLink1 = By.linkText("Customer");  // By.className("ng-binding");
    By  DialogTaskLink2 = By.linkText("Customer with UE");  // By.className("ng-binding");
    By  DialogTaskLink3 = By.linkText("Reference Tables");  // By.className("ng-binding");

    // Fields - Dashboard FlyOut Menu  http://ipg-mdm-mdmdv:8080/e360/mdm/entity360view/?wstate=(%27$ws%27:DASHBOARD)
    //  By landingPageTitle
    //  By topMenu ==== (01) Create in Data View   (02) Create Master Data  (03)  Customer Channel
    By topMenu01 = By.className("rootVoice {menu: 'infaMenu1_0'} infaMenuItem" );  //  selector "#infaMenu1 > table > tbody > tr > td"   xpath="//*[@id="infaMenu1"]/table/tbody/tr/td"
    By topMenuHover02 = By.id( "infaMenu1_0" ); // parent DIV of actual <a> link without ID but class = "{menu: &#39;infaMenu1_1&#39;, img: &#39;http://ipg-mdm-mdmdv:8080/bdd/images/new_record.png&#39;} infaMenuItem";
    By topMenuHover03 = By.id( "infaMenu1_2_a" ); // AN ACTUAL ID IN THE ACTIVE LINK !!!  id='infaMenu1_1_0'  // also bodacious  By.xpath("//table[@id='infaMenu1_1_0']//a[.='Customer Channel']")

    // Fields - CreateEditCustomerChannel
    By captionNewCustomerChannel = By.id("aF:DVTC2:lbl_txtDVTC2"); //By.ByCssSelector("#aF:DVTC2:lbl_txtDVTC2");  JUST A TITLE FOR REFERENCE, NOT NEEDED FOR SMOKE
    By inputNewCustomerChannelCode = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC2:tfsfxBPC2");
    By WARNCustomerChannelCodeEmpty = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC2:j_id790"); //  This field cannot be empty.
    By WARNCustomerChannelCodeNegative = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC2:j_id1325");     // ""Code must contain data, cannot be more than 4 characters, no special characters"
    By inputNewCustomerChannelDescrip = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC3:tfsfxBPC3"); // max 30?
    By WARNCustomerChannelDescripEmpty = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC3:j_id1692"); // This field cannot be empty.
    By WARNCustomerChannelDescripBadChars = By.id("F:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC3:j_id1336");
    // must contain data, cannot be more than 30 characters Description must contain data, cannot be more than 30 characters
    By inputNewCustomerChannelAbsOrderMin = By.linkText("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC2:tfsfxBPC2"); // maxlength="1024"
    By WARNCustomerChannelAbsOrderMin =  By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC4:tfsfxBPC4");  // integer values
    By inputNewCustomerChannelDropShipThreshold = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC5:tfsfxBPC5");
    // By.name("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC5:tfsfxBPC5");
    By  WARNCustomerChannelDropShipThreshold = By.id( "aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC5:j_id2250" );
    //  This field requires an integer value.  // zeros and  negatives are now accepted TBD

    By inputNewCustomerChannelAddFrtThreshold = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC6:tfsfxBPC6");
    By WARNCustomerChannelAddFrtThreshold = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC6:j_id1903content");
    //This field requires an integer value.  // blank ok TBD

    By inputNewCustomerMinChargeThreshhold = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC7:tfsfxBPC7");
    By WARNCustomerMinChargeThreshholdNoAlpha = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC7:j_id2092");

    By dropdownNewCustomerChannelOrderPriority = By.id( "aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR4:incCLPC2:cbsfxCLPC2comboboxFieldVirtual" );
    // outer     id="aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR4:incCLPC2:cbsfxCLPC2comboboxField"
    By WARNdropdownCustomerChannelOrderPriority = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR4:incCLPC3:j_id867content");
    // This field cannot be empty.

    By dropdownNewCustomerChannelStatus = By.id( "aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR4:incCLPC3:cbsfxCLPC3comboboxField" );


    //APPLY
    By buttonNewCustomerChannelApply = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:dvApplyGEDVB2_1butLink"); // activates JavaScript/Ajax browser DEPENDENT
    // save changes //*[@id="aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id302:btnSRA2container"]/table/tbody/tr/td[2]/text()
    By buttonSaveChanges = By.xpath("//*[@id=\"aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id302:btnSRA2container\"]/table/tbody/tr/td[2]/text()");
    By buttonSAVEcontainingDivTableTD = By.id( "aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id302:btnSRA2container" );
    // SEND FOR APPROVAL
    // great-great-grandparent TD has ID = "aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id304:btnSFAA2container";
    By buttonSendForApproval = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id304:btnSFAA2butLink");  // this is a 'great grandparent <A> with a JavaScript ACTION
    // CANCEL  and  ALERT POPUP DIALOG INFORMATICA MDM DATA DIRECTOR , ENTITY "NEW CUSTOMER CHANNEL" HAS BEEN MODIFIED BUT NOT SAE. dO YOU WANT TO DISCARD CHANGES AND PREOCEED? YES/NO
    By dialogCancelUnsavedChanges = By.id("INPUT ID = confDlgForm:closeButton");

    // LOG OUT / LANGUAGES
    By horizontalMenuTopRightDropdownUsername = By.className("rootVoice {menu: 'infaMenu3_1'} infaMenuItem");
    By horizontalMenuTopRightDropdownLogout = By.id("infaMenu3_14_a");

    //COMMON MESSAGES
    By WARNdataentryCommon = By.id("#aF\\3a DVTC2\\3a incDVTC2\\3a inc_asset\\3a inc_view\\3a dv_GEDVB2_1\\3a nf\\3a j_id248 > table > tbody > tr");
    // graphic AND string and color      //  <span class="message-label-red">Validation error</span>
    By buttonNewCustomerChannelCancel = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id306:btnCA2container");
    // outerDIV has ID, not the <td class="middle">Cancel<div class="btnstub"></div></td>
    // xpath //*[@id="aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id306:btnCA2container"]/table/tbody/tr/td[2]
    // there is also a counter clockwise undo icon: a mouse-over shows HINT Ctrl-C Cancel ... is it the SAME behavior as the blue button with English "Cancel"
    By iconCancelCtrlC = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:dvCancelEditGEDVB2_1");

    public CreateNewPathPage(WebDriver driver) {
        super(driver);
        visit("/");
    }





    public void createBrandNewProjectPath(){

        String strTemp="";                 // @Test(timeout=10000)

        driver.manage().window().setSize(new Dimension(winwidth,winheight));

        driver.findElement(By.cssSelector("input.infaField")).clear();
        driver.findElement(By.cssSelector("input.infaField")).sendKeys(struser);
         click(inputLoginUsername );


        waitForIsDisplayed(inputLoginUsername);
        clear(inputLoginUsername);
        type(struser, inputLoginUsername);
        click(inputLoginUsername);

        hover(inputLoginPassWord);
        waitForIsDisplayed(inputLoginPassWord);
        clear(inputLoginPassWord);
        type(strpw,inputLoginPassWord);
        click(inputLoginPassWord);

        hover(buttonLoginSubmit);
        waitForIsDisplayed(buttonLoginSubmit);
        click(buttonLoginSubmit);

// login success should reveal dialog box having this title and links (repeated in next method)
        hover(DialogTaskTitle);  // better yet getText?



    }



} // END CLASS
