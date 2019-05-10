package batchexcute;

import java.util.List;

/**
 * @author LinYuanSheng
 * @date 5/10/2019
 */
public class LinItemService {
    private Long ownerId;
    private List<String> items;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
