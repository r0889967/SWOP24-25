import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * General input tests for Tablr
 */
class TablrTest {
    private void resetState(){
        SubWindowManager.toTablesWindow(new TableManager());
        // Assert reset successful
        assertInstanceOf(TablesWindow.class, SubWindowManager.getWindow());
    }

    // Test table management
    @Test
    void tableManager() {
        TableManager tableManager = new TableManager();
        tableManager.createAndAddTable();
        // Make sure table was created correctly
        assert tableManager.getTables().size() == 1;
        Table genTable = tableManager.getTables().get(0);
        assert genTable.getName().equals("Table1");

        // Select first table
        tableManager.selectTable(0);
        // Make sure table was selected
        assertNotNull(tableManager.getSelectedTable());
        assert tableManager.getSelectedTable().equals(genTable);

        // Double click to create another table
        tableManager.createAndAddTable();
        assert tableManager.getTables().size() == 2;
        assert tableManager.getTableByIndex(1).getName().equals("Table2");

        // Select table 1 and change name to Table2
        tableManager.selectTable(0);
        tableManager.editTableName('\b');
        tableManager.editTableName('2');

        // Try to select table 2 and fail
        tableManager.selectTable(1);
        assertEquals(tableManager.getSelectedTable(), tableManager.getTableByIndex(0));

        // Change table 1 name to Table3
        tableManager.editTableName('\b');
        tableManager.editTableName('3');

        // Autogen table 3 with name Table4 because Table3 is taken
        tableManager.createAndAddTable();
        assertEquals("Table4", tableManager.getTableByIndex(2).getName());

        // Change table 1 name to Table1
        tableManager.editTableName('\b');
        tableManager.editTableName('2');

        // table 1 is invalid so cannot unselect with enter or select other table
        tableManager.saveNewName();
        assertEquals("Table2", tableManager.getSelectedTable().getName());
        tableManager.selectTable(1);
        assertEquals(tableManager.getSelectedTable(), tableManager.getTableByIndex(0));

        // table 1 resets name because of esc
        tableManager.undoNameEdits();
        assertEquals("Table1", tableManager.getSelectedTable().getName());
        tableManager.unselectTable();

        // No table selected, so nothing deleted
        tableManager.deleteTable();
        assert tableManager.getTables().size() == 3;
        // Try deleting table 2
        Table table1 = tableManager.getTables().get(0);
        Table table2 = tableManager.getTables().get(1);
        Table table3 = tableManager.getTables().get(2);
        tableManager.selectTable(1);
        tableManager.deleteTable();
        // Make sure that table 2 was deleted and nothing else
        assert tableManager.getTables().size() == 2;
        assertEquals(table1, tableManager.getTableByIndex(0));
        assertEquals(table3, tableManager.getTableByIndex(1));
        assert !tableManager.getTables().contains(table2);

        // Reset state for other tests
        resetState();
    }

