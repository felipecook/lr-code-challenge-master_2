package com.labregister.api.items.service;

import com.labregister.api.core.validation.EntityValidator;
import com.labregister.api.items.domain.Item;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

	//private List<Item> items;

	private Map<String, Deque<Item>> itemMap;

	private EntityValidator entityValidator;

	public ItemServiceImpl(EntityValidator entityValidator) {
		this.entityValidator = entityValidator;
		//this.items = new ArrayList<>();
		this.itemMap = new HashMap<>();
	}

	@Override
	public Item createItem(Item request) {
		entityValidator.validateCreate(request);
		request.setId(UUID.randomUUID().toString());
		// request.setCreationDate(new Date());
		return save(request);
	}

	@Override
	public List<Item> getItems() {
		return this.itemMap.values().stream()
				.map(items -> items.getLast())
				.sorted(Comparator.comparing(Item::getName))
				.collect(Collectors.toList());
	}

	@Override
	public Item updateItem(Item request, String itemId) {
		// if you have new content, lets add those attributes to the old attributes

		/*t
		 * - Validate update (go into EntityValidator.validateUpdate and do validation)
		 * - Find teh Item object in the items list using the ID
		 * - Update with the new attributes/arguments
		 * - Return the updated item
		 *
		 * */
		// need to check how to correctly call the two update validations and their purpose

		if (!itemMap.containsKey(itemId)){
			throw new NoSuchElementException();
		}
		request.setId(itemId);


		entityValidator.validateUpdate(request);

		save(request);

		return request;
	}

	@Override
	public void deleteAllItems() {
		this.itemMap.clear();
	}

	private Item save(Item item) {
		//this.items.add(item);
		Deque<Item> items = this.itemMap.getOrDefault(item.getId(), new LinkedList<>());
		items.add(item);
		this.itemMap.putIfAbsent(item.getId(), items);
		return item;
	}
}
