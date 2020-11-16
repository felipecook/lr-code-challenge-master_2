package com.labregister.api.items.service;

import com.labregister.api.items.domain.Item;

import java.util.List;

public interface ItemService {

	Item createItem(Item item);

	List<Item> getItems();

	Item updateItem(Item item, String itemId);

	void deleteAllItems();
}
