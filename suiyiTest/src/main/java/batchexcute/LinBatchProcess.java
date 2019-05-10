package batchexcute;

import java.util.*;

/**
 * @author LinYuanSheng
 * @date 5/8/2019
 */
public class LinBatchProcess<R> extends BatchProcess<List<R>> {
    public LinBatchProcess(AbstractMethodHandler<List<R>> handler) {
        super(handler);
    }

    @Override
    public List<R> doProcess(Set<Long> ids, int limit) {
        List<R> list = new ArrayList<>();
//        usaResult.setServiceCodes(new ArrayList<>(ids.size()));
        int offset = 0;
        Iterator<Long> iterator1 = ids.iterator();
        Set<Long> subScItemIds = null;
        while (offset < ids.size()) {
            subScItemIds = new HashSet<>(limit);
            for (int i = offset; i < (offset + limit) && iterator1.hasNext(); i++) {
                subScItemIds.add(iterator1.next());
            }
            //执行方法

            List<R> subUsaResult = this.handler.doAction(subScItemIds);
            list.addAll(subUsaResult);
            offset = offset + limit;
            System.out.println("分批");
        }
        return list;
    }

    public static void main(String[] args) {
        Set<Long> scItems = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            scItems.add((long) i);
        }
        Long ownerId = 1L;
        String spCode = "11";
        BatchProcess<List<String>> listBatchProcess = new LinBatchProcess<String>(new AbstractMethodHandler<List<String>>() {
            @Override
            public List<String> doAction(Set<Long> ids) {
                return getSub(ownerId, spCode, ids);
            }
        });

        List<String> objects = listBatchProcess.doProcess(scItems, 2);

        objects.forEach(System.out::println);
    }

    private static List<String> getSub(Long ownerId, String spCode, Set<Long> ids) {

        List<String> ss = new ArrayList<String>();

        for (Long next : ids) {
            if (next.equals(ownerId)) {
                continue;
            }
            ss.add(next.toString());
        }
        LinResult r = new LinResult();
        r.setServiceCodes(ss);
        return r.getServiceCodes();

    }

}
