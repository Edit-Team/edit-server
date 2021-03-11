package com.app.edit.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    // 1000 : 요청 성공
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    SUCCESS_READ_USERS(true, 1010, "회원 전체 정보 조회에 성공하였습니다."),
    SUCCESS_READ_USER(true, 1011, "회원 정보 조회에 성공하였습니다."),
    SUCCESS_POST_USER(true, 1012, "회원가입에 성공하였습니다."),
    SUCCESS_LOGIN(true, 1013, "로그인에 성공하였습니다."),
    SUCCESS_JWT(true, 1014, "JWT 검증에 성공하였습니다."),
    SUCCESS_DELETE_USER(true, 1015, "회원 탈퇴에 성공하였습니다."),
    SUCCESS_PATCH_USER(true, 1016, "회원정보 수정에 성공하였습니다."),
    SUCCESS_READ_SEARCH_USERS(true, 1017, "회원 검색 조회에 성공하였습니다."),

    // 2000 : Request 오류
    REQUEST_ERROR(false, 2000, "요청 바디의 입력값을 확인해주세요."),
    EMPTY_USERID(false, 2001, "유저 아이디 값을 확인해주세요."),
    EMPTY_JWT(false, 2010, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2011, "유효하지 않은 JWT입니다."),
    EMPTY_EMAIL(false, 2020, "이메일을 입력해주세요."),
    INVALID_EMAIL(false, 2021, "이메일 형식을 확인해주세요."),
    EMPTY_PASSWORD(false, 2030, "비밀번호를 입력해주세요."),
    EMPTY_CONFIRM_PASSWORD(false, 2031, "비밀번호 확인을 입력해주세요."),
    WRONG_PASSWORD(false, 2032, "비밀번호를 다시 입력해주세요."),
    DO_NOT_MATCH_PASSWORD(false, 2033, "비밀번호와 비밀번호확인 값이 일치하지 않습니다."),
    EMPTY_NICKNAME(false, 2040, "닉네임을 입력해주세요."),
    INVALID_PHONENUMBER(false, 2041, "전화번호 형식이 다릅니다."),
    EXPIRED_JWT(false, 2042, "토큰이 만료되었습니다."),
    EMPTY_RECEIVER(false, 2043, "이메일 수신자가 없습니다."),
    AUTHENTICATION_TIME_EXPIRED(false, 2044, "인증 시간이 만료되었습니다."),



    // colt
    REQUEST_PARAMETER_MISSING(false, 2050, "요청에 필수 파라미터가 누락되어 있습니다."),
    REQUEST_PARAMETER_MISMATCH(false, 2051, "요청 파라미터 타입이 맞지 않습니다."),
    COVER_LETTER_CONTENT_LENGTH_CAN_NOT_BE_GREATER_THAN_LENGTH_LIMIT(false, 2100, "자소서의 내용 길이는 90자를 초과할 수 없습니다."),

    // 3000 : Response 오류
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    NOT_FOUND_USER(false, 3010, "존재하지 않는 회원입니다."),
    DUPLICATED_USER(false, 3011, "이미 존재하는 회원입니다."),
    FAILED_TO_GET_USER(false, 3012, "회원 정보 조회에 실패하였습니다."),
    FAILED_TO_POST_USER(false, 3013, "회원가입에 실패하였습니다."),
    FAILED_TO_LOGIN(false, 3014, "로그인에 실패하였습니다."),
    FAILED_TO_DELETE_USER(false, 3015, "회원 탈퇴에 실패하였습니다."),
    FAILED_TO_PATCH_USER(false, 3016, "개인정보 수정에 실패하였습니다."),
    FAILED_TO_SEND_EMAIL(false, 3017, "이메일 전송에 실패했습니다."),
    FAILED_TO_AUTHENTICATION_CODE(false, 3018, "인증 번호 인증에 실패했습니다."),
    FAILED_TO_ENCRYPT_PASSWORD(false, 3019, "비밀 번호 암호화에 실패했습니다."),
    FAILED_TO_UPDATE_USER(false, 3020, "비밀번호 변경에 실패했습니다."),


    // colt
    NOT_FOUND_COVER_LETTER(false, 3100, "존재하지 않는 자소서입니다."),
    NOT_FOUND_USER_INFO(false, 3200, "존재하지 않는 유저입니다."),
    NOT_FOUND_COMMENT(false, 3300, "존재하지 않는 코멘트입니다."),
    NOT_FOUND_COVER_LETTER_CATEGORY(false, 3400, "존재하지 않는 자소서 종류입니다."),

    // 4000 : Database 오류
    SERVER_ERROR(false, 4000, "서버와의 통신에 실패하였습니다."),
    DATABASE_ERROR(false, 4001, "데이터베이스 연결에 실패하였습니다.");

    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
