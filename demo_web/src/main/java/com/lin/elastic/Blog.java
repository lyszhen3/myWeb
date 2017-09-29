package com.lin.elastic;

/**
 * Created by pc on 2017-01-16.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class Blog {
    private Integer id;
    private String title;
    private String posttime;
    private String content;

    public Blog() {
    }

    public Blog(Integer id, String title, String posttime, String content) {
        this.id = id;
        this.title = title;
        this.posttime = posttime;
        this.content = content;
    }
    //setter and getter


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
