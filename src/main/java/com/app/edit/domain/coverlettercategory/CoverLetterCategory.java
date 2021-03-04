package com.app.edit.domain.coverlettercategory;

import com.app.edit.config.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "COVER_LETTER_CATEGORY")
public class CoverLetterCategory extends BaseEntity {

    /*
     * 자소서 카테고리 ID 
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /*
     * 자소서 카테고리 이름
     **/
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Builder
    public CoverLetterCategory(String name) {
        this.name = name;
    }
}
