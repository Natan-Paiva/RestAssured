// this is not used like in the classes, I decided to have a single class for all courses


package pojo;

public class Mobile {
    private String courseTitle;
    private String price;


    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
