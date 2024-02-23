package kevin.springboot.core.guide.exception;


public class ProductNotFoundException extends CustomException {

    private static final String message = "해당 상품이 없습니다. id : ";

    public ProductNotFoundException(Long productId) {
        super(message + productId);
    }
}
