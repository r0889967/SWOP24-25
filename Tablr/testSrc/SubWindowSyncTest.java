import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SubWindowSyncTest {
    //Always succeeds, check for print statements
    @Test
    public void subWindowSyncTest() {
        TableManager tableManager = new TableManager();
        ArrayList<SubWindow> windows = new ArrayList<>();

        //Open table window, add and select 1 table
        windows.add(new TablesWindow(tableManager));
        tableManager.createAndAddTable();
        tableManager.selectTable(0);

        //Open 2 design windows of the same table
        windows.add(new TableDesignWindow(tableManager));
        windows.add(new TableDesignWindow(tableManager));
        windows.add(new TableRowsWindow(tableManager));

        Table table = tableManager.getSelectedTable();
        table.addCol();

        //This prints 3 trigger statements for 3 different windows

        System.out.println("------------------");

        //create a second window
        tableManager.createAndAddTable();
        tableManager.selectTable(1);

        //Open a design window for the second table
        windows.add(new TableDesignWindow(tableManager));

        //Now apply extra column
        table = tableManager.getSelectedTable();
        table.addCol();
        //This doesn't notify windows from other table
        //This prints 1 trigger statement
    }
}
