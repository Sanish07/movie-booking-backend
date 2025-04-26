CREATE TABLE booking_events (
    id SERIAL PRIMARY KEY NOT NULL,
    booking_number VARCHAR(50) NOT NULL REFERENCES bookings(booking_number),
    event_id VARCHAR(100) NOT NULL UNIQUE,
    event_type VARCHAR(100) NOT NULL,
    payload TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);