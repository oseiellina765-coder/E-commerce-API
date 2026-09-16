CREATE TABLE products (
                          id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
                          name            VARCHAR(255)    NOT NULL,
                          description     TEXT,
                          sku             VARCHAR(100)    NOT NULL UNIQUE,
                          price           DECIMAL(10, 2)  NOT NULL CHECK (price >= 0),
                          stock_quantity  INTEGER         NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
                          image_url       VARCHAR(500),
                          active          BOOLEAN         NOT NULL DEFAULT TRUE,
                          created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);