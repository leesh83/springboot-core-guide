package kevin.springboot.core.guide.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // JpaAuditing  쓰려면 추가해줘야함.
@Getter
@MappedSuperclass //Jpa의 엔티티가 상속받을 경우 자식클래스에게 매핑정보를 전달함.
public class BaseTimeEntity {
    @Column(updatable = false)
    @CreatedDate //엔티티가 생성될때 생성 시간 저장 // @SpringBootApplication 파일에 @EnableJpaAuditing 선언필요
    private LocalDateTime createAt;

    @LastModifiedDate //엔티티가 수정될때 수정시간 저장
    private LocalDateTime updatedAt;
}
