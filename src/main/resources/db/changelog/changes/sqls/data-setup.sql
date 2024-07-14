INSERT INTO conference_room (name, capacity, is_booked, created_at, updated_at, version) VALUES
('Amaze', 3, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('Beauty', 7, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('Inspire', 12, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('Strive', 20, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

INSERT INTO room_maintenance (start_time, end_time) VALUES
('09:00:00', '09:15:00'),
('13:00:00', '13:15:00'),
('17:00:00', '17:15:00');

INSERT INTO conference_room_maintenance (conference_room_id, room_maintenance_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 3),
(4, 1),
(4, 2),
(4, 3);