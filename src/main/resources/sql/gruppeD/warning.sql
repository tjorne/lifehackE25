/*

    Clears tables and info about them in the
    database you're in.

    Only use if you know what you do.

*/

/* WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING */

DO $$ DECLARE
r RECORD;
BEGIN
FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public') LOOP
        EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';
END LOOP;
END $$;

/* WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING */