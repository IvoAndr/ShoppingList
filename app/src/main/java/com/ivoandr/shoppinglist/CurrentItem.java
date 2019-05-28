package com.ivoandr.shoppinglist;

class CurrentItem extends Item {
    private static CurrentItem INSTANCE;

    void setCurrentItem(Item item) {
        if (item != null && INSTANCE != null) {
            INSTANCE.setId(item.getId());
            INSTANCE.setItem(item.getItem());
            INSTANCE.setQuantity(item.getQuantity());
            INSTANCE.setQuantityType(item.getQuantityType());
            INSTANCE.setPrice(item.getPrice());
            INSTANCE.setStatus(item.getStatus());
            INSTANCE.setDateIndex(item.getDateIndex());
        }
    }

// --Commented out by Inspection START (11.05.19 14:50):
//    void clearItem() {
//        if (INSTANCE != null) {
//            INSTANCE = null;
//        }
//    }
// --Commented out by Inspection STOP (11.05.19 14:50)

    static synchronized CurrentItem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrentItem();
        }

        return INSTANCE;
    }
}
