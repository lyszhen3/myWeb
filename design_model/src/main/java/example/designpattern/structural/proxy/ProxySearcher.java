package example.designpattern.structural.proxy;

/**
 * Created by lys on 5/8/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ProxySearcher implements Searcher{
	private RealSearcher searcher = new RealSearcher();
	private AccessValidator validator;
	private Logger logger;

	@Override
	public String doSearch(String userId, String keyword) {
		if(this.validate(userId)){
			String result = searcher.doSearch(userId,keyword);
			this.log(userId);
			return result;
		}
		return null;
	}

	public boolean validate(String userId){
		validator = new AccessValidator();
		return validator.validate(userId);
	}

	public void log(String userId){
		logger = new Logger();
		logger.log(userId);
	}
}
