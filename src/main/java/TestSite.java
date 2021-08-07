public class TestSite{

    public static void main(String[] args)  {


        ChromeSite s1 = new ChromeSite("https://deferralpro.stagemben.com/Public/Login");
        System.out.println(s1.runChromeTest());

        //FirefoxSite s2 = new FirefoxSite("https://deferralpro.stagemben.com/Public/Login");
        //System.out.println(s2.runFirefoxTest());

        //EdgeSite s3 = new EdgeSite("https://deferralpro.stagemben.com/Public/Login");
        //System.out.println(s3.runEdgeTest());



    }
}
