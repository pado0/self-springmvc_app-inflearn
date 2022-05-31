package springmvc.application.domain.web.basic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springmvc.application.domain.item.Item;
import springmvc.application.domain.item.ItemRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
@Slf4j
public class BasicItemController {
    private final ItemRepository itemRepository;

    // 상품 목록
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    // 상품 상세
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // 상품 추가 폼
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }
    // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item); // 모델에 값을 담아

        return "basic/item"; // 특정 뷰에 리턴
    }

    // 상품 등록 버튼 선택

    //@PostMapping("/add")
    // model attribute에 설정한 이름의 의미는? 모델에 자동으로 넣어주는 이름이다.
    public String addItemV2(@ModelAttribute("item") Item item){//, Model model){ // 생략가능

          // model attribute가 대신 해줌
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);

        itemRepository.save(item);
        // model.addAttribute("item", item); // 모델에 값을 담아 // 이름 지정해주었으므로 생략 가능

        // return "basic/item"; // 특정 뷰에 리턴
        return "redirect:/basic/items/" + item.getId(); // 새로고침 문제 해결
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    // 상품 수정
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";

    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }


    @PostConstruct
    public void init(){
        itemRepository.save(new Item("a", 10000, 20));
        itemRepository.save(new Item("b", 20000, 10));
    }

}
