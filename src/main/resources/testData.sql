insert into job
values (1, current_timestamp, current_timestamp, '개발');
insert into job
values (2, current_timestamp, current_timestamp, '경영');
insert into job
values (3, current_timestamp, current_timestamp, '기획');
insert into job
values (4, current_timestamp, current_timestamp, '디자인');
insert into job
values (5, current_timestamp, current_timestamp, '마케팅 / 홍보');
insert into job
values (6, current_timestamp, current_timestamp, '서무 / 서비스');
insert into job
values (7, current_timestamp, current_timestamp, '생산 / 기술');
insert into job
values (8, current_timestamp, current_timestamp, '영업');
insert into job
values (9, current_timestamp, current_timestamp, '인사 / 교육');
insert into job
values (10, current_timestamp, current_timestamp, '재무 / 회계');
insert into job
values (11, current_timestamp, current_timestamp, '총무');
insert into job
values (12, current_timestamp, current_timestamp, 'C/S');
insert into job
values (13, current_timestamp, current_timestamp, '기타');

insert into user_info
values (1, current_timestamp, current_timestamp, 0, concat('email', 1), 'YES','YES','YES',
        concat('name', 1),
        concat('nickName', 1),'Jjhg67XYWl1rwJ8QpW0VDg==',concat('phone', 1),
        'ACTIVE', 'MENTOR', 'NONE', 1);
insert into user_info
values (2, current_timestamp, current_timestamp, 0, concat('email', 2), 'YES','YES','YES',
        concat('name', 2),
        concat('nickName', 2),'Jjhg67XYWl1rwJ8QpW0VDg==', concat('phone', 2),
        'ACTIVE', 'MENTOR', 'NONE', 1);
insert into user_info
values (3, current_timestamp, current_timestamp, 0, concat('email', 3), 'YES','YES','YES',
        concat('name', 1),
        concat('nickName', 1),'Jjhg67XYWl1rwJ8QpW0VDg==', concat('phone', 1),
        'ACTIVE', 'MENTOR', 'NONE', 1);
insert into user_info
values (4, current_timestamp, current_timestamp, 0, concat('email', 4), 'YES','YES','YES',
        concat('name', 1),
        concat('nickName', 1),'Jjhg67XYWl1rwJ8QpW0VDg==', concat('phone', 1),
        'ACTIVE', 'MENTOR', 'NONE', 1);
insert into user_info
values (5, current_timestamp, current_timestamp, 0, concat('email', 5), 'YES','YES','YES',
        concat('name', 1),
        concat('nickName', 1),'Jjhg67XYWl1rwJ8QpW0VDg==', concat('phone', 1),
        'ACTIVE', 'MENTOR', 'NONE', 1);
insert into user_info
values (6, current_timestamp, current_timestamp, 0, concat('email', 6), 'YES','YES','YES',
        concat('name', 1),
        concat('nickName', 1),'Jjhg67XYWl1rwJ8QpW0VDg==', concat('phone', 1),
        'ACTIVE', 'MENTOR', 'NONE', 1);

insert into profile_emotion
values (1,current_timestamp,current_timestamp(),'relief','ACTIVE');

insert into profile_emotion
values (2,current_timestamp,current_timestamp(),'bigSmile','ACTIVE');

insert into profile_emotion
values (3,current_timestamp,current_timestamp(),'surprise','ACTIVE');

insert into profile_emotion
values (4,current_timestamp,current_timestamp(),'happy','ACTIVE');

insert into profile_emotion
values (5,current_timestamp,current_timestamp(),'smallSmile','ACTIVE');

insert into profile_emotion
values (6,current_timestamp,current_timestamp(),'wink','ACTIVE');

insert into profile_color
values (1, current_timestamp, current_timestamp,'purple','ACTIVE');

insert into profile_color
values (2, current_timestamp, current_timestamp,'lightPurple','ACTIVE');

insert into profile_color
values (3, current_timestamp, current_timestamp,'lightBlue','ACTIVE');

insert into profile_color
values (4, current_timestamp, current_timestamp,'blue','ACTIVE');

insert into user_profile
values (1,current_timestamp, current_timestamp,'ACTIVE',1,1);

insert into user_profile
values (2,current_timestamp, current_timestamp,'ACTIVE',1,1);

insert into user_profile
values (3,current_timestamp, current_timestamp,'ACTIVE',1,1);

insert into user_profile
values (4,current_timestamp, current_timestamp,'ACTIVE',1,1);

insert into user_profile
values (5,current_timestamp, current_timestamp,'ACTIVE',1,1);

insert into user_profile
values (6,current_timestamp, current_timestamp,'ACTIVE',1,1);

insert into cover_letter_category
values (1, current_timestamp, current_timestamp, '지원동기');
insert into cover_letter_category
values (2, current_timestamp, current_timestamp, '성장배경');
insert into cover_letter_category
values (3, current_timestamp, current_timestamp, '성격의 장단점');
insert into cover_letter_category
values (4, current_timestamp, current_timestamp, '직무 관련 경험');
insert into cover_letter_category
values (5, current_timestamp, current_timestamp, '도전, 성취, 실제극복 경험');
insert into cover_letter_category
values (6, current_timestamp, current_timestamp, '취미 / 특기');
insert into cover_letter_category
values (7, current_timestamp, current_timestamp, '기타');

