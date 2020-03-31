package com.meteor.remote.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * The GeneralListModel class is a list of \a ListItem elements
 */
public class GeneralListModel extends  Observable {

   public boolean append(ListItem item) {

      boolean added = false;

      if (! _items.contains(item)) {
         _items.add(item);
         added = true;
      }

      setChanged();
      notifyObservers();

      return added;
   }

   public boolean append(List<ListItem> items) {

      boolean dataAdded = false;

      for (ListItem item : items) {
         /* return true if any item has been added */
         dataAdded |= append(item);
      }

      setChanged();
      notifyObservers();

      return dataAdded;
   }

   /**
    * change the value at given index
    * @param index identified the item
    * @param item  is the new value
    * @return True if \p index is valid
    *
    */
   public boolean setItem(int index, ListItem item) {
      boolean validIndex = false;

      if ((index >= 0) && (index < _items.size())) {
         _items.set(index, item);
         validIndex = true;
      }

      setChanged();
      notifyObservers();

      return validIndex;
   }

   /**
    * get an item
    * @param index identified the item
    * @return a constant reference to an item
    * If \p index is invalid, a default value is returned
    */
   public ListItem getItem(int index) {
      if ((index >= 0) && (index < _items.size())) {
         return _items.get(index);
      }

      return DEFAULT_VALUE;
   }

   /**
    * remove the element at given index
    * @param index must match internal data size
    * @return True if item can be removed
    */
   public boolean remove(int index) {
      boolean removed = false;

      if ((index >= 0) && (index < _items.size())) {
         _items.remove(index);
         removed = true;
      }

      setChanged();
      notifyObservers();

      return removed;
   }

   /**
    * remove any element that matches \p item
    * @param item is compared with internal data
    * @return True if one or more elements have been removed
    */
   public boolean remove(ListItem item) {

      boolean removedOne = false;

      while (_items.contains(item)) {
         _items.remove(item);
         removedOne = true;
      }

      setChanged();
      notifyObservers();

      return removedOne;
   }

   /**
    * @return number of items
    */
   public int length() {
      return _items.size();
   }

   public void clear() {
      _items.clear();
      setChanged();
      notifyObservers();
   }

   /**
    * add \p count elements from index \p baseIndex.
    * New elements will have default values.
    * If baseIndex is higher that current size, elements are appended
    */
   public void insertItems(int baseIndex, int count) {
      for (int i = 0; i < count; i++) {
         _items.set(baseIndex + i, (new ListItem("icon", "label")));
      }

      setChanged();
      notifyObservers();
   }

   /**
    * remove some items from given \p baseIndex
    * @param baseIndex is the index of first item removed. If it's
    *                  higher than current size, nothing is removed
    * @param count     is the number of removed items. If \p baseIndex + \p counts
    *                  goes over last elements, a number of items lower that the requsted
    *                  will be removed.
    */
   public void removeItems( int baseIndex, int count) {
      for (int i=0;
           (i < count) && (baseIndex < _items.size() && (baseIndex >= 0));
           i++)
      {
         _items.remove( baseIndex);
      }

      setChanged();
      notifyObservers();
   }

   private List<ListItem> _items = new ArrayList<>();
   private final ListItem DEFAULT_VALUE = new ListItem("none", "NONE");
};

