package com.app.edit.domain.user;

import com.app.edit.config.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "userInfo")
public class UserInfo extends BaseEntity {

    /**
     * 유저 ID
     */
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 유저 이름
     */
    @Column(name = "name", nullable = false, columnDefinition = "varchar(10)")
    private String name;

    /**
     * 유저 닉네임
     */
    @Column(name = "nickName", nullable = false, columnDefinition = "varchar(20)")
    private String nickName;

    /**
     * 유저 이메일
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 유저 휴대폰 번호
     */
    @Column(name = "phoneNumber", nullable = false,columnDefinition = "varchar(100) default 'NONE'")
    private String phoneNumber;
    /**
     * 유저 역할
     */
    @Column(name = "role", nullable = false)
    private String role;

    /**
     * 멘토 인증 여부
     */
    @Column(name = "isCertificatedMentor", nullable = false)
    private String isCertificatedMentor;

}
