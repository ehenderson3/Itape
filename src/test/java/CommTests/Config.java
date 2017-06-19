package CommTests;


public interface Config {

    final String baseUrl            = System.getProperty("baseUrl", "http://ipg-mdm-mdmdv:8080");
    //final String baseUrl            = System.getProperty("baseUrl", "https://comsearch.dev.surgeforward.com/#/");
    //final String baseUrl            = System.getProperty("baseUrl", "https://legosandbox.com");

    final String browser            = System.getProperty("browser", "chrome");
    final String host               = System.getProperty("host","localhost");// "localhost" or browserstack
    final String browserVersion     = System.getProperty("browserVersion","52");
    final String platform           = System.getProperty("platform","Windows");
    final String os_version         = System.getProperty("os_version","10");
    final String resolution         = System.getProperty("resolution","1920x1080");




//////////////////////////     SUPPORTED BROWSERS      \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//    ("browser", "chrome");  ("browserVersion","58");  ("browserVersion","57");
//    ("browser", "Firefox");  ("browserVersion","52");  ("browserVersion","53");
//    ("browser", "IE");  ("browserVersion","11");
//    ("browser", "Edge");  ("browserVersion","14"); ("browserVersion","13");
//
//////////////////////////     SUPPORTED OS's           \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//    ("platform","Windows"); ---> ("os_version","10"); ("os_version","8.1");  ("os_version","8");  ("os_version","7");  ("os_version","XP");
//    ("platform","OS X"); ---> ("os_version","Sierra"); ("os_version","El Capitan");  ("os_version","Yosemite");
//
//
//
//      IE11
//      Edge (latest 2 versions)
//      Chrome (latest 2 versions)
//      Firefox (latest 2 versions)
//      Safari (latest 2 versions)
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
