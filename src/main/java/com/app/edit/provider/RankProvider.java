package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.domain.user.UserInfoRepository;
import com.app.edit.enums.CoverLetterType;
import com.app.edit.enums.IsAdopted;
import com.app.edit.enums.State;
import com.app.edit.enums.UserRole;
import com.app.edit.response.user.GetRankRes;
import com.app.edit.response.user.GetUserRankRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Transactional(readOnly = true)
@Service
@Slf4j
public class RankProvider {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public RankProvider(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    /**
     * 멘토 / 멘티 랭킹 조회
     * @param requestRole
     * @return
     */
    public GetRankRes getRank(String requestRole) throws BaseException {

        Pageable pageRequest = PageRequest.of(0,100);

        List<UserInfo> userInfo;

        if(requestRole.equals("MENTOR")) {

            userInfo = userInfoRepository
                    .findByAdoptAndState(pageRequest, UserRole.MENTOR, State.ACTIVE, IsAdopted.YES);

        }else if(requestRole.equals("MENTEE")){

            userInfo = userInfoRepository
                    .findByCoverLetterAndState(pageRequest, UserRole.MENTEE, State.ACTIVE, CoverLetterType.COMPLETING );
        }else {
            throw new BaseException(BaseResponseStatus.FAILED_TO_GET_ROLE);
        }

        //userInfo.forEach(userInfo1 -> System.out.println(userInfo1.getId()));

        if(userInfo.size() == 0)
            throw new BaseException(BaseResponseStatus.FAILED_TO_GET_USER);

        AtomicLong count = new AtomicLong(1);

        return GetRankRes.builder()
                .getUserRankResList(userInfo.stream()
                        .map(userInfo1 -> GetUserRankRes.builder()
                                .rankId(count.getAndIncrement())
                                .colorName(userInfo1.getUserProfile().getProfileColor().getName())
                                .emotionName(userInfo1.getUserProfile().getProfileEmotion().getName())
                                .name(userInfo1.getName())
                                .job(userInfo1.getJob().getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}