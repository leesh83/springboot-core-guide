package kevin.springboot.core.guide.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 상속받을 엔티티에서 등록일자,수정일자만 필요하다면 BaseTimeEntity를, 등록자,수정자도 필요하다면 BaseEntity 를 상속받는다.
 */
@Getter
@EntityListeners(AuditingEntityListener.class) // JpaAuditing  쓰려면 추가해줘야함.
@MappedSuperclass //Jpa의 엔티티가 상속받을 경우 자식클래스에게 매핑정보를 전달함. 스스로 엔티티가 될수없음.
public class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(updatable = false, nullable = false)
    private Long createdUserId;

    @LastModifiedBy
    @Column(nullable = false)
    private Long updatedUserId;
}