    // Test column management
    @Test
    void columnManager() {
        TableManager tableManager = new TableManager();
        // Create initial table to edit
        tableManager.createAndAddTable();
        tableManager.selectTable(0);
        Table table = tableManager.getSelectedTable();
        assert table != null;

        // Start designing table
        SubWindowManager.toTableDesignWindow(tableManager);

        // Try adding a column and selecting it
        table.addCol();
        assert table.getCols().size() == 1;
        assert table.getCols().get(0).getName().equals("Column1");
        table.selectCol(0);
        assertEquals(table.getCols().get(0), table.getSelectedCol());

        // Try adding another column
        table.addCol();
        assert table.getCols().size() == 2;
        assert table.getCols().get(1).getName().equals("Column2");

        // Column 1 is still selected
        assertEquals(table.getCols().get(0), table.getSelectedCol());

        // Try editing column 1
        int const_name = 0;
        int const_type = 1;
        int const_allow_blanks = 2;
        int const_default_val = 3;
        table.setColumnEditMode(const_name);
        table.editColAttributes('\b');
        table.editColAttributes('3');
        assertEquals("Column3", table.getCols().get(0).getName());

        // Generate column3 with name Column4 (because 3 is taken)
        table.addCol();
        assert table.getCols().size() == 3;
        assertEquals(("Column4"), table.getCols().get(2).getName());

        // Try editing column 1
        table.setColumnEditMode(const_name);
        table.editColAttributes('\b');
        table.editColAttributes('2');
        assertEquals("Column2", table.getCols().get(0).getName());

        // Column 1 is now invalid, try unselecting or selecting other column
        table.selectCol(1);
        assertEquals(table.getCols().get(0), table.getSelectedCol());
        table.unselectCol();
        assertEquals(table.getCols().get(0), table.getSelectedCol());

        // Revert column 1 to basic name
        table.editColAttributes('\b');
        table.editColAttributes('1');

        //Try switching types
        Column selCol = table.getSelectedCol();
        assert selCol != null;
        table.setColumnEditMode(const_type);
        assertEquals("String", selCol.getType());
        assertEquals("", selCol.getDefaultValue());
        table.editColAttributes('\0');
        assertEquals("Email", selCol.getType());
        assertEquals("", selCol.getDefaultValue());
        table.editColAttributes('\0');
        assertEquals("Boolean", selCol.getType());
        assertEquals("True", selCol.getDefaultValue());
        table.editColAttributes('\0');
        assertEquals("Integer", selCol.getType());
        assertEquals("0", selCol.getDefaultValue());
        table.editColAttributes('\0');
        assertEquals("String", selCol.getType());
        assertEquals("", selCol.getDefaultValue());

        //Try editing allow blanks
        table.setColumnEditMode(const_allow_blanks);
        assert selCol.allowsBlanks();
        table.editColAttributes('\0');
        assert !selCol.allowsBlanks();
        table.editColAttributes('\0');
        assert selCol.allowsBlanks();

        //Try changing default value
        //Type string
        table.setColumnEditMode(const_default_val);
        table.editColAttributes('a');
        table.editColAttributes('b');
        table.editColAttributes('\b');
        table.editColAttributes('c');
        assertEquals("ac", selCol.getDefaultValue());

        //Type email
        table.setColumnEditMode(const_type);
        table.editColAttributes('\0');
        //default value is unchanged but invalid now
        assertEquals("Email", selCol.getType());
        assertEquals("ac", selCol.getDefaultValue());
        //so this fails
        table.selectCol(1);
        assertEquals(table.getCols().get(0), table.getSelectedCol());

        //Type boolean
        table.setColumnEditMode(const_type);
        table.editColAttributes('\0');
        assertEquals("Boolean", selCol.getType());
        assertEquals("True", selCol.getDefaultValue());
        table.setColumnEditMode(const_default_val);
        table.editColAttributes('\0');
        assertEquals("False", selCol.getDefaultValue());
        table.editColAttributes('\0');
        assertEquals("", selCol.getDefaultValue());
        //Now with allow blanks disabled
        table.setColumnEditMode(const_allow_blanks);
        table.editColAttributes('\0');
        //Default is no longer blank but True instead
        table.setColumnEditMode(const_default_val);
        assertEquals("True", selCol.getDefaultValue());
        table.editColAttributes('\0');
        assertEquals("False", selCol.getDefaultValue());
        table.editColAttributes('\0');
        assertEquals("True", selCol.getDefaultValue());
        //Allow blanks again
        table.setColumnEditMode(const_allow_blanks);
        table.editColAttributes('\0');
        assert selCol.allowsBlanks();

        //Integer
        table.setColumnEditMode(const_type);
        table.editColAttributes('\0');
        assertEquals("Integer", selCol.getType());
        assertEquals("0", selCol.getDefaultValue());
        table.setColumnEditMode(const_default_val);
        //Reject non numbers
        table.editColAttributes('a');
        assertEquals("0",selCol.getDefaultValue());
        //Try switching allow blanks off when default value is blank
        table.editColAttributes('\b');
        table.setColumnEditMode(const_allow_blanks);
        table.editColAttributes('\0');
        //Default value should switch back to 0
        assertEquals("0",selCol.getDefaultValue());
        //Try switching columns with invalid default value, should not work
        table.setColumnEditMode(const_default_val);
        table.editColAttributes('\b');
        table.selectCol(1);
        assertEquals(table.getCols().get(0), table.getSelectedCol());
        //Switch back to default string
        table.setColumnEditMode(const_type);
        table.editColAttributes('\0');
        table.setColumnEditMode(const_allow_blanks);
        table.editColAttributes('\0');
        assertEquals("String", selCol.getType());
        assert selCol.allowsBlanks();
        assertEquals("", selCol.getDefaultValue());
        //Now we can switch
        table.selectCol(1);
        assertEquals(table.getCols().get(1), table.getSelectedCol());

        //Prep rows for next step
        table.addRow();
        table.addRow();
        assert table.getRows().size() == 2;
        for (Row row : table.getRows()) {
            assert row.getCells().size() == 3;
        }

        //Try deleting column 2
        ArrayList<Column> cols = table.getCols();
        Column col1 = cols.get(0);
        Column col2 = cols.get(1);
        Column col3 = cols.get(2);
        table.deleteCol();
        //Make sure column 2, and only column 2 was deleted
        assert table.getCols().size() == 2;
        assertEquals(table.getCols().get(0), col1);
        assertEquals(table.getCols().get(1), col3);
        assert !table.getCols().contains(col2);
        //Make sure cells from column 2 were wiped
        for (Row row : table.getRows()) {
            assert row.getCells().size() == 2;
        }

        // Reset state for other tests
        resetState();
    }

