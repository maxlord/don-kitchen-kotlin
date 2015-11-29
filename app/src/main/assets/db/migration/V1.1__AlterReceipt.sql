ALTER TABLE receipt ADD COLUMN rating INTEGER;

UPDATE receipt SET rating = 0;
