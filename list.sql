
SELECT edid
FROM  (
    SELECT DISTINCT ON (edid) *
    FROM fronta
    ORDER  BY edid,ts ASC 
    ) p
ORDER  BY ts ASC ; 