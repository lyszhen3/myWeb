package com.lin.test.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lin.data.beans.Account;
import com.lin.shiro.ShiroUser;
import com.lin.test.beans.Shop;
import com.lin.test.beans.TestUser;
import com.lin.test.bo.UserBo;
import com.lin.test.services.PrototypeService;
import com.lin.test.services.SchedulePolling.SchedulePolling;
import com.lin.test.services.TestMore;
import com.lin.test.services.TestService;
import com.lin.test.services.TransactionalService;
import com.lin.utils.validatepng.ValidateCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pc on 2017-03-21.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Controller
public class TestAction {
	private final static Logger log = LogManager.getLogger(TestAction.class);
	private static final ReentrantLock lock = new ReentrantLock();
	@Resource(name = "lin_testService")
	private TestService testService;

	private TransactionalService transactionalService;

	@Autowired
	public void setTransactionalService(TransactionalService transactionalService) {
		this.transactionalService = transactionalService;
	}

	private TestMore testMore;

	@Autowired
	@Qualifier("moreSecondImpl")
	public void setTestMore(TestMore testMore) {
		this.testMore = testMore;
	}

	private PrototypeService prototypeService;

	@Autowired
	public void setPrototypeService(PrototypeService prototypeService) {
		this.prototypeService = prototypeService;
	}

	private SchedulePolling schedulePolling;

	@Autowired
	public void setSchedulePolling(SchedulePolling schedulePolling) {
		this.schedulePolling = schedulePolling;
	}

	/**
	 * 老layuidemo
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "oldindex")
	public String test(Model model) {
		int i = testService.testCount();
		model.addAttribute("userName", "我的天啊");
		model.addAttribute("count", i);

		return "sysman/mainIndex";
	}


	/**
	 * layui demo
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "testDemo")
	public String testDemo(Model model) {
		return "demo/index";
	}

	/**
	 * shiro login
	 *
	 * @param name
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "testShiroLogin")
	public String testShiroLogin(String name, String password, Model model) {
		String message = "";
		UsernamePasswordToken token = new UsernamePasswordToken(name, password);
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {

			try {
				subject.login(token);

				return "redirect:testDemo";
			} catch (UnknownAccountException ex) {//用户名没有找到
//ex.printStackTrace;
				message = "用户名不存在";
			} catch (IncorrectCredentialsException ex) {//用户名密码不匹配
// ex.printStackTrace;
				message = "用户名和密码不匹配";
			} catch (LockedAccountException lae) {// 用户被锁定
//lae.printStackTrace;
				message = "用户被锁定";
			} catch (AuthenticationException e) {//其他的登录错误
				message = "系统错误";
			}

		}
		model.addAttribute("msg", message);
		return "demo/login";

	}

	@RequestMapping(value = "testList")
	public String testList(Model model) {
		List<Account> list = testService.selList();
		log.info("#testList:info--测试info");
		model.addAttribute("list", list);
		return "demo/lists";
	}

	@RequestMapping(value = "rightPanel", method = RequestMethod.GET)
	public String rightPanel() {

		return "sysman/common/rightPanel";
	}

	@RequestMapping(value = {"cllist"})
	public String cllist(UserBo bo, Model model) {

		List<Shop> shops = new ArrayList<Shop>();
		for (int i = 0; i < 10; i++) {
			Shop shop = new Shop();
			shop.setShopName("啊");
			shop.setUserName("嗯");
			shops.add(shop);
		}


		if (bo.getPage() == null) {
			bo.setPage(1);

		}
		if (bo.getRows() == null) {
			bo.setRows(10);
		}
		bo.setTotalPage(10);

		model.addAttribute("list", shops);
		model.addAttribute("bo", bo);


		return "sysman/shop/shoplist";
	}

	@RequestMapping("upimg")
	@ResponseBody
	public JSONObject upimg(MultipartFile file) throws IOException {
		InputStream in = file.getInputStream();
		JSONObject obj = new JSONObject();

		obj = JSONObject.parseObject("{\n" +
				"            \"code\": 0 //0表示成功，其它失败\n" +
				"                ,\"msg\": \"\" //提示信息 //一般上传失败后返回\n" +
				"                ,\"data\": {\n" +
				"            \"src\": \"upimg/qb110.jpg\"\n" +
				"                    ,\"title\": \"图片名称\" //可选\n" +
				"        }\n" +
				"        }");
		System.out.println(obj.toString());
		return obj;

	}

	@RequestMapping("addOne")
	@ResponseBody
	public JSONObject addOne(UserBo bo) {
		System.out.println(bo.getContent());
		System.out.println(bo.getTitle());
		JSONObject obj = new JSONObject();
		obj.put("msg", "成功");
		obj.put("result", "error");

		return obj;
	}

	@RequestMapping("test")
	public String testFtl(Model model, HttpServletResponse response) throws IOException {
//        response.setContentType("image/png");
//        vCode.write(response.getOutputStream());
//        response.addHeader("Content-Disposition", "attachment;filename=image.png");
		model.addAttribute("name", "负载2");
		return "test";
	}

	/**
	 * 测试单列controller下多列service
	 *
	 * @return
	 */
	@RequestMapping("prototypeTest")
	@ResponseBody
	public JSONObject testProto() {
		JSONObject obj = new JSONObject();
		obj.put("msg", "成功");
		obj.put("result", prototypeService.toString());
		prototypeService.innerBeanScope();
		return obj;
	}

