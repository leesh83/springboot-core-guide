package kevin.springboot.core.guide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kevin.springboot.core.guide.dto.ProductRequest;
import kevin.springboot.core.guide.dto.ProductResponse;
import kevin.springboot.core.guide.entity.User;
import kevin.springboot.core.guide.enums.UserRole;
import kevin.springboot.core.guide.jwt.JwtTokenProvider;
import kevin.springboot.core.guide.repository.UserRepository;
import kevin.springboot.core.guide.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @WebMvcTest(테스트대상 콘트롤러.class) = mvc 요청,응답에 대한 테스트 가능.
 * @Controller, @RestController, @ControllerAdvice 등의 컨트롤러 관련 빈 객체들이 로드됨.
 * @SpringBootTest 보다 가벼움.
 */
//@WebMvcTest(value = {ProductApiController.class})

/**
 * Api 에 SpringSecurity + jwt 토큰 인증방식이 적용되어있다면,  Security와 jwt토큰 관련 Bean 들도 모두 필요하기 때문에
 * @SpringBootTest + @AutoConfigureMockMvc 로 해야 정상적으로 테스트가 진행된다.
 */
@SpringBootTest
@AutoConfigureMockMvc //mockMvc 사용하기 위해 적용
@Transactional // 테스트 과정에서 DB입력, 수정내역 모두 rollback 처리
public class ProductApiControllerTest {

    //mockMvc : 서블릿컨테이너 구동없이 가상의 MVC환경에서 모의 http 서블릿을 요청하는 유틸클래스.
    @Autowired
    private MockMvc mockMvc;

    //MockBean = mock 객체를 스프링 컨테이너에 등록한 후 주입받는 방식. @WebMvcTest 시 사용한다.
    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    private HttpHeaders httpHeaders;

    private User user;

    @BeforeEach
    void init() {
        //테스트용 유저 생성후 저장
        user = User.builder()
                   .id(1L)
                   .email("test@naver.com")
                   .name("테스트")
                   .password("1234")
                   .roles(List.of(UserRole.ADMIN))
                   .build();
        userRepository.save(user);

        //테스트 유저 토큰 생성
        String token = jwtTokenProvider.createToken(user);

        //토큰 헤더에 저장
        httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
    }

    @Test
    @DisplayName("모든 상품을 조회 API 검증")
    void findAllProductTest() throws Exception {
        //given
        ProductResponse response = createProductResponse();

        given(productService.findAllProduct()).willReturn(List.of(response));

        //when & then
        mockMvc.perform(get("/product")
                       .headers(httpHeaders))  // mockMvc 요청시 토큰이 추가된 헤더를 적용해준다.
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].name").exists())
               .andExpect(jsonPath("$[0].price").exists())
               .andExpect(jsonPath("$[0].stock").exists())
               .andExpect(jsonPath("$[0].createdAt").exists())
               .andExpect(jsonPath("$[0].updatedAt").exists())
               .andDo(print());
    }

    @Test
    @DisplayName("단일 상품을 조회 API 검증")
    void findProductByIdTest() throws Exception {
        //given
        ProductResponse response = createProductResponse();

        Long id = 1L;

        given(productService.findProductById(id)).willReturn(response);

        //when & then
        mockMvc.perform(get("/product/{id}", id)
                       .headers(httpHeaders))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").exists())
               .andExpect(jsonPath("$.price").exists())
               .andExpect(jsonPath("$.stock").exists())
               .andExpect(jsonPath("$.createdAt").exists())
               .andExpect(jsonPath("$.updatedAt").exists())
               .andDo(print());
    }

    @Test
    @DisplayName("상품 등록 API 검증")
    void createProductTest() throws Exception {
        //given
        ProductRequest request = createProductRequest();
        ProductResponse response = createProductResponse();

        /**
         * POST, PUT, PATCH request 요청을 하여 .content(objectMapper.writeValueAsString(request))
         * 형태로 mockMvc 요청을 할 경우 given에서 any()를 사용해야 한다.  다른 것을 사용할 경우
         * mockMvc 의 response 가 NULL 로 온다. 이유는
         * .content(objectMapper.writeValueAsString(request)) 로 생성된 request 와
         * given에서 설장한 request를 서로 다른 객체로 인식하여 given().willReturn() 에 설정한 응답값이 오지 않는것으로 보인다.
         */
        given(productService.createProduct(any(), any())).willReturn(response);

        //request를 json 형태로 바꿔주는 방법1. objectMapper 사용
        String content = objectMapper.writeValueAsString(request);

        //request를 json 형태로 바꿔주는 방법2. Gson 사용
        //Gson gson = new Gson();
        //String content = gson.toJson(request);

        //when & then
        mockMvc.perform(post("/product")
                       .headers(httpHeaders)
                       .contentType(MediaType.APPLICATION_JSON) // POST, PUT, PATCH 요청 시 필요
                       .content(content)) // body에 request 데이터 입력
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.name").exists())
               .andExpect(jsonPath("$.price").exists())
               .andExpect(jsonPath("$.stock").exists())
               .andExpect(jsonPath("$.createdAt").exists())
               .andExpect(jsonPath("$.updatedAt").exists())
               .andDo(print());
    }

    @Test
    @DisplayName("상품 수정 API 검증")
    void updateProductTest() throws Exception {
        //given
        ProductRequest request = createProductRequest();
        given(productService.updateProduct(any(), any())).willReturn(true);

        String content = objectMapper.writeValueAsString(request);

        //when & then
        mockMvc.perform(put("/product/{id}", 1)
                       .headers(httpHeaders)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(content))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").value(true))
               .andDo(print());
    }

    @Test
    @DisplayName("상품 삭제 API 검증")
    void deleteProductTest() throws Exception {
        //given
        Long id = 1L;

        given(productService.deleteProduct(id)).willReturn(true);

        //when & then
        mockMvc.perform(delete("/product/{id}", id)
                       .headers(httpHeaders))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").value(true))
               .andDo(print());
    }

    private ProductResponse createProductResponse() {
        return ProductResponse.builder()
                              .id(1L)
                              .name("테스트")
                              .price(1500)
                              .stock(10)
                              .createdAt(LocalDateTime.now())
                              .updatedAt(LocalDateTime.now())
                              .build();
    }

    private ProductRequest createProductRequest() {
        return ProductRequest.builder()
                             .name("테스트")
                             .price(1500)
                             .stock(10)
                             .isActive(true)
                             .build();
    }
}
