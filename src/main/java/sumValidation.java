import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class sumValidation {
    @Test
    public void sumOfCourses(){
        JsonPath js = new JsonPath(payload.CoursePrice());
        int coursesQty = js.getInt("courses.size()");
        int purchaseAmt = js.getInt("dashboard.purchaseAmount");

        int totalAmount = 0;
        for(int i=0; i<coursesQty; i++){

            int coursePrice = js.getInt("courses["+ i +"].price");
            int courseCopies = js.getInt("courses["+ i +"].copies");
            totalAmount += (coursePrice * courseCopies);
        }
        Assert.assertEquals(totalAmount, purchaseAmt);
    }
}
