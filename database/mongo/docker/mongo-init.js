// Switch to the test-db database
db = db.getSiblingDB('test-db');

// Create the test-user with readWrite permissions on test-db
db.createUser({
    user: 'test-user',
    pwd: 'test-password',
    roles: [
        {
            role: 'readWrite',
            db: 'test-db'
        }
    ]
});

// Optional: Create some initial collections or data
db.createCollection('users');