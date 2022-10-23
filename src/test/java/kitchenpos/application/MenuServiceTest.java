package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@Transactional
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @DisplayName("create 메서드는")
    @Nested
    class CreateTest {

        @Test
        void 생성한_메뉴를_반환한다() {
            Menu menu = new Menu();
            menu.setName("후라이드+후라이드");
            menu.setPrice(BigDecimal.valueOf(19000));
            menu.setMenuGroupId(1L);
            MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProductId(1L);
            menuProduct.setQuantity(2);
            menu.setMenuProducts(List.of(menuProduct));

            Menu actual = menuService.create(menu);
            assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("후라이드+후라이드"),
                () -> assertThat(actual.getPrice()).isNotNull(),
                () -> assertThat(actual.getMenuGroupId()).isEqualTo(1L),
                () -> assertThat(actual.getMenuProducts()).hasSize(1)
            );
        }

        @Test
        void 가격정보가_누락된_경우_예외발생() {
            Menu menu = new Menu();
            menu.setName("후라이드+후라이드");
            menu.setMenuGroupId(1L);
            MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProductId(1L);
            menuProduct.setQuantity(2);
            menu.setMenuProducts(List.of(menuProduct));

            assertThatThrownBy(() -> menuService.create(menu))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 가격이_음수인_경우_예외발생() {
            Menu menu = new Menu();
            menu.setName("후라이드+후라이드");
            menu.setPrice(BigDecimal.valueOf(-1));
            menu.setMenuGroupId(1L);
            MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProductId(1L);
            menuProduct.setQuantity(2);
            menu.setMenuProducts(List.of(menuProduct));

            assertThatThrownBy(() -> menuService.create(menu))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 존재하지_않는_상품_id인_경우_예외발생() {
            Menu menu = new Menu();
            menu.setName("후라이드+후라이드");
            menu.setPrice(BigDecimal.valueOf(-1));
            menu.setMenuGroupId(1L);
            MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProductId(9999999L);
            menuProduct.setQuantity(2);
            menu.setMenuProducts(List.of(menuProduct));

            assertThatThrownBy(() -> menuService.create(menu))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 존재하지_않는_메뉴_그룹_id인_경우_예외발생() {
            Menu menu = new Menu();
            menu.setName("후라이드+후라이드");
            menu.setPrice(BigDecimal.valueOf(-1));
            menu.setMenuGroupId(9999999L);
            MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProductId(1L);
            menuProduct.setQuantity(2);
            menu.setMenuProducts(List.of(menuProduct));

            assertThatThrownBy(() -> menuService.create(menu))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 상품명이_누락된_경우_예외발생() {
            Menu menu = new Menu();
            menu.setPrice(BigDecimal.valueOf(19000));
            menu.setMenuGroupId(1L);
            MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProductId(1L);
            menuProduct.setQuantity(2);
            menu.setMenuProducts(List.of(menuProduct));

            assertThatThrownBy(() -> menuService.create(menu))
                .isInstanceOf(DataAccessException.class);
        }

        @Test
        void 개별_상품을_따로_판매할_때에_비해_가격이_높은_경우_예외발생() {
            Menu menu = new Menu();
            menu.setPrice(BigDecimal.valueOf(999999999));
            menu.setMenuGroupId(1L);
            MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProductId(1L);
            menuProduct.setQuantity(2);
            menu.setMenuProducts(List.of(menuProduct));

            assertThatThrownBy(() -> menuService.create(menu))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 메뉴를_구성하는_개별_상품에_대한_정보가_누락된_경우_예외발생() {
            Menu menu = new Menu();
            menu.setPrice(BigDecimal.valueOf(0));
            menu.setMenuGroupId(1L);

            assertThatThrownBy(() -> menuService.create(menu))
                .isInstanceOf(NullPointerException.class);
        }
    }

    @Test
    void list_메서드는_메뉴_목록을_조회한다() {
        List<Menu> menus = menuService.list();

        assertThat(menus).hasSizeGreaterThan(1);
    }
}
