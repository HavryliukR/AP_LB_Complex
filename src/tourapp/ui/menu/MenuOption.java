package tourapp.ui.menu;

public enum MenuOption {
    LOAD_FROM_FILE(1, "Load catalog from file"),
    SAVE_TO_FILE(2, "Save catalog to file"),
    VIEW_ALL_TOURS(3, "View all tours"),
    ADD_TOUR(4, "Add new tour"),
    EDIT_TOUR(5, "Edit tour"),
    DELETE_TOUR(6, "Delete tour"),
    SEARCH_TOURS(7, "Search / filter tours"),
    SORT_TOURS(8, "Sort tours"),
    MANAGE_FAVORITES(9, "Favorite tours"),
    MANAGE_BOOKINGS(10, "Bookings"),
    HELP(11, "Help"),
    EXIT(12, "Exit");

    private final int code;
    private final String description;

    MenuOption(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static MenuOption fromCode(int code) {
        for (MenuOption option : values()) {
            if (option.code == code) {
                return option;
            }
        }
        return null;
    }
}
