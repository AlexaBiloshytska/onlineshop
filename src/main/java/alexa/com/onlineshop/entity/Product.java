package alexa.com.onlineshop.entity;

public class Product {
    private Integer id;
    private String productName;
    private String productType;
    private String description;
    private Integer stock;
    private Integer price;
    private String imageSource;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductname() {
        return productName;
    }

    public void setProductname(String productname) {
        this.productName = productname;
    }

    public String getProducttype() {
        return productType;
    }

    public void setProducttype(String producttype) {
        this.productType = producttype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productname='" + productName + '\'' +
                ", producttype='" + productType + '\'' +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", imageSource='" + imageSource + '\'' +
                '}';
    }
}