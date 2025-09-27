-- Connect as SYS in the PDB
-- This script creates the ECOM user with proper Oracle syntax

CREATE USER ECOM IDENTIFIED BY ECOMPass123
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP;