CREATE TABLE category (
	_id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	image_link VARCHAR(100),
	receipt_count INTEGER NOT NULL DEFAULT 0,
	priority INTEGER NOT NULL
);

CREATE TABLE receipt (
	_id INTEGER NOT NULL PRIMARY KEY,
	category_id INTEGER NOT NULL,
	name VARCHAR(255) NOT NULL,
	ingredients TEXT,
	receipt TEXT NOT NULL,
	image_link VARCHAR(100),
	views_count INTEGER NOT NULL DEFAULT 0
);
