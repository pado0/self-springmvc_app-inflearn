package springmvc.application.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // 컴포넌트 스캔의 대상이 됨
public class ItemRepository {

    // 싱글톤 컨트롤러에서는 static 안써도 되는데, 따로 new 해줄 때 애매해서 스태틱
    // 멀티 스레드 환경에서 여러개가 동시에 store에 접근하게 되면 hashmap 사용하면 안됨
    // 사용하려면 concurrentHashmap 사용해야함.
    // long도 atomiclong 등으로 사용해야함
    private static final Map<Long, Item> store = new HashMap<>(); // static
    private static long sequence = 0L; //static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values()); // .values() : list로 변환하는 메서드
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);

        // 별도의 update param 용 객체를 만드는게 맞지만, 간단한 프로젝트이므로 그냥 진행
        // paramater DTO를 만드는게 맞음.
        // Item의 setId()도 있어서 다른 개발자가 헷갈릴 것.
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

   public void clearStore(){
       store.clear();
   }
}
