CREATE TABLE IF NOT EXISTS public.box(
	id SERIAL PRIMARY KEY NOT NULL,
	contained_in INT
);

CREATE TABLE IF NOT EXISTS public.item(
	id SERIAL PRIMARY KEY NOT NULL,
	color VARCHAR(100),
	contained_in INT REFERENCES box(id)
);

CREATE TABLE IF NOT EXISTS public.box_h(
	id SERIAL PRIMARY KEY NOT NULL,
	parent INT,
	child INT
);

CREATE UNIQUE INDEX item_id_index ON public.item(id);
CREATE UNIQUE INDEX box_id_index ON public.box(id);