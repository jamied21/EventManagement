------ Sample Dara Users
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'JohnDoe', 'Consultant');
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'AliceSmith', 'Trainer');
INSERT INTO Event_Users (id, username, role) VALUES (NEXTVAL('user_id_seq'), 'BobJohnson', 'Trainee');

-- Sample data for Events
INSERT INTO Events (id, name, Event_Date, location, FK_ORGANISER_ID) VALUES (1, 'Event1', '2024-02-14T12:00:00', 'Location1', 1); -- JohnDoe as organizer

INSERT INTO Events (id, name, Event_Date, location, FK_ORGANISER_ID) VALUES (2, 'Event2', '2024-02-15T14:30:00', 'Location2', 3); -- BobJohnson as organizer

INSERT INTO Events (id, name, Event_Date, location, FK_ORGANISER_ID) VALUES (3, 'Event3', '2024-02-16T10:00:00', 'Location3', 2); -- AliceSmith as organizer

-- Sample data for Register
INSERT INTO Register (id, attended, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, (SELECT id FROM Events WHERE name = 'Event1'), (SELECT id FROM Event_Users WHERE username = 'JohnDoe'));
INSERT INTO Register (id, attended, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), false, (SELECT id FROM Events WHERE name = 'Event2'), (SELECT id FROM Event_Users WHERE username = 'AliceSmith'));
INSERT INTO Register (id, attended, FK_EVENT_ID, FK_USER_ID) VALUES (NEXTVAL('register_id_seq'), true, (SELECT id FROM Events WHERE name = 'Event3'), (SELECT id FROM Event_Users WHERE username = 'BobJohnson'));

-- Sample data for Feedback
INSERT INTO Feedback (id, comment, rating, FK_REGISTER_ID) VALUES (NEXTVAL('Feedback_id_seq'), 'Good event!', 5, 1);
INSERT INTO Feedback (id, comment, rating, FK_REGISTER_ID) VALUES (NEXTVAL('Feedback_id_seq'), 'Could be better', 3, 2);
INSERT INTO Feedback (id, comment, rating, FK_REGISTER_ID) VALUES (NEXTVAL('Feedback_id_seq'), 'Excellent organization', 4, 3);

