

DO $$ DECLARE
  r RECORD;
BEGIN
  FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
    EXECUTE 'DROP TABLE ' || quote_ident(r.tablename) || ' CASCADE';
  END LOOP;
END $$;

CREATE TABLE davky(
   davkaid VARCHAR(255) NOT NULL,
   status VARCHAR(255) NOT NULL,
   PRIMARY KEY(davkaid)
);

CREATE TABLE fronta(
   no INT GENERATED ALWAYS AS IDENTITY,
   noderef VARCHAR(255) NOT NULL,
   edid VARCHAR(255) NOT NULL,
   davkaid VARCHAR(255) NOT NULL,
   status VARCHAR(255) NOT NULL,
   ts TIMESTAMP,
   PRIMARY KEY(no),

   CONSTRAINT nejakynazev
      FOREIGN KEY(davkaid) 
	  REFERENCES davky(davkaid)
	  ON DELETE CASCADE
);

/*

INSERT INTO davky(davkaid, status)
VALUES('davkaid1','NEW'),
      ('davkaid2','NEW');	   

SELECT * FROM davky ;

INSERT INTO fronta(noderef, edid, davkaid, status, ts)
VALUES('noderef0','edid0','davkaid1','NPROGRESS','2016-06-22 19:10:25-07'), 
      ('noderef1','edid1','davkaid1','WAITING','2016-06-22 19:10:22-07'), 
      ('noderef1','edid1','davkaid1','WAITING','2016-06-22 19:10:12-07'), 
      ('noderef2','edid3','davkaid2','WAITING','2016-06-22 19:10:24-07');
      
      
SELECT * FROM fronta ;

DELETE FROM davky WHERE davkaid = 'davkaid1' ;

SELECT * FROM fronta ;

*/

/*
SELECT * FROM fronta WHERE edid = edid1
*/

/*
SELECT * FROM fronta WHERE edid IN (SELECT edid FROM fronta ORDER BY ts ASC LIMIT 1) ORDER BY ts ASC;
*/