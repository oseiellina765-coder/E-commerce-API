CREATE TABLE carts (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                       customer_id VARCHAR(255) NOT NULL UNIQUE, --One active cart per customer

                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cart_items (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                            cart_id UUID NOT NULL REFERENCES carts(id) ON DELETE CASCADE, --When a cart is deleted,its items too are deleted

                            product_id UUID NOT NULL REFERENCES products(id),

                            quantity INTEGER NOT NULL CHECK (quantity > 0),

                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            --Prevent the same product from appearing twice in one cart
                            CONSTRAINT uq_cart_product UNIQUE (cart_id, product_id)
);