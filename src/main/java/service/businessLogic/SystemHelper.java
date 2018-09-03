package service.businessLogic;

import entities.Order;
import entities.Share;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A storage of different tools, functions business logic
 * @author Dmitry Tochilin
 */
public class SystemHelper {

    public static Share getLoyalty(List<Share> sharesOfOrder, Optional<Boolean> isOn) {

        // given that order may have only one loyalty and/or one share
        return sharesOfOrder.stream()
                .filter(loy -> (loy!=null && loy.getIsLoyalty()) && (!isOn.isPresent() || isOn.get().equals(loy.getIsOn())))
                .findAny()
                .orElse(null);
    }

    public static Share getShareNotLoyalty(List<Share> sharesOfOrder, Optional<Boolean> isOn) {
        return sharesOfOrder.stream()
                .filter(shar -> (shar!=null && !shar.getIsLoyalty()) && (!isOn.isPresent() || isOn.get().equals(shar.getIsOn())))
                .findAny()
                .orElse(null);
    }



}
