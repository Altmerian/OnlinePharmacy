SELECT id, valid_until, drug_id, customer_id, doctor_id, status 
FROM prescriptions p 
WHERE p.status = 'requested' 
ORDER BY p.valid_until 
LIMIT ?, ?;