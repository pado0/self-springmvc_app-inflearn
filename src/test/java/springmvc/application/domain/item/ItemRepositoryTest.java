package springmvc.application.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    // 스프링 없이 테스트
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach // 테스트 끝날때마다 실행
    void afterEach(){
        itemRepository.clearStore(); // 끝날때마다 데이터 지워줌
    }

    @Test
    void save(){
        // given
        Item item = new Item("itemA", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll(){
        // given
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 12000, 12);

        itemRepository.save(item1);
        itemRepository.save(item2);

        // when
        List<Item> result = itemRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);
    }

    @Test
    void updateItem(){
        // given
        Item item = new Item("itemC", 10000, 20);
        Item savedItem = itemRepository.save(item);

        Long itemId = savedItem.getId();

        // when
        Item willUpdate = new Item("itemD", 10000, 20);
        itemRepository.update(itemId, willUpdate);

        // then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(willUpdate.getItemName());
    }
}
