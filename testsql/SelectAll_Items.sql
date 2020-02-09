SELECT drugs.id, drugs.label, dosages.name AS dosage, drugs.volume, drugs.volume_type, 
manufacturers.name AS manufacturer, drugs.by_prescription, drugs.description
FROM drugs
JOIN manufacturers ON drugs.manufacturer_id = manufacturers.id
JOIN dosages ON drugs.dosage_id = dosages.id;