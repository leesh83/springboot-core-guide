package kevin.springboot.core.guide.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import kevin.springboot.core.guide.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER) // 설명 :
    @Builder.Default // 설명 :
    @Enumerated(value = EnumType.STRING)
    private List<UserRole> roles;

    protected User() {
    }

    @Builder
    public User(Long id, String email, String password, String name, List<UserRole> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    //계정이 가지고 있는 권한 목록 리턴
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.toString()))
                    .collect(Collectors.toList());
    }

    //계정 패스워드 리턴
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public String getPassword() {
        return password;
    }

    //계정의 이름리턴. 일반적으로 유니크한 아이디, 이메일을 리턴합니다.
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public String getUsername() {
        return email;
    }

    //계정 만료여부 리턴 (true = 만료되지 않음, false = 만료됨)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있는지 리턴 (true = 잠김 되지 않음, false = 잠김)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되었는지 리턴 (true = 만료되지 않음, false = 만료됨)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화인지 리턴 (true = 활성화, false = 비활성화)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
