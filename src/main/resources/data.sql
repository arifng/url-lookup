
DROP TABLE IF EXISTS mapped_urls;

CREATE TABLE mapped_urls (
  id bigint(20) AUTO_INCREMENT PRIMARY KEY,
  version bigint(20) DEFAULT NULL,
  parameterized_url VARCHAR(2048) NOT NULL,
  pretty_url VARCHAR(2048) NOT NULL
);


INSERT INTO mapped_urls
	(version, parameterized_url, pretty_url)
VALUES
	(1, '/products', '/Fashion/'),
    (1, '/products?gender=female', '/Women/'),
    (1, '/products?tag=5678', '/Boat-Shoes/'),
    (1, '/products?gender=female&tag=123&tag=1234', '/Women/Shoes/'),
    (1, '/products?brand=123', '/Adidas/');