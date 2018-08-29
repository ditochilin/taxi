package dao.factoryDao;

import dao.AbstractDao;
import dao.implementation.RoleDaoImpl;
import dao.implementation.UserDaoImpl;
import entities.Role;
import entities.User;

public final class DaoFactory<T> {

    public static AbstractDao getDao(){
//        if (T instanceof User){
//            return UserDaoImpl.getInstance();
//        }
//
//        if (T instanceof Role){
//            return RoleDaoImpl.getInstance();
//        }
//
//        //todo
        return null;
    }

}
