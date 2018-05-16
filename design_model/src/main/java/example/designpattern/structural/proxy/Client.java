package example.designpattern.structural.proxy;

class Client {
    public static void main(String args[]) {
        Searcher searcher = (Searcher)XMLUtil.getBean();

        searcher.doSearch("杨过","小龙女");
    }  
}