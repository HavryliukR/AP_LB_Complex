package tourapp.ui.menu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenuOptionTest {

    @Test
    void fromCodeReturnsCorrectOption() {
        assertEquals(MenuOption.VIEW_ALL_TOURS, MenuOption.fromCode(3));
        assertEquals(MenuOption.EXIT, MenuOption.fromCode(12));
    }

    @Test
    void fromCodeReturnsNullForUnknownCode() {
        assertNull(MenuOption.fromCode(999));
    }
}
