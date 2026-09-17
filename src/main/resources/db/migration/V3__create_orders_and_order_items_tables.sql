CREATE TABLE orders (
                        id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
                        order_number    VARCHAR(50)     NOT NULL UNIQUE,
                        customer_id     VARCHAR(255)    NOT NULL,
                        status          VARCHAR(50)     NOT NULL DEFAULT 'PENDING',
                        total_amount    DECIMAL(12, 2)  NOT NULL CHECK (total_amount >= 0),
                        created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items (
                             id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
                             order_id        UUID            NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                             product_id      UUID            NOT NULL,
                             product_name    VARCHAR(255)    NOT NULL,
                             sku             VARCHAR(100)    NOT NULL,
                             unit_price      DECIMAL(10, 2)  NOT NULL CHECK (unit_price >= 0),
                             quantity        INTEGER         NOT NULL CHECK (quantity > 0),
                             line_total      DECIMAL(12, 2)  NOT NULL CHECK (line_total >= 0)
);