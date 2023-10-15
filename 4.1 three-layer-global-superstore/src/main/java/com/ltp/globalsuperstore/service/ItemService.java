package com.ltp.globalsuperstore.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ltp.globalsuperstore.Constants;
import com.ltp.globalsuperstore.Item;
import com.ltp.globalsuperstore.repository.ItemRepository;

public class ItemService {
    ItemRepository itemRepository = new ItemRepository();

    
    public boolean within5Days(Date newDate, Date oldDate) {
        long diff = Math.abs(newDate.getTime() - oldDate.getTime());
        return (int) (TimeUnit.MILLISECONDS.toDays(diff)) <= 5;
    }

     public Item getItem(int index) {
        return itemRepository.getItem(index);
    }

    public void addItem(Item item) {
        itemRepository.addItem(item);
    }

    public void updateItem(int index, Item item) {
        itemRepository.updateItem(index, item);
    }

     public int getIndexFromId(String id) {
        for (int i = 0; i < itemRepository.size(); i++) {
            if (itemRepository.getItem(i).getId().equals(id)) return i;
        }
        return Constants.NOT_FOUND;
    }


        public List<Item> getItems() {
        return itemRepository.getItems();
    }


    public int size() {
        return itemRepository.size();
    }

    public String handleSubmit(Item item) {
        int index = getIndexFromId(item.getId());
        String status = Constants.SUCCESS_STATUS;
        if (index == Constants.NOT_FOUND) {
            addItem(item);
        } else if (within5Days(item.getDate(), getItem(index).getDate())) {
            updateItem(index, item);
        } else {
            status = Constants.FAILED_STATUS;
        }

        return status;
    }

    public Item getItemFromId(String id) {
        int index = getIndexFromId(id);
        return  index == Constants.NOT_FOUND ? new Item() : getItem(index);
    }
}
