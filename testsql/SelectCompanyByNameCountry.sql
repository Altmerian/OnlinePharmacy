SELECT m.id, m.name, c.id AS country_id, website FROM manufacturers m
JOIN countries c ON m.country_id = c.id
WHERE m.name = 'Белмедпрепараты' AND c.id = '1';