	@RequestMapping("schedulePollingTest")
	@ResponseBody
	public JSONObject schedulePollingTest() {

		schedulePolling.excute();
		return JSON.parseObject("{}");
	}

	/**
	 * 返回验证码图片
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("verCode")
	public void verCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("image/png");

		ValidateCode vCode = new ValidateCode(120, 40, 5, 100);

		vCode.write(response.getOutputStream());
		HttpSession session = request.getSession();
		session.setAttribute("verCode", ValidateCode.getCode());
		response.getOutputStream().flush();


	}

	/**
	 * 测试spring事务
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("test2")
	@ResponseBody
	public JSONObject testTransaction(HttpServletRequest request) throws Exception {
		try {
			testService.testTransation();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.parseObject("{'result':'success','msg':'草。。'}");
	}

	/**
	 * 验证下验证码是否正确
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("testVerCode")
	@ResponseBody
	public JSONObject testVerCode(HttpServletRequest request) {
		String verCode = (String) request.getSession().getAttribute("verCode");
		return JSON.parseObject("{'result':'success','msg':'" + verCode + "'}");
	}

	@RequestMapping("testDemo2")
	@ResponseBody
	public JSONObject testMvcJSON() {
		return JSON.parseObject("{'result':'success','msg':'去死吧'}");
	}

	/**
	 * 测试事务可读性
	 * 修改
	 * 增加lock 使testFirst 和testFirst2串行化
	 *
	 * @return
	 */
	@RequestMapping("testFirst")
	@ResponseBody
	public JSONObject testTransaction2(Long id) {

		try {
			lock.lock();
			testService.updateByPK(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

		return JSON.parseObject("{'result':'success','msg':'去死吧'}");
	}

	/**
	 * 查询
	 *
	 * @return
	 */
	@RequestMapping("testFirst2")
	@ResponseBody
	public JSONObject testTransaction3(Long id) {
		Account account;
		try {
			lock.lock();
			account = testService.selOne(id);
		} finally {
			lock.unlock();
		}
		return JSON.parseObject(JSON.toJSONString(account));
	}

	/**
	 * 测试负载session共享 所以呢不是session共享不要访问了
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("testLogin")
	@ResponseBody
	public JSONObject testLogin(HttpServletRequest request) {
		String id = request.getSession().getId();
		TestUser testUser = (TestUser) request.getSession().getAttribute("testUser");
		String name = "没有用户";
		if (testUser == null) {
			testUser = new TestUser();
			testUser.setId(1111L);
			testUser.setName("元盛");
			request.getSession().setAttribute("testUser", testUser);
		} else {
			name = testUser.getName();
		}

		return JSON.parseObject("{'sessionId':'" + id + "','userName':'" + name + "'}");
	}

	@RequestMapping("testDubbo")
	@ResponseBody
	public JSONObject testDubbo() {
		testService.testDubbo();
		return JSON.parseObject("{'result':'success'}");
	}

	/**
	 * 分布式锁测试
	 *
	 * @return json
	 */
	@RequestMapping("testZkLockRead")
	@ResponseBody
	public JSONObject testZkLockRead() {

		return testService.testZkLockRead();

	}

	/**
	 * 分布式锁测试
	 *
	 * @return json
	 */
	@RequestMapping("testZkLockWrite")
	@ResponseBody
	public JSONObject testZkLockWrite() {

		return testService.testZkLockWrite();
	}

	/**
	 * 标签验证参数
	 *
	 * @param id
	 * @param bo
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "testValid/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String testValid(@PathVariable("id") Integer id, @Valid Shop bo, BindingResult result) {
		String msg = "";
		if (result.hasErrors()) {
			msg = result.getAllErrors().get(0).getDefaultMessage();
		}

		return msg;
	}

	@RequestMapping("tttttt")
	@ResponseBody
	public String tttttt(@Deprecated Shop shop, @Valid TestUser user, Errors errors) {
		if (errors.hasErrors()) {
			throw new NullException(errors.getAllErrors().get(0).getDefaultMessage());
		}
		System.out.println(shop.getShopName());
		System.out.println(user.getName());
		return "hello";
	}

	public class NullException extends RuntimeException {

		private static final long serialVersionUID = 2014051027509027137L;

		NullException(String msg) {
			super(msg);
		}
	}

	/**
	 * 异常处理
	 *
	 * @param ex
	 * @return
	 */
//	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<JSONObject> handleRuntimeException(RuntimeException ex) {
		JSONObject jsonObject = JSON.parseObject(String.format("{\"message\":\"%s\"}", ex.getMessage()));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonObject);
	}

