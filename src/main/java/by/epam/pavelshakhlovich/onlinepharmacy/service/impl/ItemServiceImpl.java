package by.epam.pavelshakhlovich.onlinepharmacy.service.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.ItemDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.impl.ItemDaoSQLImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Dosage;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * An implementation of the {@see ItemService} interface
 */
public class ItemServiceImpl implements ItemService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ItemDao itemDao = new ItemDaoSQLImpl();

    @Override
    public boolean addItem(Item item) throws ServiceException {
        try {
            if (itemDao.selectItemByLabelDosageVolume(item.getLabel(), item.getDosageId(),
                    item.getVolume(), item.getVolumeType(), item.getManufacturerId()) != null) {
                return false;
            } else {
                return itemDao.create(item);
            }
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public boolean editItem(Item item) throws ServiceException {
        try {
            return itemDao.update(item);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public boolean deleteItem(long id) throws ServiceException {
        try{
            if (itemDao.selectById(id) != null) {
                return itemDao.delete(id);
            } else {
                return false;
            }
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public Item selectItemById(long id) throws ServiceException {
        try {
            return itemDao.selectById(id);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public Item selectItemByLabelDosageVolume(String label, long dosageId, double volume,
                                              String volumeType, long manufacturerId) throws ServiceException {
        try {
            return itemDao.selectItemByLabelDosageVolume(label, dosageId, volume, volumeType, manufacturerId);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public List<Item> selectAllItems(int offset, int limit) throws ServiceException {
        try {
            return itemDao.selectAll(offset, limit);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public List<Item> selectItemsByLabel(String label, int offset, int limit) throws ServiceException {
        try {
            return itemDao.selectItemsByLabel(label, offset, limit);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public int countItemsByLabel(String label) throws ServiceException {
        try {
            return itemDao.countItemsByLabel(label);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public List<Dosage> getDosages() throws ServiceException {
        try {
            return itemDao.getDosages();
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public boolean addDosage(String dosage) throws ServiceException {
        try {
            if (itemDao.getDosageByName(dosage) != null) {
                return false;
            } else {
                return itemDao.addDosage(dosage);
            }
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }
}
