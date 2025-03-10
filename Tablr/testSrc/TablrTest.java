import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TablrTest {

    private void resetState(){
        ArrayList<Table> tables = TableManager.getTables();
        for (Table table : new ArrayList<>(tables)) {
            table.select();
            TableManager.deleteTable();
        }
        ModeManager.toTablesMode();
        // Assert reset successful
        assert TableManager.getTables().isEmpty();
        assertInstanceOf(TablesMode.class, ModeManager.getMode());
    }

    // Test table creation
    @Test
    void tableManager() {
        TableManager.createAndAddTable();
        // Make sure table was created correctly
        assert TableManager.getTables().size() == 1;
        Table genTable = TableManager.getTables().get(0);
        assert genTable.getName().equals("Table1");

        // Select first table
        TableManager.getTableByIndex(0).select();
        // Make sure table was selected
        assertNotNull(TableManager.getSelectedTable());
        assert TableManager.getSelectedTable().equals(genTable);

        // Double click to create another table
        TableManager.createAndAddTable();
        assert TableManager.getTables().size() == 2;
        assert TableManager.getTableByIndex(1).getName().equals("Table2");

        // Select table 1 and change name to Table2
        TableManager.getTableByIndex(0).select();
        TableManager.editTableName('\b');
        TableManager.editTableName('2');

        // Try to select table 2 and fail
        TableManager.selectTable(1);
        assertEquals(TableManager.getSelectedTable(), TableManager.getTableByIndex(0));

        // Change table 1 name to Table3
        TableManager.editTableName('\b');
        TableManager.editTableName('3');

        // Autogen table 3 with name Table4 because Table3 is taken
        TableManager.createAndAddTable();
        assertEquals("Table4", TableManager.getTableByIndex(2).getName());

        // Change table 1 name to Table1
        TableManager.editTableName('\b');
        TableManager.editTableName('2');

        // table 1 is invalid so cannot unselect with enter or select other table
        TableManager.saveNewName();
        assertEquals("Table2", TableManager.getSelectedTable().getName());
        TableManager.selectTable(1);
        assertEquals(TableManager.getSelectedTable(), TableManager.getTableByIndex(0));

        // table 1 resets name because of esc
        TableManager.undoEditName();
        assertEquals("Table1", TableManager.getSelectedTable().getName());
        TableManager.unselectTable();

        // No table selected, so nothing deleted
        TableManager.deleteTable();
        assert TableManager.getTables().size() == 3;
        // Try deleting table 2
        Table table1 = TableManager.getTables().get(0);
        Table table2 = TableManager.getTables().get(1);
        Table table3 = TableManager.getTables().get(2);
        TableManager.selectTable(1);
        TableManager.deleteTable();
        // Make sure that table 2 was deleted and nothing else
        assert TableManager.getTables().size() == 2;
        assertEquals(table1, TableManager.getTableByIndex(0));
        assertEquals(table3, TableManager.getTableByIndex(1));
        assert !TableManager.getTables().contains(table2);

        // Reset state for other tests
        resetState();
    }

    @Test
    void columnManager() {
        // Create initial table to edit
        TableManager.createAndAddTable();
        TableManager.selectTable(0);
        Table table = TableManager.getSelectedTable();
        assert table != null;

        // Start designing table
        ModeManager.toTableDesignMode();

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

        // Reset state for other tests
        resetState();

    }
}