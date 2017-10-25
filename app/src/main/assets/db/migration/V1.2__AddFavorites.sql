CREATE TABLE favorite (
	_id INTEGER NOT NULL PRIMARY KEY,
	receipt_id INTEGER NOT NULL,
	date INTEGER NOT NULL
);

CREATE INDEX idx_favorite_receipt ON favorite(receipt_id);

CREATE INDEX idx_favorite_date ON favorite(date);
