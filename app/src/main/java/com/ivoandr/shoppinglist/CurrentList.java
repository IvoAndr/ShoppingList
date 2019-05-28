package com.ivoandr.shoppinglist;

class CurrentList extends ShoppingList {
    private static CurrentList INSTANCE;

    public void setCurrentList(ShoppingList list) {
        if (list != null && INSTANCE != null) {
            INSTANCE.setId(list.getId());
            INSTANCE.setName(list.getName());
            INSTANCE.setItemsCount(list.getItemsCount());
            INSTANCE.setDate(list.getDate());
        }
    }

    static synchronized CurrentList getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrentList();
        }

        return INSTANCE;
    }
}
