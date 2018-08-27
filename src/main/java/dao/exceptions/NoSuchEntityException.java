package dao.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoSuchEntityException extends Exception {

    private static final Logger LOGGER = LogManager.getLogger(NoSuchEntityException.class.getName());

    public NoSuchEntityException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
