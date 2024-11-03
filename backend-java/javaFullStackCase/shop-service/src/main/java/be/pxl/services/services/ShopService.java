package be.pxl.services.services;

import be.pxl.services.domain.Shop;
import be.pxl.services.domain.Status;
import be.pxl.services.repositories.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService implements IShopService {

    private final ShopRepository shopRepository;

    @Override
    public Shop getShopContent() {

        Shop shop = new Shop();
        shop.setClientName("Gregory Vervoort");
        shop.setTotalPrice(150.00);

        return shop ;

        //return shopRepository.findById(1L).get();
    }
}
