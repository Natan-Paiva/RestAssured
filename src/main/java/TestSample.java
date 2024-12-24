import java.io.IOException;
import java.util.ArrayList;

public class TestSample {
    public static void main(String[] args) throws IOException {
        DataDriven dd = new DataDriven();
        ArrayList<String> data = dd.getData("AddProfile");
        System.out.println(data);
        System.out.println(data.get(1));
        System.out.println(data.get(2));
        System.out.println(data.get(3));
    }
}
