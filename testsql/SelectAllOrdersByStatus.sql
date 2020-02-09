SELECT o.id, o.customer_id, u.first_name, u.last_name, u.login, u.address, o.date, o.amount, o.status 
FROM orders o 
JOIN  users u ON o.customer_id = u.id 
WHERE o.status IN(?, ?, ?, ?) 
ORDER BY o.date DESC 
LIMIT ?, ?;