package rs.webshop.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import rs.webshop.domain.Item;
import rs.webshop.dto.item.CreateItemCmd;
import rs.webshop.dto.item.ItemInfo;
import rs.webshop.dto.item.ItemResult;
import rs.webshop.dto.item.UpdateItemCmd;

@Mapper
public interface ItemMapper {

  ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

  Item createItemCmdToItem(CreateItemCmd cmd);

  @Mapping(target = "productDto", source = "product")
  List<ItemResult> listItemToListItemResult(List<Item> items);

  @Mapping(target = "productDto", source = "product")
  ItemInfo itemToItemInfo(Item item);

  void updateItemCmdToItem(@MappingTarget Item item, UpdateItemCmd cmd);

}