    // Test row management
    @Test
    void rowManager(){
        TableManager tableManager = new TableManager();
        // Edit constants
        int const_type = 1;
        int const_allow_blanks = 2;
        int const_default_val = 3;
        // Create initial table to edit
        tableManager.createAndAddTable();
        tableManager.selectTable(0);
        Table table = tableManager.getSelectedTable();
        SubWindowManager.toTableDesignWindow(tableManager);
        assert table != null;
        // Create a column for each type
        table.addCol();
        table.addCol();
        table.addCol();
        table.addCol();
        // Column 1
        table.selectCol(0);
        Column selCol = table.getSelectedCol();
        assert selCol != null;
        table.setColumnEditMode(const_allow_blanks);
        table.editColAttributes('\0');
        table.setColumnEditMode(const_default_val);
        table.editColAttributes('a');
        table.editColAttributes('b');
        table.editColAttributes('c');
        // Column 2
        table.selectCol(1);
        assertEquals(table.getCols().get(1), table.getSelectedCol());
        selCol = table.getSelectedCol();
        assert selCol != null;
        table.setColumnEditMode(const_type);
        table.editColAttributes('\0');
        table.setColumnEditMode(const_allow_blanks);
        table.editColAttributes('\0');
        table.setColumnEditMode(const_default_val);
        String defEmail = "abc@gmail.com";
        for (char keyChar : defEmail.toCharArray()){
            table.editColAttributes(keyChar);
        }
        // Column 3
        table.selectCol(2);
        assertEquals(table.getCols().get(2), table.getSelectedCol());
        selCol = table.getSelectedCol();
        assert selCol != null;
        table.setColumnEditMode(const_type);
        table.editColAttributes('\0');
        table.editColAttributes('\0');
        table.setColumnEditMode(const_allow_blanks);
        table.editColAttributes('\0');
        table.setColumnEditMode(const_default_val);
        table.editColAttributes('\0');
        // Column 4
        table.selectCol(3);
        assertEquals(table.getCols().get(3), table.getSelectedCol());
        selCol = table.getSelectedCol();
        assert selCol != null;
        table.setColumnEditMode(const_type);
        table.editColAttributes('\0');
        table.editColAttributes('\0');
        table.editColAttributes('\0');
        table.setColumnEditMode(const_allow_blanks);
        table.editColAttributes('\0');
        table.setColumnEditMode(const_default_val);
        String defInteger = "\b123";
        for (char keyChar : defInteger.toCharArray()){
            table.editColAttributes(keyChar);
        }
        //Select first column 1 again
        table.selectCol(0);
        assertEquals(table.getCols().get(0), table.getSelectedCol());

        //Try adding rows
        SubWindowManager.toTableRowsWindow(tableManager);
        table.addRow();
        //Ensure that row was added properly and default values are correctly filled
        assert table.getRows().size() == 1;
        Row row = table.getRows().get(0);
        assertEquals("abc", row.getCells().get(0).getValue());
        assertEquals("abc@gmail.com", row.getCells().get(1).getValue());
        assertEquals("False", row.getCells().get(2).getValue());
        assertEquals("123", row.getCells().get(3).getValue());
        //Try editing rows
        table.addRow();
        table.selectRow(1);
        table.selectCell(1,0);
        assertEquals(table.getSelectedRow(), table.getRows().get(1));
        assertEquals(table.getSelectedCell(), table.getRows().get(1).getCells().get(0));
        String cellVal = "\b\b\b";
        for (char keyChar : cellVal.toCharArray()) {
            table.editCellValue(keyChar);
        }
        //Select another cell, cell 1,0 is invalid now but select should go through
        table.unSelectCell();
        table.unSelectRow();
        table.selectRow(1);
        table.selectCell(1, 2);
        assertEquals(table.getSelectedRow(), table.getRows().get(1));
        assertEquals(table.getSelectedCell(), table.getRows().get(1).getCells().get(2));
        //But exiting row mode is not possible because columns are invalid
        assert !table.allValidColumns();

        //Restore cell 1,0 to valid state
        table.unSelectCell();
        table.unSelectRow();
        table.selectRow(1);
        table.selectCell(1, 0);
        assertEquals(table.getSelectedRow(), table.getRows().get(1));
        assertEquals(table.getSelectedCell(), table.getRows().get(1).getCells().get(0));
        cellVal = "def";
        for (char keyChar : cellVal.toCharArray()) {
            table.editCellValue(keyChar);
        }
        assertEquals("def", table.getRows().get(1).getCells().get(0).getValue());
        //Now we can exit if we wish
        assert table.allValidColumns();

        // Reset state for other tests
        resetState();
    }
}