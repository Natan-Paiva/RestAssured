package files;

public class payload {
    public static String AddPlace(){
        return "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -39.383494,\n" +
                "    \"lng\": 34.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"testing\",\n" +
                "  \"phone_number\": \"(+91) 1111111111\",\n" +
                "  \"address\": \"29, side layout, cohen 09\",\n" +
                "  \"types\": [\n" +
                "    \"shoe park\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}\n";
    }

    public static String UpdatePlace(String id, String address){
        return "{\n" +
                "\"place_id\":\""+id+"\",\n" +
                "\"address\":\""+address+"\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}";
    }

    public static String CoursePrice(){
        return "{\n" + "\n" + "\"dashboard\": {\n" +
                "\n" + "\"purchaseAmount\": 910,\n" + "\n" +
                "\"website\": \"rahulshettyacademy.com\"\n" + "\n" +
                "},\n" + "\n" + "\"courses\": [\n" +
                "\n" + "{\n" + "\n" + "\"title\": \"Selenium Python\",\n" +
                "\n" + "\"price\": 50,\n" + "\n" + "\"copies\": 6\n" +
                "\n" + "},\n" + "\n" + "{\n" + "\n" +
                "\"title\": \"Cypress\",\n" + "\n" +
                "\"price\": 40,\n" + "\n" + "\"copies\": 4\n" +
                "\n" + "},\n" + "\n" + "{\n" + "\n" +
                "\"title\": \"RPA\",\n" + "\n" +
                "\"price\": 45,\n" + "\n" +
                "\"copies\": 10\n" + "\n" + "}\n" +
                "\n" + "]\n" + "\n" + "}";
    }
    public static String AddBook(String aisle, String isbn){
        String payload = "{\n" +
                "\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"John foe\"\n" +
                "}\n";

        return payload;
    }
}
