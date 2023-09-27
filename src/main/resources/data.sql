-- Insert sample users
INSERT INTO users (email, password, registered_at, invalidate_token_before, is_email_confirmed)
VALUES ('user1@example.com', '$2a$12$5TTdnuaWaeYf2NHthlFKSOz8ObtgMQWJ1uiqM2N2LlUcqVkcoXGM.', '2023-09-19 10:00:00', '2023-09-20 10:00:00', true),
       ('user2@example.com', '$2a$12$BwjgK1f6OG9PdY0rKP8K7OQqcbpD7PEYEIYBiVmLOLvlj.LDj8Aue', '2023-09-19 11:00:00', '2023-09-20 11:00:00', false),
       ('user3@example.com', '$2a$12$mLUDtoUVHVZ19IC7m.Q03eN/mFZXxOzcvfojqMTE7tpwNIgx5SKLu', '2023-09-19 12:00:00', '2023-09-20 12:00:00', true);

-- Insert sample user roles
INSERT INTO user_role (user_id, role)
VALUES (1, 'ADMIN'),
       (2, 'USER'),
       (3, 'USER');

-- Insert sample user profiles
INSERT INTO user_profile (id, first_name, last_name, sur_name, company_name, position, time_zone)
VALUES (1, 'John', 'Doe', 'Sr.', 'Acme Inc.', 'Software Engineer',  'America/New_York'),
       (2, 'Jane', 'Smith', NULL, 'Tech Corp.', 'Product Manager', 'Europe/London'),
       (3, 'Alice', 'Johnson', 'Jr.', 'Data Solutions', 'Data Analyst', 'Asia/Tokyo');

-- Insert sample calendars
INSERT INTO calendar (creator_id, title, color)
VALUES (1, 'Personal Calendar', 'BLUE'),
       (1, 'Work Calendar', 'GREEN'),
       (2, 'Team Calendar', 'RED');

-- Insert sample calendar events
INSERT INTO calendar_event (calendar_id, creator_id, title, description, time_from, time_to, notification_time, is_blocking)
VALUES (1, 1, 'Meeting', 'Discuss project updates', '2023-09-20 14:00:00', '2023-09-20 15:00:00', '2023-09-20 13:30:00', true),
       (2, 1, 'Conference Call', 'Client call', '2023-09-21 10:00:00', '2023-09-21 11:30:00', '2023-09-21 09:30:00', false),
       (3, 2, 'Team Meeting', 'Weekly team meeting', '2023-09-22 09:00:00', '2023-09-22 10:30:00', '2023-09-22 08:30:00', true);

-- Insert sample calendar users
INSERT INTO calendar_user (user_id, calendar_id, role, is_calendar_active)
VALUES (1, 1, 'CREATOR', true),
       (1, 2, 'CREATOR', false),
       (2, 3, 'USER', true),
       (3, 2, 'USER', true);

-- Insert sample attachments
INSERT INTO attachment (calendar_event_id, title, url)
VALUES (1, 'Agenda', 'https://example.com/agenda.pdf'),
       (2, 'Notes', 'https://example.com/notes.txt'),
       (3, 'Presentation', 'https://example.com/presentation.pptx');

-- Insert sample calendar event users
INSERT INTO calendar_event_user (user_id, calendar_event_id)
VALUES (1, 1),
       (2, 1),
       (1, 2),
       (3, 3);