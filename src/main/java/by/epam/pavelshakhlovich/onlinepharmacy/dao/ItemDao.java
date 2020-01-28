package by.epam.pavelshakhlovich.onlinepharmacy.dao;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.Dosage;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;

import java.util.List;

/**
 * Represents an interface for retrieving item-related data. Contains all methods, required for getting such data from
 * the database
 */
public interface ItemDao extends BaseDao<Item> {
    /**
     * Retrieves an item with given id.
     *
     * @param label          item's label
     * @param dosageId       item's dosage ID
     * @param volume         item's volume
     * @param volumeType     item's volume type
     * @param manufacturerId item's manufacturer ID
     * @return Item with corresponding label,dosage and volume and other parameters or {@code null} if such item doesn't exist
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    Item selectItemByLabelDosageVolume(String label, long dosageId, double volume,
                                       String volumeType, long manufacturerId) throws DaoException;

    /**
     * Retrieves a list of items with given label from {@param offset}. List's max limit is {@param limit}.
     *
     * @param label  item's title
     * @param offset set the number of the first row from request
     * @param limit  set the number of items that will be listed
     * @return list that contains limited number of items.
     * @throws DaoException
     */
    List<Item> selectItemsByLabel(String label, int offset, int limit) throws DaoException;

    /**
     * Counts all items in the storage.
     *
     * @return number of items .
     * @throws DaoException
     */
    int countAllItems() throws DaoException;

    /**
     * Counts only currently searched items in the storage.
     *
     * @param label item's title
     * @return number of items .
     * @throws DaoException
     */
    int countItemsByLabel(String label) throws DaoException;

    /**
     * Add a new dosage to the storage, e.g. into database
     *
     * @param dosage that should be stored in data source
     * @return true if dosage was added and false if dosage with such parameters already exists
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean addDosage(String dosage) throws DaoException;

    /**
     * Retrieves all possible dosages from data source
     */
    List<Dosage> getDosages() throws DaoException;

    /**
     * Retrieves dosage by name from data source
     *
     * @param dosage dosage name
     * @return dosage with given name
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    Dosage getDosageByName(String dosage) throws DaoException;
}
