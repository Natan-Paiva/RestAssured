import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.Order;
import pojo.Orders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ECommerceAPITest {
    public static void main(String[] args){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("nptester@gmail.com");
        loginRequest.setUserPassword("Npt.12345");

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();
        //relaxedHTTPSValidation is method tha lets RestAssured bypass SSL certificate verifications
        RequestSpecification reqLogin = given().relaxedHTTPSValidation().spec(req).body(loginRequest);
        LoginResponse loginResponse = reqLogin.post("api/ecom/auth/login")
                .then().extract().response().as(LoginResponse.class);

        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();
        System.out.println(token);
        System.out.println(userId);

        //AddProduct
        RequestSpecification addProductReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token).build();

        RequestSpecification reqAddProduct = given().spec(addProductReq)
                .param("productName", "Dnd Character")
                .param("productAddedBy", userId)
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirts")
                .param("productPrice", "11500")
                .param("productDescription", "DnD")
                .param("productFor", "man")
                .multiPart("productImage", new File("/Users/Natan/Downloads/hjolmir.png"));
        String addProductResponse = reqAddProduct.when().post("api/ecom/product/add-product")
                .then().log().all().extract().response().asString();

        JsonPath js = new JsonPath(addProductResponse);
        String prodId = js.getString("productId");
        System.out.println(prodId);

        //PlaceOrder
        Order order = new Order();
        order.setCountry("Brazil");
        order.setProductOrderedId(prodId);
        List <Order> orderList = new ArrayList<Order>();
        orderList.add(order);
        Orders orders = new Orders();
        orders.setOrders(orderList);


        RequestSpecification placeOrderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .setContentType(ContentType.JSON).build();

        RequestSpecification reqPlaceOrder = given().spec(placeOrderReq).body(orders);
        String placeOrderRes = reqPlaceOrder.when().post("api/ecom/order/create-order")
                .then().log().all().extract().response().asString();

        //DeleteProduct
        RequestSpecification deleteProdReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .setContentType(ContentType.JSON).build();

        RequestSpecification reqDeleteProd = given().spec(deleteProdReq).pathParam("productId", prodId);
        String deleteProdRes = reqDeleteProd.when().delete("api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();
        JsonPath js1 = new JsonPath(deleteProdRes);
        String deleteMsg = js1.getString("message");
        Assert.assertEquals("Product Deleted Successfully", deleteMsg);
    }
}
