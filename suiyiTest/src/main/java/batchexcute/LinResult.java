package batchexcute;

import java.util.List;

/**
 * @author LinYuanSheng
 * @date 5/8/2019
 */
public class LinResult<T> {
    private T moudle;

    private List<String> serviceCodes;

    public List<String> getServiceCodes() {
        return serviceCodes;
    }

    public void setServiceCodes(List<String> serviceCodes) {
        this.serviceCodes = serviceCodes;
    }

    public T getMoudle() {
        return moudle;
    }

    public void setMoudle(T moudle) {
        this.moudle = moudle;
    }
}
