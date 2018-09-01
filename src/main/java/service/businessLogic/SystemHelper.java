package service.businessLogic;

import entities.Share;

import java.util.List;
import java.util.function.Predicate;

/**
 * A storage of different tools, functions for System & business logic
 * @author Dmitry Tochilin
 */
public class SystemHelper {

    public static Share getShare(List<Share> shares, Predicate<Share> ifIsLoyalty) {
        return shares.stream()
                .filter(ifIsLoyalty)
                .findAny()
                .orElse(null);
    }

    public static class CheckLoyalty implements Predicate<Share>{
        @Override
        public boolean test(Share share) {
            return share.getIsLoyalty();
        }
    }
}
