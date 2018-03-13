insert into users values (users_seq.nextval, 'user1','$2a$11$4Ttz61zmXVMhKD77.tvRsu6d0EvunMVam8da/o3We/p7E6eG34d.u', CURRENT_DATE); // password1
insert into users values (users_seq.nextval, 'user2','$2a$11$rswUrzX32wkUIdouxS98Neq5Dt..6t14nuCXAP3RYKsEBalzthE72', CURRENT_DATE); // password2 etc ..
insert into users values (users_seq.nextval, 'user3','$2a$11$/JBVUb/kRleB8Pkb0X/DuOcUWVgBfjT8SRHPtqddkI8QyYWbg1BwK', CURRENT_DATE);
insert into users values (users_seq.nextval, 'user4','$2a$11$PHdBasedWvuIMFSlNEwk6OU92lWfiG/64SWz2vMLKZr76oIYH9Ke6', CURRENT_DATE);

insert into message VALUES (message_seq.nextval, 'First message text First message text First message text First message text First message text First message text', 1);
insert into message VALUES (message_seq.nextval, 'Second message text Second message text Second message text Second message text Second message text Second message text', 2);
insert into message VALUES (message_seq.nextval, 'Third message text Third message text Third message text Third message text Third message text Third message text', 3);
insert into message VALUES (message_seq.nextval, 'Fourth message text Fourth message text Fourth message text Fourth message text Fourth message text Fourth message text', 4);

insert into allowed_messages VALUES (1, 2);
insert into allowed_messages VALUES (1, 3);
insert into allowed_messages VALUES (1, 4);
insert into allowed_messages VALUES (2, 1);