package com.app.edit.domain.userprofile;

import com.app.edit.config.BaseEntity;
import com.app.edit.domain.user.UserInfo;
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
@Table(name = "user_profile")
public class UserProfile extends BaseEntity {

    /**
     * 회원 프로필 번호
     */
    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "userInfoId")
    private UserInfo userInfo;

    /**
     * 회원 프로필 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "varchar(10) default 'ACTIVE'")
    private State state;
}
