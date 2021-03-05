package com.app.edit.domain.mentor;

import com.app.edit.config.BaseEntity;
import com.app.edit.config.BaseException;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.State;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.catalina.User;

import javax.persistence.*;
import java.io.Serializable;

@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Entity
@Table(name = "mentor_info")
public class MentorInfo extends BaseEntity{

    /**
     * 멘토 ID
     */
    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "userInfoId")
    private UserInfo userInfo;

    /**
     * 멘토 사진
     */
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    /**
     * 유저 정보 삭제시 삭제 여부
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "varchar(10) default 'ACTIVE'")
    private State state;


}
