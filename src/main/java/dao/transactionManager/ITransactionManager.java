package dao.transactionManager;

import dao.exceptions.DaoException;

import java.util.concurrent.Callable;

/**
 * Organises execution of method (unitOfWork) inside transaction
 * Method may have one or more DAO operations, that are groped for atomic operation
 * @author Dmitry Tochilin
 */
public interface ITransactionManager {

    <T> T doInTransaction(Callable<T> unitOfWork) throws DaoException;

}
