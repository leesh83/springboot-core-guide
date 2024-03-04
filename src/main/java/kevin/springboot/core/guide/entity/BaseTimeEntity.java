package kevin.springboot.core.guide.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class) // JpaAuditing  쓰려면 추가해줘야함.
@MappedSuperclass //Jpa의 엔티티가 상속받을 경우 자식클래스에게 매핑정보를 전달함. 스스로 엔티티가 될수없음.
public class BaseTimeEntity {

    @CreatedDate //엔티티가 생성될때 생성 시간 저장
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate //엔티티가 수정될때 수정시간 저장
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
