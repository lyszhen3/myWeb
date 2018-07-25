package com.lin.modules;

import com.lin.test.services.ImageService;
import org.elasticsearch.join.aggregations.Children;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lys on 2018/7/23.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Service
@Scope(value= "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
@Lazy
public class FamilyModule {
	private ImageService imageService;

	@Autowired
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	private String id;

	private Family family;
	private List<Children> childrenList;



	public FamilyModule(String id){
		this.family = getById(id);
	}

	private Family getById(String id) {
		Family family = new Family();
		family.setName("加白了");
		family.setId(id);
		return family;
	}

	public FamilyModule(){

	}

	public List<Children> getChilds(){
		Children children = new Children();
		children.setId("11");
		children.setName("two");
		return Collections.singletonList(children);
	}

	public void deleteChilds(){


	}









	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Children> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<Children> childrenList) {
		this.childrenList = childrenList;
	}

	public class Family{
		private String id;
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public class Children{
		private String id;
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static void main(String[] args) {
	}
}
