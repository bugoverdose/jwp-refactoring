package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import kitchenpos.domain.MenuGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@Transactional
class MenuGroupServiceTest {

    @Autowired
    private MenuGroupService menuGroupService;

    @Test
    void create_메서드는_생성한_메뉴_그룹을_반환한다() {
        MenuGroup menuGroup = new MenuGroup();
        menuGroup.setName("추천메뉴");

        MenuGroup actual = menuGroupService.create(menuGroup);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo("추천메뉴")
        );
    }

    @Test
    void list_메서드는_메뉴_그룹_목록을_조회한다() {
        List<MenuGroup> menuGroups = menuGroupService.list();

        assertThat(menuGroups).hasSizeGreaterThan(1);
    }
}