insert into cover_letter
values (1, current_timestamp, current_timestamp, concat('content', 1), 1, 'ACTIVE', 'WRITING', 1, 1);
insert into cover_letter
values (2, current_timestamp, current_timestamp, concat('content', 2), 1, 'ACTIVE', 'WRITING', 1, 2);
insert into cover_letter
values (3, current_timestamp, current_timestamp, concat('content', 3), 1, 'ACTIVE', 'WRITING', 1, 3);
insert into cover_letter
values (4, current_timestamp, current_timestamp, concat('content', 4), 1, 'ACTIVE', 'WRITING', 1, 4);
insert into cover_letter
values (5, current_timestamp, current_timestamp, concat('content', 5), 1, 'ACTIVE', 'WRITING', 1, 5);
insert into cover_letter
values (6, current_timestamp, current_timestamp, concat('content', 6), 1, 'ACTIVE', 'WRITING', 1, 6);
insert into cover_letter
values (7, current_timestamp, current_timestamp, concat('content', 7), 1, 'ACTIVE', 'WRITING', 1, 1);
insert into cover_letter
values (8, current_timestamp, current_timestamp, concat('content', 8), 1, 'ACTIVE', 'WRITING', 1, 2);
insert into cover_letter
values (9, current_timestamp, current_timestamp, concat('content', 9), 1, 'ACTIVE', 'WRITING', 1, 3);
insert into cover_letter
values (10, current_timestamp, current_timestamp, concat('content', 10), 1, 'ACTIVE', 'WRITING', 1, 4);
insert into cover_letter
values (11, current_timestamp, current_timestamp, concat('content', 11), 1, 'ACTIVE', 'WRITING', 1, 5);
insert into cover_letter
values (12, current_timestamp, current_timestamp, concat('content', 12), 1, 'ACTIVE', 'WRITING', 1, 6);
insert into cover_letter
values (13, current_timestamp, current_timestamp, concat('content', 13), 1, 'ACTIVE', 'WRITING', 1, 1);
insert into cover_letter
values (14, current_timestamp, current_timestamp, concat('content', 14), 1, 'ACTIVE', 'WRITING', 1, 1);
insert into cover_letter
values (15, current_timestamp, current_timestamp, concat('content', 15), 1, 'ACTIVE', 'WRITING', 1, 1);
insert into cover_letter
values (16, current_timestamp, current_timestamp, concat('content', 16), 1, 'ACTIVE', 'WRITING', 1, 1);

insert into comment
values (1, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        1, 1);
insert into comment
values (2, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        2, 1);
insert into comment
values (3, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        3, 1);
insert into comment
values (4, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        4, 1);
insert into comment
values (5, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        5, 1);
insert into comment
values (6, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        6, 1);
insert into comment
values (7, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        7, 1);
insert into comment
values (8, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        8, 1);
insert into comment
values (9, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        9, 1);
insert into comment
values (10, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        10, 1);
insert into comment
values (11, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        11, 1);
insert into comment
values (12, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        12, 1);
insert into comment
values (13, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        1, 5);
insert into comment
values (14, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        1, 6);
insert into comment
values (15, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        1, 1);
insert into comment
values (16, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        1, 5);
insert into comment
values (17, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        1, 6);
insert into comment
values (18, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        1, 1);
insert into comment
values (19, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        1, 5);
insert into comment
values (20, current_timestamp, current_timestamp, 'NONE', 'NONE', concat('comment', 1), 'NO', 'NONE', 'NONE', 'ACTIVE',
        1, 6);

insert into sympathy values (1, 1, current_timestamp, current_timestamp, 'ACTIVE');
insert into sympathy values (1, 2, current_timestamp, current_timestamp, 'ACTIVE');
insert into sympathy values (3, 3, current_timestamp, current_timestamp, 'ACTIVE');
insert into sympathy values (3, 4, current_timestamp, current_timestamp, 'ACTIVE');
insert into sympathy values (3, 5, current_timestamp, current_timestamp, 'INACTIVE');
insert into sympathy values (6, 6, current_timestamp, current_timestamp, 'ACTIVE');
insert into sympathy values (6, 1, current_timestamp, current_timestamp, 'ACTIVE');
insert into sympathy values (6, 2, current_timestamp, current_timestamp, 'ACTIVE');

insert into change_role_category values (1,current_timestamp, current_timestamp,'퇴사 및 자소서 준비');
insert into change_role_category values (2,current_timestamp, current_timestamp,'멘티로 다시 활동하기 위해');
insert into change_role_category values (3,current_timestamp, current_timestamp,'멘티 활동에 대한 궁금중');
insert into change_role_category values (4,current_timestamp, current_timestamp,'멘토 활동에 대한 부담감');
insert into change_role_category values (5,current_timestamp, current_timestamp,'기타');
