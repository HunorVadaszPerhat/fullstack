-- Insert required CountryRegion (foreign key for StateProvince)
INSERT INTO CountryRegion (CountryRegionCode, Name, ModifiedDate)
VALUES ('US', 'United States', NOW());

-- Insert required SalesTerritory (foreign key for StateProvince)
INSERT INTO SalesTerritory (
    TerritoryID, Name, CountryRegionCode, Group_, SalesYTD, SalesLastYear,
    CostYTD, CostLastYear, rowguid, ModifiedDate
) VALUES (
             1, 'North America', 'US', 'NA', 0.0, 0.0, 0.0, 0.0, UUID(), NOW()
         );

-- Insert StateProvince for test
INSERT INTO StateProvince (
    StateProvinceID,
    StateProvinceCode,
    CountryRegionCode,
    IsOnlyStateProvinceFlag,
    Name,
    TerritoryID,
    rowguid,
    ModifiedDate
)
VALUES (
           1, 'TS', 'US', true, 'Test Province', 1, UUID(), NOW()
       );
