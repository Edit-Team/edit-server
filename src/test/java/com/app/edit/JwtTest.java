package com.app.edit;

import com.app.edit.config.BaseException;
import com.app.edit.config.secret.Secret;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.UserRole;
import com.app.edit.response.user.GetUserInfo;
import com.app.edit.utils.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.app.edit.config.BaseResponseStatus.EXPIRED_JWT;
import static com.app.edit.config.BaseResponseStatus.INVALID_JWT;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {

    @Autowired
    public JwtService jwtService;

    @Test
    public void jwt테스트(){
        UserInfo userInfo = UserInfo.builder()
                .id(1L)
                .email("test")
                .etcJobName("test")
                .isCertificatedMentor(null)
                .name("test")
                .coinCount(1L)
                .nickName("test")
                .phoneNumber("test")
                .userRole(UserRole.MENTEE)
                .state(null)
                .withdrawal("test")
                .build();
        String accessToken = jwtService.createJwt(userInfo.getId(),userInfo.getUserRole());
        System.out.println(accessToken);
        // 2. JWT parsing
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);

        } catch (ExpiredJwtException exception) {
            System.out.println("JWT가 만료되었습니다.");
        }catch (Exception e){
            System.out.println("유효하지 않은 JWT 입니다.");
        }


        // 3. userInfo 추출
        GetUserInfo getUserInfo = GetUserInfo.builder()
                .userId((long)claims.getBody().get("userId", Integer.class))
                .role(claims.getBody().get("role", String.class))
                .build();
        System.out.println(getUserInfo.getUserId() + " " + getUserInfo.getRole());
    }
}