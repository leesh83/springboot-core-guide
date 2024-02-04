package kevin.springboot.core.guide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kevin.springboot.core.guide.dto.ProductRequest;
import kevin.springboot.core.guide.dto.ProductResponse;
import kevin.springboot.core.guide.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest
@WebMvcTest(ProductApiController.class)
public class ProductApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("모든 상품을 조회 API 검증")
    void findAllProductTest() throws Exception {
        //given
        ProductResponse response = createProductResponse();

        given(productService.findAllProduct()).willReturn(List.of(response));

        //when & then
        mockMvc.perform(get("/product"))
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
        mockMvc.perform(get("/product/{id}", id))
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
        given(productService.createProduct(any())).willReturn(response);

        //when & then
        mockMvc.perform(post("/product")
                       .contentType(MediaType.APPLICATION_JSON) // POST, PUT, PATCH 요청 시 필요
                       .content(objectMapper.writeValueAsString(request))) // body에 request 데이터 입력
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

        //when & then
        mockMvc.perform(put("/product/{id}", 1)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(request)))
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
        mockMvc.perform(delete("/product/{id}", id))
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
                             .build();
    }
}
