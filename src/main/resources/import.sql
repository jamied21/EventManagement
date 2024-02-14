---- Insert Owners
INSERT INTO OWNERS (id, OWNER_NAME) VALUES (NEXTVAL('owner_id_seq'), 'John Doe');
INSERT INTO OWNERS (id, OWNER_NAME) VALUES (NEXTVAL('owner_id_seq'), 'Jane Smith');

---- Insert Participants
INSERT INTO PARTICIPANTS (id, USER_NAME) VALUES  (NEXTVAL('participants_id_seq'), 'Alice Johnson');
INSERT INTO PARTICIPANTS (id, USER_NAME) VALUES  (NEXTVAL('participants_id_seq'), 'Bob Williams');
INSERT INTO PARTICIPANTS (id, USER_NAME) VALUES  (NEXTVAL('participants_id_seq'), 'Charlie Brown');
INSERT INTO PARTICIPANTS (id, USER_NAME) VALUES  (NEXTVAL('participants_id_seq'), 'Diana Miller');
INSERT INTO PARTICIPANTS (id, USER_NAME) VALUES  (NEXTVAL('participants_id_seq'), 'Eddie Johnson');

---- Insert Events
INSERT INTO EVENTS (id, name, EVENT_DATE, location, FK_OWNER_ID, FK_PARTICIPANT_ID) VALUES (NEXTVAL('event_id_seq'), 'Birthday Bash', '2024-03-15 18:00:00', '123 Main St', 1, 1);
INSERT INTO EVENTS (id, name, EVENT_DATE, location, FK_OWNER_ID, FK_PARTICIPANT_ID) VALUES (NEXTVAL('event_id_seq'), 'Conference', '2024-04-20 09:30:00', '456 Center Ave', 2, 2);
INSERT INTO EVENTS (id, name, EVENT_DATE, location, FK_OWNER_ID, FK_PARTICIPANT_ID) VALUES (NEXTVAL('event_id_seq'), 'Music Concert', '2024-05-10 20:00:00', '789 Park Lane', 1, 3);
INSERT INTO EVENTS (id, name, EVENT_DATE, location, FK_OWNER_ID, FK_PARTICIPANT_ID) VALUES (NEXTVAL('event_id_seq'), 'Tech Workshop', '2024-06-15 10:00:00', '101 Innovation Blvd', 2, 4);
INSERT INTO EVENTS (id, name, EVENT_DATE, location, FK_OWNER_ID, FK_PARTICIPANT_ID) VALUES (NEXTVAL('event_id_seq'), 'Food Festival', '2024-07-20 12:30:00', '202 Culinary Street', 1, 5);

---- Insert Feedback
INSERT INTO FEEDBACK (id, comment, rating, FK_EVENT_ID, FK_PARTICIPANT_ID) VALUES (NEXTVAL('event_id_seq'), 'Enjoyed the event', 5, 1, 1);
INSERT INTO FEEDBACK (id, comment, rating, FK_EVENT_ID, FK_PARTICIPANT_ID) VALUES (NEXTVAL('event_id_seq'), 'Room for improvement', 3, 2, 2);
INSERT INTO FEEDBACK (id, comment, rating, FK_EVENT_ID, FK_PARTICIPANT_ID) VALUES (NEXTVAL('event_id_seq'), 'Fantastic music performance!', 4, 3, 3);
INSERT INTO FEEDBACK (id, comment, rating, FK_EVENT_ID, FK_PARTICIPANT_ID) VALUES (NEXTVAL('event_id_seq'), 'Learned a lot about new technologies', 5, 4, 4);
INSERT INTO FEEDBACK (id, comment, rating, FK_EVENT_ID, FK_PARTICIPANT_ID) VALUES (NEXTVAL('event_id_seq'), 'Amazing variety of food options', 4, 5, 5);

---- Insert Registers
INSERT INTO REGISTER (id, attended, FK_PARTICIPANT_ID, FK_EVENT_ID) VALUES (NEXTVAL('register_id_seq'), true, 3, 3);
INSERT INTO REGISTER (id, attended, FK_PARTICIPANT_ID, FK_EVENT_ID) VALUES (NEXTVAL('register_id_seq'), true, 4, 4);
INSERT INTO REGISTER (id, attended, FK_PARTICIPANT_ID, FK_EVENT_ID) VALUES (NEXTVAL('register_id_seq'), false, 5, 5);
