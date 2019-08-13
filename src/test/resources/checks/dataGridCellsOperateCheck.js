datagrid.cells(r,1); // NOK
datagrid.cells(r,2); // NOK
datagrid.cells(r,3); // NOK  

datagrid.cells(r,datagrid.getColIndexById("QUERY_BANK_CODE")); // OK