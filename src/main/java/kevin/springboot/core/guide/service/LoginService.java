package kevin.springboot.core.guide.service;

import kevin.springboot.core.guide.dto.LoginRequest;
import kevin.springboot.core.guide.dto.LoginResponse;
import kevin.springboot.core.guide.dto.SignUpRequest;
import kevin.springboot.core.guide.entity.User;
import kevin.springboot.core.guide.exception.DuplicatedEmailException;
import kevin.springboot.core.guide.exception.InvalidPasswordException;
import kevin.springboot.core.guide.exception.UserNotFoundException;
import kevin.springboot.core.guide.jwt.JwtTokenProvider;
import kevin.springboot.core.guide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //회원가입
    public boolean signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicatedEmailException();
        }
        userRepository.save(createUser(request));
        return true;
    }

    //유저생성
    private User createUser(SignUpRequest request) {
        return User.builder()
                   .email(request.getEmail())
                   .password(bCryptPasswordEncoder.encode(request.getPassword()))
                   .name(request.getName())
                   .roles(request.getRoles())
                   .build();
    }

    //로그인
    public LoginResponse logIn(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                                  .orElseThrow(() -> new UserNotFoundException(request.getEmail()));

        //패스워드 검증
        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        //토큰 생성
        String token = jwtTokenProvider.createToken(user);

        return new LoginResponse(token);
    }
}
