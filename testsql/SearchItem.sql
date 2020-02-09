SELECT d.id, d.label, d.dosage_id, dos.name AS dosage,
d.volume, d.volume_type, d.manufacturer_id, m.name AS manufacturer_name, d.price, d.by_prescription, d.description
From drugs d
LEFT JOIN dosages dos ON d.dosage_id = dos.id
LEFT JOIN manufacturers m ON d.manufacturer_id = m.id
WHERE d.label LIKE 'Аспирин%'
ORDER BY dosage
LIMIT 0, 10;