-- Delete dependent records first to respect FK constraints
DELETE FROM `Address`;

-- Then delete lookup/parent records
DELETE FROM `StateProvince`;
DELETE FROM `SalesTerritory`;
DELETE FROM `CountryRegion`;