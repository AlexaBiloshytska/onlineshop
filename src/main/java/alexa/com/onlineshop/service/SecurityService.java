package alexa.com.onlineshop.service;

public interface SecurityService {
    void addToken(String token);
    boolean isValid(String token);
}
