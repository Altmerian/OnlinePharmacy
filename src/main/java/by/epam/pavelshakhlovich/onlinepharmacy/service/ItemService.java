package by.epam.pavelshakhlovich.onlinepharmacy.service;


import by.epam.pavelshakhlovich.onlinepharmacy.entity.Dosage;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;

import java.util.List;

/**
 * Represents an interface of a service for item-related actions
 */
public interface ItemService {

    /**
     * Retrieves all possible dosage forms from dao layer
     */
    List<Dosage> getDosages() throws ServiceException;

    /**
     * Attempts to add a new item with given parameters.
     * @param item item with given parameters
     * @return true if adding succeeded, false if item with such label, dosage and volume already exists
     * @throws ServiceException if DaoException occurred
     */
    boolean addItem(Item item) throws ServiceException;

    /**
     * Attempts to edit item with given parameters.
     * @param item item with given parameters
     * @return true if editing succeeded, false if given parameters incorrect
     * @throws ServiceException if DaoException occurred
     */
    boolean editItem(Item item) throws ServiceException;

    /**
     * Attempts to delete item with given id.
     * @param id is item's id
     * @return true if deleting succeeded, false if no item was found in the storage or item is used in orders
     * @throws ServiceException if DaoException occurred
     */
    boolean deleteItem(long id) throws ServiceException;

    /**
     * Selects item by a given id
     * @param id id of the item
     * @return corresponding item
     * @throws ServiceException if exception occurred on an underlying level
     */
    Item selectItemById(long id) throws ServiceException;

    /**
     * Selects item by a given parameters
     * @param label item's label
     * @param dosageFormId item's dosageFormId
     * @param volume  item's volume or quantity
     * @param volumeType item's volume type
     * @param manufacturerId item's manufacturer id
     * @return item with all parameters from storage
     * @throws ServiceException
     */
    Item selectItemByLabelDosageVolume(String label, long dosageFormId, double volume,
                                       String volumeType, long manufacturerId) throws ServiceException;

    /**
     * Retrieves a list of items from db.
     * @return list that contains limited number of items.
     * @throws ServiceException
     */
    List<Item> selectAllItems() throws ServiceException;

    /**Counts all items in the storage.
     * @return number of items .
     * @throws ServiceException
     */
    int countAllItems() throws ServiceException;

    /**
     * Retrieves a list of items with given label from {@param offset}. List's max limit is {@param limit}.
     * @param offset set the number of the first row from request
     * @param limit set the number of items that will be listed
     * @return list that contains limited number of items.
     * @throws ServiceException
     */
    List<Item> selectItemsByLabel(String label, int offset, int limit) throws ServiceException;

    /**Counts only currently searched items in the storage.
     * @return number of items .
     * @throws ServiceException
     */
    int countItemsByLabel(String label) throws ServiceException;

}
