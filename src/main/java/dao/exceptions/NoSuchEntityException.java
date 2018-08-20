package dao.exceptions;

import dao.transactionManager.TransactionManagerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoSuchEntityException extends Exception {

    private static final Logger LOGGER = LogManager.getLogger(TransactionManagerImpl.class.getName());

    // todo : optimize logging
    public NoSuchEntityException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
