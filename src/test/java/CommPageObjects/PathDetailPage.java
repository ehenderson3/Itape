// TODO WARNINGs and their RULES  section 05 line 379
// TODO enable Junit reporting - ask Enrique OK to bypass or work with his strategy of having this class "PathDetailPage" in "CommPageObjects" folder
// TODO (from above)  this test (class PathDetailPage) and run by "DataSetupTest" in the lower folder
// TODO  - confirm with Enrique OK to delete PathSummaryPage containing his previous radio project
// TODO ADD LOGIC TO CHOOSE BROWSER AT RUNTIME // explore alternatives to ENRIQUE'S library CONFIG LOGIC
// TODO ON SETUP : WARN AND CLOSE ANY OPEN BROWSERS OF SAME TYPE
// TODO WHY CAN'T MAXIMIZE FORM AREA: MUST SCROLL TO SEE OUT OF SIGHT FIELD AND COMMAND BUTTONS - Client email says known issue
// DONE replaced individual warnings with single "locWARNcommon" ... loop of 90to confirm intermittent 'stale element' fixed by added 'waits'
// TODO LOOP through AN ARRAY OF FIELDs and Buttons and WARNINGs (or 2 dim for warnings on/off)
// TODO REMOVE TEMPORARY END-OF-TEST DIALOG CONFIRMATION
// TODO with database access finish sections 2.x from Client Test Strategy "Channel Test Script screen shots.docx"


package CommPageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PathDetailPage extends BasePage {

    public PathDetailPage(WebDriver driver) {
        super( driver );
        visit("/bdd");
    }

    // validation variables
    //    String strTemp = null;
    boolean DOASKUSER = true;
    boolean MULTITEST = true;
    boolean isSHORTTEST = true;

    int CCcodeMin=1; int CCcodeMax=4; // no special characters
    int CCdescMin=1; int CCdescMax=30;      // TODO confirm if/which/any ) special characters
    //TODO confirm if Threshold and Minimums should be Negative (they can be negative now)
    // TODO confirm if known bug:  Second edits of Customer Code and Customer description only will accept invalid conditions that were trapped at first

    int  CCThresholdsMIN = -99;          //TODO TBD is it a business rule to have a Min / Max here?
    int  CCThresholdsMAX = 99999;
    String dupeCCcodeList = "ECP,1345,ICO,ID,AB,INDU,INTL,NEW3,NEW"; //TODO to be used by Validation Tests
    String CCdescTooLong  = "char 251 v - Len OK  > 31 char show RED warning!"; // 48 chars
    String CCdescOK = "José Nuñez  30 chars no warn !";
    String CCcodeInvalid = "123|><½";
    int howslow=5;


    // navigation variables
    By inputLoginUsername = By.className("infaField");   // By.cssSelector("input.infaField");
    By inputLoginPassWord = By.className("infaField");  // By.cssSelector("#ifrm1_password > input");          // By.xpath("//div[@id='ifrm1_password']/input")
    By buttonLoginSubmit = By.id("infaButton1");
    By msgLoginWarning = By.cssSelector("div.infaLoginMsg");

    //   02 Task Dialog 002
    By DialogTaskTitle = By.id("ui-id-1");
    By DialogTaskLink1 = By.linkText("Customer");  // By.className("ng-binding");  // below are Menu items 3 and 4 that may be used in future
//    By DialogTaskLink2 = By.linkText("Customer with UE");  // By.className("ng-binding");
//    By DialogTaskLink3 = By.linkText("Reference Tables");  // By.className("ng-binding");

    // 03 Dashboard FlyOut Menu  http://ipg-mdm-mdmdv:8080/e360/mdm/entity360view/?wstate=(%27$ws%27:DASHBOARD)
    //          MOUSEOVER HIDDEN BUTTONS TO OPEN NEW CUSTOMER CHANNEL ENTRY FORM
    //  By topMenu ==== (00) Create in Data View   (01) Create Master Data  (02) Reference Tables  (03)  Customer Channel
    By noTasksToDisplay = By.className("empty-set-text");
    By topMenu00 = By.xpath("//*[@id=\"infaMenu1\"]/table/tbody/tr/td");  //  selector "#infaMenu1 > table > tbody > tr > td"   xpath="//*[@id="infaMenu1"]/table/tbody/tr/td"

    By linkHover01CreateMasterData = By.linkText("Create Master Data"); //  linkText "Create Master Data"

    By linkHover02ReferenceTables = By.id("infaMenu1_1_a");  // Reference Tables MISSING in this version: exists in PROD. Use If/else
    By linkHover03CustomerChannel = By.linkText("Customer Channel"); //  By.id("infaMenu1_2_a"); // AN ACTUAL ID IN THE ACTIVE LINK !!!  id='infaMenu1_1_0'  // also bodacious  By.xpath("//table[@id='infaMenu1_1_0']//a[.='Customer Channel']")

    By inputNewCustomerChannelCode = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC2:tfsfxBPC2");

    By inputNewCustomerChannelDescrip = By.xpath(".//*[@id='aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC3:tfsfxBPC3']");
    By inputAbsOrderMin = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC4:tfsfxBPC4"); // maxlength="1024"
    By inputDropShipThreshold = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC5:tfsfxBPC5");
    By inputMinChargeThreshold = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC7:tfsfxBPC7");
    By inputAddFrtThreshold = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC6:tfsfxBPC6");
    By dropPriorityX  = By.xpath("//form[4]/table/tbody/tr/td[2]/div/div/div/table/tbody/tr/td/div/div[2]/div[2]/div/span[1]/span/div/table/tbody/tr/td[2]/div/div/div/div/div/div/div/div[2]/table/tbody/tr/td[2]/div/table/tbody/tr[4]/td[2]/div/table/tbody/tr/td[1]/div/div/div[2]/input[2]");
    //    By dropdownStatusActiveInactive = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR4:incCLPC3:cbsfxCLPC3comboboxField");
    By dropdownStatusActiveInactive =By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR4:incCLPC3:cbsfxCLPC3comboboxField");
    // By.xpath("//form[4]/table/tbody/tr/td[2]/div/div/div/table/tbody/tr/td/div/div[2]/div[2]/div/span[1]/span/div/table/tbody/tr/td[2]/div/div/div/div/div/div/div/div[2]/table/tbody/tr/td[2]/div/table/tbody/tr[4]/td[4]/div/table/tbody/tr/td[1]/div/div/div[2]/input[2]");

    //05 validation warning messages
    By locWARNCodeEmpty = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC2:j_id278content");  // This field cannot be empty.
        //    By locWARNcommon = By.xpath("//*[contains(@style,'display:block') or contains(@class,'propertyTooltip') ]//*[contains(text(),'This field')]"); // technically finds two, the second is stale: only one expected. So using find() NOT findS()
    By locWARNcommon = By.xpath("//*[contains(@class,'propertyTooltip') ]//*[contains(text(),'This field')]"); // technically finds two, the second is stale: only one expected. So using find() NOT findS()

    By locWARNCustomerChannelCodeEmpty = By.id("aF:DVTC3:incDVTC3:inc_asset:inc_view:dv_GEDVB3_2:coreGEDVB3_2:inccorerowCPLR5:incBPC8:j_id2644content");
    // xpath  .//*[@id='aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC2:j_id1866content']  2017-05-31-094801
    // span id aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC2:j_id1866content"  2017-05-31-094801


    By locWARNCustomerChannelCodeINVALID = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC2:j_id1690content"); // 2017-05-31-0942
    ;
    By locWARNCustomerChannelDescripEmpty = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC2:j_id790content");
    // By.xpath(".//*[@id='aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC3:j_id2049content']"); 5/29/2017
    // id   aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC2:j_id790content  2017-05-31-092843

    By locWARNCustomerChannelDescripBadChars = By.xpath(".//*[@id='aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR2:incBPC3:j_id801content']");
    By locWARNAbsOrderMin = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC4:j_id1170content");

    By locWARNInputDropshipThreshold =  By.xpath(".//*[@id='aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC5:j_id824content']");
    By locWARNAddFrtThreshold =  By.xpath(".//*[@id='aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC6:j_id835content']");
    By locWARNMinChargeThreshold =  By.xpath(".//*[@id='aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC7:j_id846content']");
    By locWARNstatusActiveInactive = By.xpath(".//*[@id='aF:DVTC3:incDVTC3:inc_asset:inc_view:dv_GEDVB3_2:coreGEDVB3_2:inccorerowCPLR7:incCLPC5:j_id2721content']");

    // message varibles, compare SUBstrings for individual rules, to confirm COMBINED string(s)
        /*    String warningEmpty = "This field cannot be empty.";
            String warningInteger  = "This field requires an integer value.";
            String warningSpecial = "special characters";
            String warningCodeData = "Code must contain data, cannot be more than 4 characters, no special characters";
            String warningDescription = "Description must contain data, cannot be more than 30 characters";
            String warningCodeEmpty = "Code must contain data";
            String warningCodeMax ="cannot be more than 4 characters";*/



    By locWARNCustomerChannelDropShipThreshold = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC5:j_id2250");
    //  This field requires an integer value.  // zeros and  negatives are now accepted TBD
    By locWARNCustomerMinChargeThresholdNoAlpha = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR3:incBPC7:j_id2092");
    By locWARNdropdownCustomerChannelOrderPriority = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_viXXXew:dv_GEDVB2_1:coreGEDVB2_1:inccorerowCPLR4:incCLPC3:j_id867content");

    // 06 BUTTONS:  APPLY exposes SAVE and SEND FOR APPROVAL
    By buttonApply = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:dvApplyGEDVB2_1butLink"); // activates JavaScript/Ajax browser DEPENDENT
    // save changes //*[@id="aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id302:btnSRA2container"]/table/tbody/tr/td[2]/text()
    By buttonSaveChanges = By.xpath("//*[@id=\"aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id302:btnSRA2container\"]/table/tbody/tr/td[2]/text()");
    By buttonSAVEcontainingDivTableTD = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id302:btnSRA2container");



    By buttonUndoCtrlAltZ = By.id("aF:DVTC3:incDVTC3:inc_asset:inc_view:dv_GEDVB3_2:dvRevertGEDVB3_2 ");  // graphic icon at right of screen (right of APPLY)
    // aF:DVTC3:incDVTC3:inc_asset:inc_view:dv_GEDVB3_2:dvRevertGEDVB3_2
    // 07 CANCEL  and  ALERT POPUP DIALOG INFORMATICA MDM DATA DIRECTOR , ENTITY "NEW CUSTOMER CHANNEL" HAS BEEN MODIFIED BUT NOT SAVED. dO YOU WANT TO DISCARD CHANGES AND PROCEED? YES/NO

    By dialogCancelUnsavedChanges = By.id("confDlgForm:confirmationDialogHeader"); // caption title: "Informatica MDM Data Director"
    By buttonDialogCancelNO = By.id(":noButton #confDlgForm:noButton");
    By buttonDialogCancelYES = By.id("#confDlgForm:yesButton");
    By buttonDialogCancelCLOSE = By.id("confDlgForm:closeButton");

    // 08  LOG OUT / LANGUAGES
    By horizontalMenuTopRightDropdownUsername = By.className("rootVoice {menu: 'infaMenu3_1'} infaMenuItem");
    By horizontalMenuTopRightDropdownLogout = By.id("infaMenu3_14_a");

    // 09   COMMON MESSAGES
    By RequiredFieldLegend = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:j_id447"); // <sp>*</sp>"Required Input"
    By locWARNdataentryCommon = By.className("message-label-red");
    // By.xpath("//*[@id=\"aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:nf:j_id454\"]/table/tbody/tr/td[2]/span");

    //By.id("#aF\\3a DVTC2\\3a incDVTC2\\3a inc_asset\\3a inc_view\\3a dv_GEDVB2_1\\3a nf\\3a j_id248 > table > tbody > tr");
    By buttonNewCustomerChannelCancel = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id306:btnCA2container");
    By buttonCancelBlueTopTbl = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id306:btnCA2"); // tableid
    By buttonCancelBlueTopHref = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_top_toolbar:j_id306:btnCA2butLink"); // hrefID

    // outerDIV has ID, not the <td class="middle">Cancel<div class="btnstub"></div></td>
    By iconCancelCtrlC = By.id("aF:DVTC2:incDVTC2:inc_asset:inc_view:dv_GEDVB2_1:dvCancelEditGEDVB2_1");

   public void customerChannelTraverse() {


        Test_01_Login();
        Test_02_TaskListDialog();
        Test_03_FlyOutMenu_ChooseCustomerChannel();
        Test_04_LandOnCustomerChannel();


        if (isSHORTTEST)
        {
            Test_05_ApplyEMPTYCustChannelEdits();

            Test_05A_ApplyInvalidCODE();
            Test_05B_ApplyInvalidDesc();
            Test_06_InputNewChannelCode_BADCHARS();
            Test_07_InputNewChannelCodeBADLENGTH();

            Test_05C_ApplyInvalid_AbsOrderMin();
            Test_05D_ApplyInvalid_InputDropShipThreshold();
            Test_05E_ApplyInvalid_AddFrtThreshold();
            Test_05F_ApplyInvalid_MinChargeThreshold();
            Test_05G_ApplyInvalid_MinOrderPriority();
            Test_05H_ApplyInvalidEntry_Status();
            Test_05G_ApplyInvalid_MinOrderPriority();
            Test_08_Input_All_GOODDATA(); // used in Test_10
            //Test_09_ApplyButtonAfterWarnings();  // comment this out to bypass negative tests

        }
        Test_10_ApplyButtonAfterNoWarnings();
        Test_11_CheckWarningsDescription();

        Test_99_CloseDown();

        if (DOASKUSER) {    askUserYN("Ready to CLOSE TEST  OBJECTS and  BROWSERS?","Press *Any Key* (even 'NO')\n       ( Y / N / Esc) \n to close down all NOW");}



        /****************************************************************************************************/

        // TODO DROP DOWN TESTS - CHOOSE ANY ONE OF EACH DROPDOWN ELEMENT   UI
        // TODO DROP DOWN TESTS - ORDER PRIORITY - UI DROP DOWN POPULATED FROM DATABASE TABLE
        // TODO TEST SCENARIOS FOR EACH WARNING AND THE PATHS THAT GENERATE EACH



        // dialog CANCEL AND ALERT
        // CLICK CANCEL  (NO) --> VERIFY POP UP MODAL DIALOG POPS UP
        // VERIFY NO BUTTON CAN BE CLICKED:
        // VERIFY  RETURN TO PAGE
        // CLICK CANCEL  (YES) --> VERIFY POP UP MODAL DIALOG POPS UP
        // VERIFY YES BUTTON CAN BE CLICKED:
        // CLOSES NEW CUSTOMER CHANNNEL TAB
        // VERIFY DIALOG CAN BE CLOSE [X] : SAME AS  "NO"
        //  VERIFY RETURN TO PAGE

        //  VALIDATED NEW RECORD - CANCEL
        // - - DIALOG CONFIRMATION YES -
        // - - CONFIRM NEW PAGE LOADS with expected Eight (8) Elements
        // - - DIALOG CONFIRMATION NO
        // - - confirm return to previous page

//  SAVE VALIDATED NEW RECORD - compare (a) with entries (b) with Database

//   BACK-END
        //  - CONFIRM EACH NEW RECORD IN DATABASE
        //  - CONFIRM EACH NEW DATA BASE ENTRY IN FRONT ENDS
//   LOGOUT
        // A. verify EXIST: (from currentURL) in tabbed IFRAME with the CAPTION  "NEW CUSTOMER CHANNEL" AND/OR TAB "NEW CUSTOMER CHANNEL #1 #2 #3"
        // B. VERIFY USER NAME (READ FROM WHERE?) LINK EXISTS (e.g. Allan Whitworth) AT TOP MOST RIGHT OF PAGE  LOCATOR: horizontalMenuTopRightDropdownUsername
        // C. HOVER on the Firstname Lastname LINK: --> confirm DROP DOWN OPENS
        // D.    verify drop down has LINK "LOG OUT"             LOCATOR:  horizontalMenuTopRightDropdownLogout
        // E. CLICK OPTION "LOG OUT"
        // F. VERIFY CURRENTURL CHANGES FROM STEP A TO "http://ipg-mdm-mdmdv:8080/e360/com.informatica.tools.mdm.web.auth/login"

//BACK END SERVER TESTS
//        2.1 Data from Cust channel updates CUSCLS table in PRISM for every treasury
        //        SAVE a new Customer channel in IDD.
        //        Channel Code, Desc, and Priority Code exist in the CUSCLS table in all 6 Entities
        //        T_PRISM CUSCLS tables_001

//        2.2 Data from Cust channel updates CUSCLS table in PRISM for every treasury
        //        SAVE updates to an existing Customer Channel in IDD
        //        Desc and Priority Code exist in the CUSCLS table in all 6 Entities
        //        T_PRISM CUSCLS tables_002
        //        T_ETL_ Target system PRISM_001
//        2.3 Threshold data from Customer Channel update the NOTES table in PRISM
        //        SAVE a new customer channel in IDD
        //        All 4 threshold map to the NOTES file.
        //        T_PRISM "NOTES" tables_001
//        2.4  Threshold data from Customer Channel update the NOTES table in PRISM
        //        SAVE updates to an existing Customer Channel in IDD
        //        All 4 threshold map to the NOTES file.
        //        T_ETL_ Target system PRISM_001



    }   // end method customerChannelTraverse





    //    @Test
    public void Test_00_Setup() {       // 01   Password Test
        // driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        // note: Enrique set this up so this test is actually ran by test "DataSetupTest"

        System.out.println("INFO, Start Customer Channel POC  at, " + datetimestamp());
    }


    //    @Test
    public void Test_01_Login()  {
        // 01   Password Test
        // validate field USERNAME  is IN PLACE  - By locator variable: inputLoginUsername
        // type USERNAME
        assertTrue("Username field is not present", isDisplayed( inputLoginUsername, 8 ) );
        type("allanw", inputLoginUsername, 0 );

        // validate field PASSWORD is in place -  By locator variable: inputLoginPassWord
        // type PASSWORD
        assertTrue("Password field is not present", isDisplayed( inputLoginPassWord, 8 ) );
        type("password", inputLoginPassWord, 1 );

        // validate BUTTON submit is in place -  By locator variable: buttonLoginSubmit
        // click on button
        assertTrue("Submit button is not present", isDisplayed( buttonLoginSubmit, 8 ) );
        click( buttonLoginSubmit );
        System.out.println("INFO, Completed Test01_Login at, " + datetimestamp());
    }

    //@Test
    public void Test_02_TaskListDialog() {
        //@Test 02 Task Dialog 002
        // validate that NEW DIALOG  appears in place - and that LINK "Customer" is Displayed And Clickable
        // click on link "CUSTOMER"
        assertTrue("Task Dialog is not present", isDisplayed( DialogTaskTitle, 8 ) );
        assertTrue("link 'Customer' on Dialog 'Select Application' not found", isDisplayed( DialogTaskLink1, 8 ) );
        click( DialogTaskLink1 );
        // TODO  driver.manage().window().fullscreen(); // HAS PRIVATE ACCESS? SUPER?

        System.out.println("INFO, Completed Test_02_TaskListDialog at, " + datetimestamp());

    }

    //@Test
    public void Test_03_FlyOutMenu_ChooseCustomerChannel() {

// 03 Dashboard FlyOut ListMenu  - top Menu Item "Create In Data View" with TWO CHILDREN
        // maximize window in to avoid INFORMATICA issue that Iframe can't be maximized
        //  verify exists: next  MENU ITEM - By locator variable:  topMenu01
        //  verify that expect MENU ITEM displays: "CREATE IN DATA VIEW"  locator - topMenu00

        //    max(); //  TODO ?? Possible Bug?  //  // Dimension priorWinDim = winDimGet(); // winSize(priorWinDim); // will restore

        assertTrue("Top Menu item 'Create in Data View' is not present ", isDisplayed( topMenu00, 8 ) );

        slowDown( 2 );
        click( topMenu00 );
        hover( topMenu00 );
        slowDown( 2 );
        assertTrue("NOT FOUND: Menu / Link Create Master Data", isDisplayed( linkHover01CreateMasterData, 8 ) );
        click( linkHover01CreateMasterData, 0 );

        if (isDisplayed( linkHover02ReferenceTables, 2 )) {
            click( linkHover02ReferenceTables );
        } else System.out.println( "INFO: Not Implemented: ''Reference Table'' flyout menu" );

        assertTrue("NOT FOUND: Menu (Flyout) ''Customer Channel''", isDisplayed( linkHover03CustomerChannel, 8 ) );
        slowDown( 2 );
        click( linkHover03CustomerChannel );
        slowDown( howslow );
        switchToFrame(1); // switchto IFRAME           // TODO switchBackToDefaultContent();

        System.out.println("INFO, Completed Test_03_FlyOutMenu_ChooseCustomerChannel at, " + datetimestamp());
    }





    public void Test_04_LandOnCustomerChannel(){   //Test_04  LANDING "HOME" (only for this proof of concept) PAGE FOR CUSTOMER CHANNEL COMPONENT

// 04 AA CreateEdit_CustomerChannel_Inputs  - New Customer Channel Entry Form (tab)
        // FORM ELEMENTS exists  big form id=aF

/*      verifyBoolean("Expected Apply is Displayed and Clickable",isDisplayedAndClickable( buttonApply,5 ) );
        click(buttonApply);  // IF WARNing[s] then TESTs X else TESTsY  Apply
*/

        verifyBoolean("Test_04 Expect button CANCEL (href)  is Displayed and Clickable",isDisplayedAndClickable( buttonCancelBlueTopHref,5 ) );
        // verifyBoolean("Expected CANCEL (tbl)  is Displayed and Clickable",isDisplayedAndClickable( buttonCancelBlueTopTbl,5 ) );

        // VERIFY CODE BLANK AND WARNING VISIBLE
        focusOn(inputNewCustomerChannelCode );

        System.out.println("INFO, Completed Test_04_LandOnCustomerChannel at, " + datetimestamp());

    }

    public void Test_05_ApplyEMPTYCustChannelEdits(){
// todo add hit refresh
        System.out.println("INFO, BEGIN Test_05_ApplyEMPTYCustChannelEdits at, " + datetimestamp());
        reApply( "Test_05_ApplyEMPTYCustChannelEdits" );
/*        if (verifyBoolean("Expected Apply is Displayed and Clickable",isDisplayedAndClickable( buttonApply,5 ) )) {
            click(buttonApply);  // IF WARNing[s] then TESTs X else TESTsY  Apply
        } else
            assertTrue( "FAILED (Expected Apply visible and clickable) ", isDisplayedAndClickable( buttonApply, 5 ) );*/

        //  Verify all BLANK fields

        assertTrue("NOT FOUND: FORM input Entry ''Customer Channel CODE''",isDisplayedAndClickable(inputNewCustomerChannelCode,8 ) );
        click(inputNewCustomerChannelCode);
        verifyEquals("Test_05_ApplyEmpty: Expect Field ''Channel CODE'' to be EMPTY: " ,"",getFieldText(inputNewCustomerChannelCode));
        slowDown( 2 );

        assertTrue("NOT FOUND: FORM input Entry ''Customer Channel Description''",isDisplayed(inputNewCustomerChannelDescrip,8 ) );
        click(inputNewCustomerChannelDescrip);
        verifyEquals("Test_05_ApplyEmpty: Expect Field ''Channel Description'' to be EMPTY: " ,"",getFieldText(inputNewCustomerChannelDescrip));
        slowDown( 2 );

        assertTrue("NOT FOUND: FORM input Entry ''Abs Order Min''",isDisplayed(inputAbsOrderMin,8 ) );
        click(inputAbsOrderMin);
        verifyEquals("Test_05_ApplyEmpty: Expect Field ''Channel Description'' to be EMPTY: " ,"",getFieldText(inputAbsOrderMin));
        slowDown( 2 );

        assertTrue("NOT FOUND: FORM input Entry ''Dropship Threshold''",isDisplayedAndClickable(inputDropShipThreshold,8 ) );
        click(inputDropShipThreshold);
        verifyEquals("Test_05_ApplyEmpty: Expect Field ''Dropship Threshold'' to be EMPTY: " ,"",getFieldText(inputDropShipThreshold));
        slowDown( 2 );

        assertTrue("NOT FOUND: FORM input Entry ''Add Frt Threshold''",isDisplayedAndClickable(inputAddFrtThreshold,8 ) );
        click(inputAddFrtThreshold);
        verifyEquals("Test_05_ApplyEmpty: Expect Field ''Add Frt Threshold'' to be EMPTY: " ,"",getFieldText(inputAddFrtThreshold));
        slowDown( 2 );

        assertTrue("NOT FOUND: FORM input Entry ''Add Frt ThresholdE''",isDisplayedAndClickable(inputAddFrtThreshold,8 ) );
        click(inputAddFrtThreshold);
        verifyEquals("Test_05_ApplyEmpty: Expect Field ''Add Frt Threshold'' to be EMPTY: " ,"",getFieldText(inputAddFrtThreshold));
        slowDown( 2 );

        assertTrue("NOT FOUND: FORM input Entry ''Min Charge Threshold''",isDisplayedAndClickable(inputMinChargeThreshold,8 ) );
        click(inputMinChargeThreshold);
        verifyEquals("Test_05_ApplyEmpty: Expect Field ''Min Charge Threshold'' to be EMPTY: " ,"",getFieldText(inputMinChargeThreshold));
        slowDown( 2 );

        assertTrue("NOT FOUND: FORM input Entry ''Order Priority''",isDisplayedAndClickable(dropPriorityX,8 ) );
        click(dropPriorityX);
        verifyEquals("Test_05_ApplyEmpty: Field ''Order Priority'' to be EMPTY" ,"",getFieldText(dropPriorityX));
        slowDown( 2 );

        assertTrue("NOT FOUND: input drop down box:  ''STATUS'' (dropdownStatusActiveInactive): ",isDisplayedAndClickable(dropdownStatusActiveInactive,8 ) );
        click(dropdownStatusActiveInactive);
        verifyEquals("Test_05_ApplyEmpty: Expect Field ''STATUS'' to be EMPTY" ,"",getFieldText(dropdownStatusActiveInactive));



    }


    public void Test_05A_ApplyInvalidCODE(){
        System.out.println("INFO, BEGIN Test_05A_ApplyInvalidCODE at, " + datetimestamp());
        String strTempA = "12v34|6";  //  strRandChars( 4 );
        clear(inputNewCustomerChannelCode);
        type(strTempA,inputNewCustomerChannelCode);
        //   WebElement eleCCode = find(inputNewCustomerChannelCode);

        reApply("Test_05A by hover();  ");

        hover(inputNewCustomerChannelCode); // click hover focusON
        popUpWarningFound("Test_05A_ApplyInvalidCODE");
/*        WebElement eleWarningA = find(locWARNcommon);
        String whathappened =  "Test 05A Code Warning '" + eleWarningA.getText() + "' properties: Visibility: " + eleWarningA.isDisplayed() + ", Enabled: " + eleWarningA.isEnabled();
        System.out.println(whathappened);*/

//        sysoutListHTMLxpath("//*[contains(@class,'propertyTooltip')]//*[contains(text(),'This field')]");

//        sysoutListHTMLxpath("//*[contains(@class,'propertyTooltip')]//*[contains(text(),'This field')]");

        //  sysoutListHTMLxpath("//*[contains(@style,'display:block') or contains(@class,'propertyTooltip') ]//*[contains(text(),'This field')]"); // add and or 'or' later
        //      above finds TWO (and the second is stale)
//        verifyBoolean("Test_05A Expected WARNING to Display by HOVER: ", (find(locWARNcommon).isDisplayed() )) ;  //  locWARNCustomerChannelCodeINVALID
//        slowDown(howslow);


        System.out.println("INFO, Completed Test_05A_ApplyInvalidCODE at, " + datetimestamp());
        // locWARNAbsOrderMin.equals(                  locWARNAddFrtThreshold.         locWARNInputDropshipThreshold          locWARNMinChargeThreshold      WARNCustomerChannelCodeINVALID
        slowDown(howslow);
    }


    public void Test_05B_ApplyInvalidDesc(){
        System.out.println("INFO, BEGIN Test_05B_ApplyInvalidDesc at, " + datetimestamp());
        //  B  VERIFY  inputNewCustomerChannelDescrip             exists, TYPE  "0123456789ABCDEF 0123456789ABCDEF", assert element VALUE = same
        String strTempB = CCdescTooLong;   //characters 29 OK, no warnings
        verifyBoolean("Expect FORM input Entry ''Customer Channel Description'' is Visible and Enabled",isDisplayedAndClickable(inputNewCustomerChannelDescrip,10 ) );
        //   String strTempB = CCdescTooLong;  //  "characters 31 WARNs in red!";  // = datetimestamp();
        clear(inputNewCustomerChannelDescrip);
        //       click(inputNewCustomerChannelDescrip);
        type(strTempB,inputNewCustomerChannelDescrip);
//          WebElement eleCDesc = find(inputNewCustomerChannelDescrip);  // already FOUND IT but need element attributes
        boolean validCCDesc_length = strTempB.length() > 0 && strTempB.length()<=30 ;

        reApply("Test_05B");


        hover(inputNewCustomerChannelCode); // wip akw
        popUpWarningFound("Test_05B_ApplyInvalidDesc");
     /*   String whathappened =  "Test 05B Description Warning '" + eleWarningB.getText() + "' properties: Visibility: " + eleWarningB.isDisplayed() + ", Enabled: " + eleWarningB.isEnabled();
        System.out.println(whathappened);*/

//        sysoutListHTMLxpath("//*[contains(@class,'propertyTooltip')]"); // //*[contains(text(),'This field')]
        //    sysoutListHTMLxpath("//*[contains(@style,'display:block') ]//*[contains(text(),'This field')]"); // add and or 'or' later
        //  sysoutListHTMLxpath("//*[contains(@style,'display:block') or contains(@class,'propertyTooltip') ]//*[contains(text(),'This field')]"); // add and or 'or' later
//        slowDown(howslow);

        //  verifyEquals("Expect user INPUT to match after validation: " ,eleCDesc.getAttribute("value") ,strTempB );
        verifyEquals("Expect user INPUT to be unchanged after validation: " ,find(inputNewCustomerChannelDescrip).getAttribute("value" ) ,strTempB );
        verifyBoolean("Expect Invalid Length ("+ strTempB.length() + ") between " + CCdescMin + " and " + CCdescMax + " visible characters: " ,!validCCDesc_length);

        System.out.println("INFO, Completed Test_05B_ApplyInvalidDesc at, " + datetimestamp());
    }


    public void Test_05C_ApplyInvalid_AbsOrderMin(){

        //  C  VERIFY  inputAbsOrderMin  exists, TYPE  "A1", assert element VALUE = same
        assertTrue("NOT Displayed and Clickable: FORM input Entry ''Abs Order Min''",isDisplayedAndClickable(inputAbsOrderMin,8 ) );
        // 1.4 Abs Ord Min can only contain integers. Float are trapped.  Negative Integers are NOT trapped

        String strTempC = "1.234";
        clear(inputAbsOrderMin);
        type(strTempC,inputAbsOrderMin);
        WebElement eleAbsOrderMin = find(inputAbsOrderMin);

        reApply( "Test_05C" );

        // click(inputAbsOrderMin);
        click(inputAbsOrderMin);
        hover(inputAbsOrderMin);
                    //         boolean tempTest = waitForIsDisplayed( locWARNcommon,5);
         popUpWarningFound("Test_05C_ApplyInvalid_AbsOrderMin");

        verifyEquals("Expect INPUT AbsOrderMin  should MATCH entered value AFTER Apply: " , find(inputAbsOrderMin).getAttribute("value") ,strTempC );

        System.out.println("INFO, Completed Test_05C_ApplyInvalid_AbsOrderMin at, " + datetimestamp());
    }


    public void Test_05D_ApplyInvalid_InputDropShipThreshold(){
        //  D  VERIFY  inputDropShipThreshold   exists, TYPE  "2B", assert element VALUE = same
        verifyBoolean("NOT Displayed and Clickable: FORM input Entry Drop Ship Threshold",isDisplayed( inputDropShipThreshold,8));

        String strTempD = "246.89";
        clear(inputDropShipThreshold);
        type(strTempD,inputDropShipThreshold);

        reApply("Test_05D");

        click(inputDropShipThreshold);
        hover(inputDropShipThreshold);
        popUpWarningFound( "Test_05D_ApplyInvalid_InputDropShipThreshold"  );

        System.out.println("INFO, Completed Test_05D_ApplyInvalid_InputDropShipThreshold at, " + datetimestamp());

    }


    public void Test_05E_ApplyInvalid_AddFrtThreshold(){

        //  E  VERIFY  inputAddFrtThreshold     exists, TYPE  "-0101", assert element VALUE = -1011
        verifyBoolean("NOT FOUND: FORM 'Add Frt Threshold' ",isDisplayed( inputAddFrtThreshold,8));
        String strTempE = "5.5555";

        clear(inputAddFrtThreshold);
        type(strTempE,inputAddFrtThreshold);
        WebElement eleAddFrtThreshold = find(inputAddFrtThreshold);
        //  assertEquals("Test 05E: INPUT 'Add Frt Threshold' DOES NOT MATCH : " ,eleAddFrtThreshold.getAttribute("value") ,strTempE );
        reApply("Test_05E");
        click(inputAddFrtThreshold);
        hover(inputAddFrtThreshold);  // hover(); ?
        popUpWarningFound("Test_05E_ApplyInvalid_AddFrtThreshold"  );
        //   sysoutListHTMLxpath("//*[contains(@style,'display:block') ]//*[contains(text(),'This field')]"); // add and or 'or' later
        slowDown(howslow);

        verifyEquals("Expect INPUT 'Add Frt Threshold'  should MATCH entered value AFTER Apply: " ,find(inputAddFrtThreshold).getAttribute("value") ,strTempE );
        System.out.println("INFO, Completed Test_05E_ApplyInvalid_AddFrtThreshold at, " + datetimestamp());

    }


    public void Test_05F_ApplyInvalid_MinChargeThreshold(){

        assertTrue("NOT FOUND: FORM 'Min Charge Threshold' ",isDisplayed( inputMinChargeThreshold,8));
        String strTempF = "Min Charge Threshold accepts integers";
        clear(inputMinChargeThreshold);
        type(strTempF,inputMinChargeThreshold);
        //  WebElement eleMinChargeThreshold = find(inputMinChargeThreshold);
        //     assertEquals("Test 05F: INPUT 'Min Charge Threshold' DOES NOT MATCH VALUE: " ,eleMinChargeThreshold.getAttribute("value") ,strTempF );

        reApply("Test_05F");

        click(inputMinChargeThreshold);  // hover(); ?
        hover(inputMinChargeThreshold);  // hover(); ?  // TODO getting "stale error" -otherwise don't think I need to click AND hover?!
        //    sysoutListHTMLxpath("//*[contains(@style,'display:block') ]//*[contains(text(),'This field')]"); // add and or 'or' later
        popUpWarningFound( "Test_05F_ApplyInvalid_MinChargeThreshold" );
        slowDown(howslow);

        // verifyBoolean("Test_05F Expected WARNING to Display: ", (find(locWARNcommon).isDisplayed() )) ;
        //     verifyEquals("Expect INPUT 'Min Charge Threshold' to MATCH entered value AFTER Apply: " , find(inputMinChargeThreshold).getAttribute("value") ,strTempF );
        System.out.println("INFO, Completed Test_05F_ApplyInvalid_MinChargeThreshold at, " + datetimestamp());

    }


    public void Test_05G_ApplyInvalid_MinOrderPriority(){


        //  G  VERIFY    dropdownChannelOrderPriority             exists, TYPE  "H", assert element VALUE = H  1-5,9 ok, 6,7,8 omitted, invalid dropdownOrderPriorityX
        // 1.5 Order Priority Code only contains Active Order Priority Codes

        verifyBoolean("Expect VISIBLE Drop Down 'Channel Order Priority' ",isDisplayed(dropPriorityX ,8));
        String strTempG = "6";
        clear(dropPriorityX);
        type(strTempG,dropPriorityX);
        WebElement eleChannelOrderPriority = find(dropPriorityX);

        reApply("Test_05G");

        click(dropPriorityX);  // hover(); ?

        verifyBoolean("Invalid INPUT should be rejected by drop down 'Min Order Priority': " , !find(dropPriorityX).getAttribute("value").contentEquals(strTempG ) );

        System.out.println("INFO, Completed Test_05G_ApplyInvalid_MinOrderPriority at, " + datetimestamp());
        slowDown(howslow);
    }


    public void Test_05H_ApplyInvalidEntry_Status(){  // different than OTHERS: only produces warning when empty: this test of invalid manual entry should be impossible to fail.
        //  H  VERIFY   dropdownStatusActiveInactive             exists, TYPE  "A", assert element VALUE = ACTIVE
        //  TODO "1.6 Status Defaults to Active, but user can change."  (actually default is blank: but defaults active if user attempt to enter invalid key)
        verifyBoolean("STATUS: : Drop Down 'Status Active/Inactive' ",isDisplayed( dropdownStatusActiveInactive,8));
        String strTempH = ("Q");

        WebElement eleStatusActiveInactive = find(dropdownStatusActiveInactive);


        reApply("Test_05H");
        slowDown(howslow);

        // click(dropdownStatusActiveInactive);  //
        hover(dropdownStatusActiveInactive);
        verifyBoolean("Test_05H Expect NO  WARNING to Display on key entry: ", (!isDisplayedAndClickable( locWARNstatusActiveInactive, 5 ))) ;


        verifyEquals("INPUT 'STATUS*' should MATCH entered value AFTER Apply: " ,find(dropdownStatusActiveInactive).getAttribute("value") ,strTempH );

        System.out.println("INFO, Completed Test_05H_ApplyInvalidEntry_Status at, " + datetimestamp());


    }




    public void Test_06_InputNewChannelCode_BADCHARS() {   //Test_06 POSITIVE TEST edit all fields with valid data   // CCcodeValidChars is REGEX statement
        //  assertTrue("NOT FOUND: FORM input Entry ''Customer Channel Code''",isDisplayed(inputNewCustomerChannelCode,8 ) );
        String strTempA = CCcodeInvalid;
        clear(inputNewCustomerChannelCode);
        type(strTempA,inputNewCustomerChannelCode);
        WebElement eleCCode = find(inputNewCustomerChannelCode);

        slowDown(howslow);

        reApply("Test_06");
        hover(inputNewCustomerChannelCode);

        verifyBoolean("Test 06 Expect Warning Displayed: ", isDisplayedAndClickable( locWARNCustomerChannelCodeINVALID , 5 )) ;
        System.out.println("INFO, Completed Test_06_InputNewChannelCode_BADCHARS at, " + datetimestamp());
    }


    public void Test_07_InputNewChannelCodeBADLENGTH() {   //Test_07 NEGATIVE TEST edit all fields with INVALID valid data (NOT BLANK, teste elsewhere
        System.out.println("INFO, BEGIN Test_07_InputNewChannelCodeBADLENGTH at, " + datetimestamp());
        String strTempA = "12345 67";
        clear(inputNewCustomerChannelCode);
        type(strTempA,inputNewCustomerChannelCode);
        //WebElement eleCCode = find(inputNewCustomerChannelCode);
        slowDown(howslow);

        reApply("Test 07");
        hover(inputNewCustomerChannelCode); //    hover
        popUpWarningFound("Test_07_InputNewChannelCodeBADLENGTH");
//        verifyBoolean("Test 07 Expected Warning Displayed: ", isDisplayedAndClickable( locWARNCustomerChannelCodeEmpty , 5 )) ;

        System.out.println("INFO, COMPLETE Test_07_InputNewChannelCodeBADLENGTH at, " + datetimestamp());
    }

    public void  Test_08_Input_All_GOODDATA() {
        System.out.println("INFO, BEGIN Test_08_Input_All_GOODDATA at, " + datetimestamp());


        Basic_Input_All_GOODDATA("Test_08_Input_All_GOODDATA");


        System.out.println("INFO, BEGIN Test_08_Input_All_GOODDATA at, " + datetimestamp());
    }

    public void  Basic_Input_All_GOODDATA(String calledByTestName) {
        boolean clickApplyAtEachField = false;

        System.out.println("INFO, BEGIN Test_08_Input_All_GOODDATA at, " + datetimestamp());
/* A */
        String strTempA = strRandChars( CCcodeMax ); // CCcodeMin
        clear(inputNewCustomerChannelCode);
        type(strTempA,inputNewCustomerChannelCode);
        //WebElement eleCCode = find(inputNewCustomerChannelCode);

        if (clickApplyAtEachField ) reApply( calledByTestName + "_A");


        focusOn(inputNewCustomerChannelCode);
        verifyBoolean(calledByTestName + " Expected NO WARNING to Display: ", !(isDisplayedAndClickable( locWARNcommon, 5 ))) ;



/* B */         //  B  VERIFY  inputNewCustomerChannelDescrip             exists, TYPE  "0123456789ABCDEF 0123456789ABCDEF", assert element VALUE = same
        assertTrue("NOT FOUND: FORM input Entry ''Customer Channel Description''",isDisplayed(inputNewCustomerChannelDescrip,8 ) );
        //   String strTempB = CCdescTooLong;  //  "characters 31 WARNs in red!";  // = datetimestamp();
        String strTempB = CCdescOK;   //characters 29 OK, no warnings
        clear(inputNewCustomerChannelDescrip);
        type(strTempB,inputNewCustomerChannelDescrip);
//          WebElement eleCDesc = find(inputNewCustomerChannelDescrip);  // already FOUND IT but need element attributes

        if (clickApplyAtEachField ) reApply(calledByTestName + "_B" );


        String newStrTempB = find(inputNewCustomerChannelDescrip).getAttribute("value");
        boolean validCCDesc_length = newStrTempB.length() > 0 && newStrTempB.length()<=30 ;


        verifyBoolean("Expect Length ("+ strTempB.length() + ") between " + CCdescMin + " and " + CCdescMax + " visible characters: " ,validCCDesc_length);
        verifyBoolean(calledByTestName + " Expected NO EMPTY  WARNING to Display: ", !(isDisplayedAndClickable( locWARNCustomerChannelDescripEmpty , 5 ))) ;
        verifyBoolean(calledByTestName + " Expected NO Length WARNING to Display: ", !(isDisplayedAndClickable( locWARNCustomerChannelDescripBadChars, 5 ))) ;


/* C */
        //  C  VERIFY  inputAbsOrderMin  exists, TYPE  "A1", assert element VALUE = same
        assertTrue("NOT FOUND: FORM input Entry ''Abs Order Min''",isDisplayedAndClickable(inputAbsOrderMin,8 ) );
        // 1.4 Abs Ord Min can only contain numbers          //   float seedAbsOrder = roundfloat(99f,2);

        String strTempC = randIntegerAsString(  CCThresholdsMIN,CCThresholdsMAX ); // ***warning modified by line below
        clear(inputAbsOrderMin);
        type(strTempC,inputAbsOrderMin);
        if (clickApplyAtEachField ) reApply(calledByTestName + "_C" );


        verifyEquals("INPUT AbsOrderMin  should MATCH VALUE: " ,find(inputAbsOrderMin).getAttribute("value") ,strTempC );
        slowDown(howslow);


/* D */
        //  D  VERIFY  inputDropShipThreshold   exists, TYPE  "2B", assert element VALUE = same
        assertTrue("NOT FOUND: FORM input Entry Drop Ship Threshold",isDisplayed( inputDropShipThreshold,8));

        String strTempD = randIntegerAsString(  CCThresholdsMIN,CCThresholdsMAX );
        clear(inputDropShipThreshold);
        type(strTempD,inputDropShipThreshold);
        if (clickApplyAtEachField ) reApply(calledByTestName + "_D" );

        // WebElement eleDropShipThreshold = find(inputDropShipThreshold);
        verifyEquals("Expect INPUT 'Drop Ship Threshold' to MATCH stored VALUE: ",find(inputDropShipThreshold).getAttribute("value") ,strTempD );
        slowDown(howslow);

/* E */    //  E  VERIFY  inputAddFrtThreshold     exists, TYPE  "-0101", assert element VALUE = -101
        verifyBoolean( " NOT FOUND: FORM 'Add Frt Threshold' ",isDisplayed( inputAddFrtThreshold,8));
        String strTemp8E = randIntegerAsString(  CCThresholdsMIN,CCThresholdsMAX );
        clear(inputAddFrtThreshold);
        type(strTemp8E,inputAddFrtThreshold);   // 2017-05-30-135626         Expected :5.55552         Actual   :2

        if (clickApplyAtEachField ) reApply(calledByTestName + "_E" );

        assertEquals(calledByTestName + " INPUT 'Add Frt Threshold' DOES NOT MATCH : " ,find(inputAddFrtThreshold).getAttribute("value") ,strTemp8E );
        slowDown(howslow);

/* F */
        //  F  VERIFY  inputNewCustomerMinChargeThreshold         exists, TYPE  "1234567890", assert element VALUE = same
        // WebElement eleMinChargeThreshold = find(inputMinChargeThreshold);
        assertTrue("NOT FOUND: FORM 'Min Charge Threshold' ",isDisplayed( inputMinChargeThreshold,8));
        String strTemp8F = randIntegerAsString(  CCThresholdsMIN,CCThresholdsMAX );
        clear(inputMinChargeThreshold);
        type(strTemp8F,inputMinChargeThreshold);
        if (clickApplyAtEachField ) reApply(calledByTestName + "_F" );

        assertEquals("INPUT 'Min Charge Threshold' DOES NOT MATCH VALUE: " ,find(inputMinChargeThreshold).getAttribute("value") ,strTemp8F );
        slowDown(howslow);

/* G */         //  G  VERIFY    dropdownChannelOrderPriority      exists, TYPE  "H", assert element VALUE = H  1-5,9 ok, 6,7,8 omitted, invalid dropdownOrderPriorityX
        // 1.5 Order Priority Code only contains Active Order Priority Codes

        verifyBoolean("Expect VISIBLE Drop Down 'Channel Order Priority' ",isDisplayed(dropPriorityX ,8));
        String strTempG = "9";
//TODO COMPARE DROP DOWN BOX WITH DATABASE TABLE/VIEW TBD
        clear(dropPriorityX);
        type(strTempG,dropPriorityX);
        if (clickApplyAtEachField ) reApply(calledByTestName + "_G" );
        assertEquals(calledByTestName +" Expect the drop down ''Order Priority Code'' to MATCH user INPUT: " ,find(dropPriorityX).getAttribute("value") ,strTempG );
        slowDown(howslow);

/* H */
        //  H  VERIFY   dropdownStatusActiveInactive             exists, TYPE  "A", assert element VALUE = ACTIVE
        // 1.6 Status Defaults to Active, but user can change.
        verifyBoolean(calledByTestName + " STATUS: : Drop Down 'Status Active/Inactive' ",isDisplayed( dropdownStatusActiveInactive,8));
        String strTemp8H = ("A");
        clear(dropdownStatusActiveInactive);
        type(strTemp8H,dropdownStatusActiveInactive);

        // if (clickApplyAtEachField ) reApply(calledByTestName + "_H" );
//        reApply(calledByTestName + "_H" );

        verifyEquals ("Expect the drop down 'STATUS' to MATCH user selected VALUE: " ,find(dropdownStatusActiveInactive).getAttribute("value") ,strTemp8H );
        slowDown(howslow);
        System.out.println("INFO, END " +  calledByTestName + " at, " + datetimestamp());
    }

    public void Test_09_ApplyButtonAfterWarnings() {
        // *******  ASSUMES PREVIOUS TEST SUCCESSFUL  - TODO COLLECT EACH VALID FIELD and then APPLY
        System.out.println("INFO, BEGIN Test_09_ApplyButtonAfterWarnings at, " + datetimestamp());
        verifyBoolean("Before Apply: Expect button APPLY is Visible: ",isDisplayed( buttonApply,5 ));
        verifyBoolean("Before Apply: Expect button CANCEL is Visible: ",isDisplayed( buttonNewCustomerChannelCancel,5 ));
        verifyBoolean("Before Apply: Expect icon  UNDO is Visible: ",isDisplayed( buttonUndoCtrlAltZ,5 ));
        verifyBoolean("Before Apply: Expect button SAVE is HIDDEN: ",!(isDisplayed( buttonSaveChanges,5 )));


        assertTrue("STATUS: : field input  'Min Charge Threshold' ",isDisplayed( inputMinChargeThreshold,8));
        String strTemp9 = ("Testing Error handling is in progress ...");
        clear(inputMinChargeThreshold);
        type(strTemp9,inputMinChargeThreshold);

        reApply("Test Test_09_ApplyButtonAfterWarnings");;  // IF WARNing[s] then TESTs X else TESTsY  Apply



        verifyBoolean("After Apply: Expect button APPLY is Visible: ",isDisplayed( buttonApply,5 ));
        verifyBoolean("After Apply: Expect button CANCEL is Visible: ",isDisplayed( buttonNewCustomerChannelCancel,5 ));
        verifyBoolean("After Apply: Expect icon  UNDO is Visible: ",isDisplayed( buttonUndoCtrlAltZ,5 ));
        verifyBoolean("After Apply: Expect button SAVE is HIDDEN: ",!(isDisplayed( buttonSaveChanges,5 )));

        slowDown(howslow);
        System.out.println("INFO, COMPLETE Test_09_ApplyButtonAfterWarnings at, " + datetimestamp());
    }

    public void Test_10_ApplyButtonAfterNoWarnings() {
        System.out.println("INFO, Test_10_ApplyButtonAfterNOWARNINGS at, " + datetimestamp());

    /*    find(buttonApply);

        click(buttonApply);*/

     Test_08_Input_All_GOODDATA();

        //    UNDO				is VISIBLE    - - check the state of APPLY BUTTON, during the INPUT TEST ABOVE - Assert VISIBLE
        //    save 				is INvisible  - buttonSaveChanges - check the state of APPLY BUTTON, during the INPUT TEST ABOVE - Assert INVISIBLE
        verifyBoolean("Expect button APPLY is HIDDEN: ",!isDisplayed( buttonApply,5 ));
        verifyBoolean("Expect button CANCEL is Visible: ",isDisplayed( buttonNewCustomerChannelCancel,5 ));
        verifyBoolean("Expect icon  UNDO is HIDDEN: ",!isDisplayed( buttonUndoCtrlAltZ,5 ));
        verifyBoolean("Expect button SAVE IS VISIBLE: ",(isDisplayed( buttonSaveChanges,5 )));
        slowDown(howslow);


        System.out.println("INFO, COMPLETE Test_10_ApplyButtonAfterNOWARNINGS at, " + datetimestamp());
    }

    public void Test_11_CheckWarningsDescription() {

        // 05 FIELD VALIDATION WARNINGS - for each field
        System.out.println("EMPTY TEST: 'Test_11_CheckWarningsDescription' : moved to TEST_05");

    }



    public void Test_99_CloseDown() {   //Test_07 NEGATIVE TEST edit all fields with INVALID valid data (NOT BLANK, teste elsewhere
        slowDown(8);
        if (MULTITEST) { slowDown(8);};
        System.out.println("INFO, Completed Test99_CloseDown at, " + datetimestamp());
    }


































    private  void reApply(String testName) {

        if (verifyBoolean("Expected button 'Apply' is Displayed and Clickable",isDisplayedAndClickable( buttonApply,5 ) )) {
            click(buttonApply);
        } else
            assertTrue( "FAILED " + testName + " (Expected that button 'APPLY' would be visible and clickable) ", isDisplayedAndClickable( buttonApply, 5 ) );

    }


    public static String strRandChars(int CharLen) {
        String result = ""; int k;
        for( k=0;k< CharLen; k++){
            char intRand  = (char)(Math.random() * 26 + 65 );
            result += intRand;
        }
        return result;
    }
    public static int randInteger(int min, int max) {
        int result = 0;
        int intRand  = (int)(Math.random() * (max-min)  + min);
        return result;
    }
public  void popUpWarningFound(String testName){

    boolean tempTest = waitForIsDisplayed( locWARNcommon,5);
    if (tempTest ){
        WebElement elePopupWarning = find(locWARNcommon);
        String readElementProps =  testName + "  Absolute Order MinimumDescription Warning '" + elePopupWarning.getText()
                + "' properties: Visibility: " + elePopupWarning.isDisplayed() + ", Enabled: " + elePopupWarning.isEnabled();
        System.out.println(readElementProps);} else verifyBoolean("Warning not shown in test: " + testName,tempTest);
    slowDown(howslow);
}
    public static String randIntegerAsString(int min, int max) {
        int intRand  = (int)(Math.random() *  (max-min)  + min);
        return Integer.toString(intRand);

    }

    public static String randFloatAsString(float min, float max, int decimalplaces) {
        float flRand  = (float)(Math.random() *  (max-min)  + min);
        flRand = roundfloat(flRand,2);
        return Float.toString(flRand);
    }


    public static String datetimestamp() {
        String Tstamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss.SS" ).format( Calendar.getInstance().getTime() );
        return Tstamp;
    }

    public static int askUserYN(String msgTitle, String msg) {
        int qy = JOptionPane.showConfirmDialog(null,
                msg, msgTitle, JOptionPane.YES_NO_OPTION);
        return qy;  // NO ==> if (qy==1 || qy==-1 ) { System.out.println("CANCELLED BY USER!"); System.exit(1);  	 }
        //  askUserYN("TESTER ACTION REQUIRED before LOGGING properties to console","ENTER KNOWN VALUES IN DROP DOWN BOXES\n\nPress Any Key [ Y ] [ N ] [ Esc ]  to log properties to Console");
    }

    public static boolean verifyEquals(String message, String  expected, String actual) {
        boolean isverified = false;
        if(expected.equals(actual)) {
            //  isverified =  false;
            if(expected instanceof String && actual instanceof String) {
                String cleanMessage = message == null?"":message;
                System.out.println("Verified Equality,  SUCCESS: " + message + " Expected: (" +  expected +") == Found: (" +  actual + ").");
                //  throw new ComparisonFailure(cleanMessage, (String)expected, (String)actual);
                isverified = true;
            } else System.out.println( "Verify NOT EQUAL, FAIL : " + message + " Expected: "
                    + expected + " of length (" + message.length() + ")  But found: " + expected.length() + ")." );
        } return isverified;
    }

    public static boolean verifyBoolean(String message, boolean condition) {
        if(condition) {
            String cleanMessage = message == null?"":message;
            System.out.println("Verify SUCCESS : " + message );
            return true;
        }
        System.out.println("Verify FAIL : " + message );
        return false;
    }



    /*      --- -- - -- --- *** --- -- - -- ---  */
    public void sysoutListHTMLlocator(By byLocator) {         //(Logger logger, WebDriver driver) {

        List<WebElement> elements= finds(byLocator);

        // CONSTANTs for logging to an HTML table
        //	final String HTMLtableBegin = "<TABLE align=left border=1 width=\"88%\">";
        final String HTMLtableEnd = "</TABLE>";

        final String ROWNEW = "<TR ><TD>" ;  	// String ROWNEW = "<TR valign=top><TD>" ;
        final String ROWEND = "</TD></TR>" ;  // " &nbsp; </TD></TR>" ;
        final String DELIM = "</TD><TD>" ;

        String bgcolor = "BGCOLOR='#E6FFFF'";  //""; // light green #DEFDE7	lightcyan #DFF4FA;



        //Now iterate through List and do the required operation with each individual element
        int zy = 0; // initialize outside of loop, only increment when TAB not excluded
        // System.out.println(ROWNEW + zy + DELIM + "tagName" + DELIM + "id" + DELIM + "class" 		+ DELIM + "name" + DELIM + "getText" + ROWEND );
        System.out.println("<TABLE width='100%' border=1 ><TR><TD colspan=9 " + bgcolor +">" + byLocator.toString() + ": " +  ROWEND );
        System.out.println(ROWNEW + "#"
                + DELIM + "tagName" 		+ DELIM + "id"		+ DELIM + "name"
                + DELIM + "value" 		+ DELIM + "class"		+ DELIM + "style"
                + DELIM + "visible?"
                // + DELIM +  "innerHTML"
                + ROWEND );

        for(WebElement elem:elements) {
            zy++;
            if ( (zy & 1) == 0 ) { bgcolor = ""; } else bgcolor = "BGCOLOR='#E6FFFF'";
            System.out.println("<TR valign=top " + bgcolor + "><TD>" + zy
                    + DELIM + elem.getTagName()         + DELIM + elem.getAttribute("id")
                    + DELIM + elem.getAttribute("name")        + DELIM + elem.getAttribute("value")
                    + DELIM + elem.getAttribute("class")        + DELIM + elem.getAttribute("style")
                    + DELIM + "visible: " + elem.isDisplayed() + "<br \\>enabled: " + elem.isEnabled()
                    //     + DELIM + elem.getAttribute("innerHTML")
                    + ROWEND );          } // for each webelement
        System.out.println(HTMLtableEnd);

    }  // end method sysoutListHTML

    public void sysoutListHTMLxpath(String strXpath) {         //(Logger logger, WebDriver driver) {
        By byLocator = By.xpath(strXpath);
        List<WebElement> elements= finds(byLocator);

        String substrExcludetags = "HEAD,HTML,BODY,STYLE,SCRIPT,META,LINK,TABLE,TBODY";  // tables too big, catch only TR and TD
      List<String> excludeList = 	asList(substrExcludetags.split("\\s*,\\s*"));


        // CONSTANTs for logging to an HTML table
        //	final String HTMLtableBegin = "<TABLE align=left border=1 width=\"88%\">";
        final String HTMLtableEnd = "</TABLE>";

        final String ROWNEW = "<TR ><TD>" ;  	// String ROWNEW = "<TR valign=top><TD>" ;
        final String ROWEND = "</TD></TR>" ;  // " &nbsp; </TD></TR>" ;
        final String DELIM = "</TD><TD>" ;

        String bgcolor = "BGCOLOR='#E6FFFF'";  //""; // light green #DEFDE7	lightcyan #DFF4FA;



        //Now iterate through List and do the required operation with each individual element
        int zy = 0; // initialize outside of loop, only increment when TAB not excluded
        // System.out.println(ROWNEW + zy + DELIM + "tagName" + DELIM + "id" + DELIM + "class" 		+ DELIM + "name" + DELIM + "getText" + ROWEND );
        System.out.println("<TABLE width='100%' border=1 ><TR><TD colspan=9 " + bgcolor +">" + byLocator.toString() + ": " +  ROWEND );
        System.out.println(ROWNEW + "#"
                + DELIM + "tagName" 		+ DELIM + "id"		+ DELIM + "name"
                + DELIM + "value" 		+ DELIM + "class"		+ DELIM + "style"
                + DELIM + "visible?"
                // + DELIM +  "innerHTML"
                + ROWEND );

        for(WebElement elem:elements) {
            if (!(excludeList.contains( elem.getTagName().toUpperCase() ))) {  // ONLY INSPECT ELEMENTS *NOT* IN EXCLUDE LIST
                zy++;
                if ((zy & 1) == 0) {
                    bgcolor = "";
                } else bgcolor = "BGCOLOR='#E6FFFF'";
                System.out.print( "<TR valign=top " + bgcolor + "><TD>" + zy );
                System.out.print( DELIM + elem.getTagName() + DELIM + elem.getAttribute( "id" ) );
                System.out.print( DELIM + elem.getAttribute( "name" ) + DELIM + elem.getAttribute( "value" ) );
//            System.out.print( DELIM + elem.getAttribute("class"));
                System.out.print( DELIM + elem.getAttribute( "style" ) );
                System.out.print( DELIM + "visible: " + elem.isDisplayed() );
                System.out.print( "<br \\>enabled: " + elem.isEnabled() );
                System.out.print( DELIM + elem.getAttribute( "innerHTML" ) );
                System.out.println( ROWEND );
               } // close if
            } // for each webelement

        System.out.println(HTMLtableEnd);

    }  // end method sysoutListHTML

    public  void sysoutWebElementsToHTM() {         //(Logger logger, WebDriver driver) {
        // (locator1_CreateInDataViewlink);  // 158
        String EXCLUDETAGS = "HEAD,HTML,BODY,STYLE,SCRIPT,META,LINK,TABLE,TBODY";

        // CONSTANTs for logging to an HTML table
        //	final String HTMLtableBegin = "<TABLE align=left border=1 width=\"88%\">";
        final String HTMLtableEnd = "</TABLE>";

        final String ROWNEW = "<TR ><TD>" ;  	// String ROWNEW = "<TR valign=top><TD>" ;
        final String ROWEND = "</TD></TR>" ;  // " &nbsp; </TD></TR>" ;
        final String DELIM = "</TD><TD>" ;

        String bgcolor = "BGCOLOR='#ecffec'";  //""; // light green #DEFDE7	lightcyan #DFF4FA;

        List<WebElement> elements= finds(By.xpath(".//*"));
        List<String> excludeList = 	asList(EXCLUDETAGS.split("\\s*,\\s*"));

        //Now iterate through List and do the required operation with each individual element
        int zy = 0; // initialize outside of loop, only increment when TAB not excluded

        System.out.println("<TABLE width='100%' border=1 ><TR><TD colspan=9 " + bgcolor +">" + ""  + /*": " +  driver.getTitle()   + " ~ ~ " +  getCurrentUrl()  + */  ROWEND );
        System.out.println(ROWNEW + "#"
                + DELIM + "tagName"
                + DELIM + "id"
                + DELIM + "name"
                + DELIM + "value"
                + DELIM + "class"
                + DELIM + "style"
                + DELIM + "visible?"
                 + DELIM + "innerHTML"
                + ROWEND );
        for(WebElement elem:elements) {
            if ( !(excludeList.contains(elem.getTagName().toUpperCase())) ){
                zy++;
                if ( (zy & 1) == 0 ) { bgcolor = ""; } else bgcolor = "BGCOLOR='#ecffec'";
// lightgreen #0D0F0A #0D0D0A	lightcyan #0C0CFF;
                // String bgcolor = "#dcf395;"; //  #dcf395;	green #0D0D0A;	lightcyan #0C0CFF;	#c5ffff;
                System.out.println("<TR valign=top " + bgcolor + "><TD>" + zy
                        + DELIM + elem.getTagName()
                        + DELIM + elem.getAttribute("id")
                        + DELIM + elem.getAttribute("name")
                        + DELIM + elem.getAttribute("value")
                        + DELIM + elem.getAttribute("class")
                        + DELIM + elem.getAttribute("style")
                        + DELIM + "visible: " + elem.isDisplayed() + "<br \\>enabled: " + elem.isEnabled()
                        //              + DELIM + elem.getAttribute("innerHTML")
                        + ROWEND );
            }  // if not excluded
        } // for each webelement
        System.out.println(HTMLtableEnd);

    }

    public String stripCharsByRegex(String regTest, String testthis) {          //  https://stackoverflow.com/questions/5071236/java-regexp-to-match-ascii-characters#5071265
        Pattern asciiPattern = Pattern.compile(regTest);   // ("\\p{ASCII}*$");   // [a-zA-Z0-9]
        Matcher matcher = asciiPattern.matcher(testthis);
        String asciiString = null;
        if (matcher.find()) {
            asciiString = matcher.group();
        }
//        return (asciiString.equalsIgnoreCase(testthis));
        return asciiString;
    }

    public static String affixTimeStamp(String Basename) // throws IOException { // used to setup filepath,
    {
        try {
            String newname = new SimpleDateFormat("yyyy-MM-dd_HHmmss" ).format( Calendar.getInstance().getTime() );
            Basename  = Basename + "_" + newname;
        } catch (Exception e) {
            System.out.println("FAIL TRY/CATCH in  affixTimeStamp");
            e.printStackTrace() ;
        }

        return Basename;
    }

    public static float roundfloat(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


} // END OF CLASS



/*             - - - - - -            */

// 04 CC BUTTON VISIBILITY TESTS AFTER SUCCESSFUL VALIDATION
//    apply button			is INvisible    buttonApply
//    cancel button			ALWAYS VISIBLE buttonCancelEdit
//    UNDO button			is VISIBLE      (alt+ctrl_c)
//
//    save button			is visible     buttonSaveChanges, buttonSAVEcontainingDivTableTD
//    send for approval     is visible     buttonSendForApproval // OUT OF SCOPE: CLICKING IT LEADS TO A KNOWN ISSUE per Customer Cathy Bass