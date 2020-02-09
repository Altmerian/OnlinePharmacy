SELECT DISTINCT d.id AS drug_id, d.label, dro.order_id FROM drugs_ordered dro
LEFT JOIN drugs d ON d.id = dro.drug_id
WHERE drug_id = 9 
ORDER BY drug_id;