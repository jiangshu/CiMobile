package com.baidu.cimobi.mobileinfo;

public class ObjectTest implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String a;
	private int b;
    public ObjectTest(String a,int b){
	   this.a = a;
	   this.b = b;
    }
    
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
    
}
