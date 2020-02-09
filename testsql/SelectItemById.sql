SELECT d.id, d.label, d.dosage_id, dos.name AS dosage, d.volume, d.volume_type, d.manufacturer_id, m.name AS manufacturer_name, 
d.price, d.by_prescription, d.description FROM drugs d 
JOIN dosages dos ON d.dosage_id = dos.id
JOIN manufacturers m ON d.manufacturer_id = m.id 
WHERE d.id = 1;