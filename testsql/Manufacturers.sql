SELECT m.id, m.name, c.name FROM manufacturers m
JOIN countries c ON m.country_id = c.id;