
CREATE TABLE mythical_creature (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    number_of_legs INTEGER NOT NULL DEFAULT 0,
    has_hair BOOLEAN NOT NULL DEFAULT FALSE,
    taxonomy_tags JSONB
);

CREATE INDEX IF NOT EXISTS mythical_creature_name_idx ON mythical_creature (name);

COMMENT ON TABLE mythical_creature IS 'This example holds info on mythical creatures';