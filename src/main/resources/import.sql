
/*INSERT INTO Roles (role_Id, authority) VALUES (NEXTVAL('role_id_seq'), 'ADMIN');*/

INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'John2001', 'Consultant');
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'Maisy_Peters', 'Trainer');
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'Bob99', 'Trainee');
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'EmilySmith21', 'Consultant');
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'AlexJohnson94', 'Trainer');
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'SarahDoe03', 'Trainee');
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'JakeBrown88', 'Consultant');
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'SophiaAdams12', 'Trainer');


INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Tech Conference', CURRENT_DATE + 3, 'Conference Center A', 1);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Data Science Symposium', CURRENT_DATE + 3, 'Convention Center B', 1);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Workshop on AI', CURRENT_DATE + 13, 'Tech Hub B', 1);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Networking Mixer', CURRENT_DATE + 9, 'Social Lounge', 2);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Web Development Workshop', CURRENT_DATE + 6, 'Tech Park Auditorium', 2);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Cybersecurity Conference', CURRENT_DATE + 11, 'Digital Security Institute', 3);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Entrepreneurship Summit', CURRENT_DATE + 4, 'Startup Hub', 4);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Mobile App Development Seminar', CURRENT_DATE + 0, 'Innovation Center', 5);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Marketing Analytics Workshop', CURRENT_DATE + 10, 'Marketing Institute', 5);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Cloud Computing Summit', CURRENT_DATE + 4, 'CloudTech Expo', 5);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Artificial Intelligence Symposium', '2025-01-20T12:00:00', 'AI Innovation Center', 6);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Finance and Technology Conference', '2025-02-08T10:45:00', 'Financial District', 7);
INSERT INTO Events (id, name, EVENT_DATE, location, FK_ORGANISER_ID) VALUES (NEXTVAL('event_id_seq'), 'Healthcare Innovation Summit', '2025-03-22T09:30:00', 'Medical Campus', 8);

INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, CURRENT_DATE + 12, CURRENT_DATE + 11, 1, 2);
INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, CURRENT_DATE + 5, CURRENT_DATE + 1, 1, 3);
INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, CURRENT_DATE + 13, CURRENT_DATE + 10, 1, 4);
INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, CURRENT_DATE + 5, CURRENT_DATE + 6, 1, 5);
INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, CURRENT_DATE + 12, CURRENT_DATE + 4, 2, 1);
INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, CURRENT_DATE + 6, CURRENT_DATE + 8, 2, 4);
INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, CURRENT_DATE + 12, CURRENT_DATE + 6, 2, 5);
INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, CURRENT_DATE + 5, CURRENT_DATE + 9, 2, 6);
INSERT INTO Register (id, attended, Regsitration_Time, Check_In_Time, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, CURRENT_DATE + 12, CURRENT_DATE + 10, 3, 1);

INSERT INTO Feedback (id, comment, rating, FK_REGISTER_ID) VALUES (NEXTVAL('feedback_id_seq'), 'Great event, learned a lot!', 5, 1);
INSERT INTO Feedback (id, comment, rating, FK_REGISTER_ID) VALUES (NEXTVAL('feedback_id_seq'), 'The workshop was informative.', 4, 2);
INSERT INTO Feedback (id, comment, rating, FK_REGISTER_ID) VALUES (NEXTVAL('feedback_id_seq'), 'Good networking opportunity.', 4, 3);

