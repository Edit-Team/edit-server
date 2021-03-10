package com.app.edit.request.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentDeclarationReq {

    @NotNull
    private Long commentId;
}
