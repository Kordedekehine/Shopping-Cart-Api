package ShoppingCartsAPI.carts.dtos.user;

public class LoginResponseDto {

    private String status;
    private String token;

    public LoginResponseDto(String status, String token) {
        this.status = status;
        this.token = token;
    }
}