	/**
	 * 通过实现接口来通过jdk代理
	 * 调用接口中得方法事务同样可以回滚
	 *
	 * @return
	 */
	@RequestMapping("testAopTransactional")
	@ResponseBody
	public String testAopTransactional() {
		transactionalService.a();
		return "ok";
	}

	/**
	 * 测试重复读
	 *
	 * @return
	 */
	@RequestMapping("testSpringPropagation")
	@ResponseBody
	public String testSpringPropagation() {
		testService.testSpringPropagation();
		return "ok";
	}

	/**
	 * 测试 Qualifier 注解的使用
	 *
	 * @return
	 */
	@RequestMapping("testSpringMoreImpl")
	@ResponseBody
	public String testSpringMoreImpl() {
		testMore.say();
		return "ok";
	}

	@RequestMapping("/excel")
	@ResponseBody
	public void testExportExcel(HttpServletResponse response) {

		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			System.out.println(response.getCharacterEncoding());
			response.setHeader("Content-disposition", "attachment;filename=" +
					encodeURIComponent("文件名.txt") + ";" + "filename*=utf-8''" + encodeURIComponent("文件名.txt"));
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(2);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping("sessiontest")
	public String getSession(HttpServletRequest request,HttpServletResponse response){
		String id = request.getSession().getId();

		Cookie cookie = new Cookie("SESSION",id);
		response.addCookie(cookie);

		return id;
	}

	/**
	 * 测试直接write
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("testwrite")
	public void writeout(HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");

		try(PrintWriter writer = response.getWriter()){
			writer.write("ok");
		}catch (IOException ignore) {
		}

	}

	@ResponseBody
	@RequestMapping(value = "/paytest",method = {RequestMethod.POST})
	public String pay(String money){
		Subject subject = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
		return shiroUser.getUserName()+":支付成功"+money;
	}

	public static String encodeURIComponent(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}

}
