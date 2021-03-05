package com.app.edit.domain.profileemotion;


import com.app.edit.config.BaseEntity;
import com.app.edit.enums.State;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Entity
@Table(name = "profile_emotion")
public class ProfileEmotion extends BaseEntity {

    /**
     * 프로필 캐릭터 표정 등록 번호
     */
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 프로필 캐릭터 색상 이미지
     */
    @Column(name = "name", columnDefinition = "TEXT")
    private String imageUrl;

    /**
     * 프로필 색깔 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "varchar(10) default 'ACTIVE'")
    private State state;
}