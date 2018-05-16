package example.designpattern.structural.proxy;

/**
 * Created by lys on 5/8/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class RealSearcher implements Searcher{
	@Override
	public String doSearch(String userId, String keyword) {
		System.out.printf("用户:%s使用关键词:%s查询信息",userId,keyword);
		return "信息";
	}
}
