package com.ivoandr.shoppinglist;

class LinksManager {
    private static LinksManager INSTANCE;

    private final String list = "https://www.distortedreality.eu/shoppinglist/php/list.php?act=";
    private final String item = "https://www.distortedreality.eu/shoppinglist/php/item.php?act=";

    public String getAllLists() {
        return list + "getLists";
    }

    public String getListByDate(int id) {
        return list + "getListByDate&dateId=" + id;
    }

    public String getNewList() {
        return list + "newList&data=";
    }

    public String getDelList(int id) {
        return list + "delList&id=" + id;
    }

    public String getUpdateItem() {
        return item + "updateItem&item=";
    }

    public String getDelItem(int id, int listId) {
        return item + "delItem&id=" + id + "&listId=" + listId;
    }

    public String getAddItem() {
        return item + "addItem&item=";
    }

    static synchronized LinksManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LinksManager();
        }

        return INSTANCE;
    }
}
