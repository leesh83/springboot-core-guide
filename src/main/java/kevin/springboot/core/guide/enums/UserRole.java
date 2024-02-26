package kevin.springboot.core.guide.enums;

public enum UserRole {
    ADMIN("통합 관리자"),
    MANAGER("매니저"),
    USER("사용자");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }
}
