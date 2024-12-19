import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexJsonParse {
    public static  void main(String[] args){
        JsonPath js = new JsonPath(payload.CoursePrice());
        int coursesQty = js.getInt("courses.size()");
        System.out.println(coursesQty);
        int purchaseAmt = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmt);
        String firstCourseTitle = js.getString("courses[0].title");
        System.out.println(firstCourseTitle);

        //print all courses titles and prices
        for(int i=0; i<coursesQty; i++){
            String courseTitle = js.getString("courses["+ i +"].title");
            int coursePrice = js.getInt("courses["+ i +"].price");
            //System.out.println(js.get("courses["+ i +"].price").toString()); - use toString if want to print directly

            System.out.println(courseTitle + " - " + coursePrice);
        }

        //print copies sold of RPA course
        for(int i=0; i<coursesQty; i++){
            String courseTitle = js.getString("courses["+ i +"].title");
            if(courseTitle.equalsIgnoreCase("RPA")){
                int copiesSold = js.getInt("courses["+ i +"].copies");
                System.out.println(copiesSold);
                break;
            }
        }

        //verify if sum of all courses prices matches with purchase
        int totalAmount = 0;
        for(int i=0; i<coursesQty; i++){

            int coursePrice = js.getInt("courses["+ i +"].price");
            int courseCopies = js.getInt("courses["+ i +"].copies");
            totalAmount += (coursePrice * courseCopies);
        }
        Assert.assertEquals(totalAmount, purchaseAmt);
    }
}
