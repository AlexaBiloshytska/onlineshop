package alexa.com.onlineshop.entity;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Session {
    private String token;
    private User user;
    private List<Product> cart = new ArrayList<>();
    private LocalDateTime expireDate;
    private List<Category> categories = new ArrayList<>();

    public Session(String token, User user, LocalDateTime expireDate) {
        this.token = token;
        this.user = user;
        this.expireDate = expireDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addProductToCard(Product product) {
        cart.add(product);
    }

    public List<Product> getCart() {
        return new ArrayList<>(cart);//Copy constructor pattern
    }


    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "SecurityService{" +
                "token='" + token + '\'' +
                ", user=" + user +
                ", cart=" + cart +
                ", expireDate=" + expireDate +
                '}';
    }
}
