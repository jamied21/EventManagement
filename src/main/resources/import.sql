INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'JohnDoe', 'Consultant');
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'AliceSmith', 'Trainer');
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'BobJohnson', 'Trainee');

INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Tech Conference', '2024-03-15T10:00:00', 'Conference Center A', 1);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Workshop on AI', '2024-04-20T14:30:00', 'Tech Hub B', 2);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Networking Mixer', '2024-05-10T18:00:00', 'Social Lounge', 3);

INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, '2024-03-15T09:30:00', '2024-03-15T10:00:00', 1, 1);
INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), false, '2024-04-20T14:00:00', NULL, 2, 2);
INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, '2024-05-10T17:30:00', '2024-05-10T18:05:00', 3, 3);

INSERT INTO Feedback (id, comment, rating, FK_REGISTER_ID) VALUES (NEXTVAL('feedback_id_seq'), 'Great event, learned a lot!', 5, 1);
INSERT INTO Feedback (id, comment, rating, FK_REGISTER_ID) VALUES (NEXTVAL('feedback_id_seq'), 'The workshop was informative.', 4, 2);
INSERT INTO Feedback (id, comment, rating, FK_REGISTER_ID) VALUES (NEXTVAL('feedback_id_seq'), 'Good networking opportunity.', 4, 3);
