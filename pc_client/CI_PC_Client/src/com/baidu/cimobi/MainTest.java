package com.baidu.cimobi;

import java.io.*;


public class MainTest {

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		System.out.println(System.getProperty("user.dir"));
//		
		
		/*
		 * 1.节点流：直接连接到实际的数据源；
		 * 2.处理流：对一个已存在的流进行连接和封装，通过封装后的流来实现数据的读、写功能；
		 * 
		 *  **处理流一个明显的好处是，只要使用相同的处理流，程序就可以采用完全相同的输入、输出代码来访问不同的数据源，
		 *  随着处理流包装节点流变化，程序实际所访问的数据源也相应地发发生变化
		 *  
		 *  1.InputStream/Reader：所有输入流的基类，前者是字节输入流，后者是字符流输入流
		 *  2.OutputStream/Writer：所有输出流的基类，前者是字节输入流，后者是字符流输入流
		 * */
//		
//		test8();
//
//	}
	
	/*
	 *  字符流
	 * */
	public static void test1(){
		try{
			FileReader fis = new FileReader("./a.txt"); 
			
			char[] bbuf = new char[4];
			int hasRead = 0;
			while((hasRead = fis.read(bbuf))>0){
				System.out.println(new String(bbuf,0,hasRead));
			}
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	/*
	 * 字节流
	 * */
	public static void test2(){
		
		try{
			FileInputStream fis = new FileInputStream("./a.txt"); 
			
			byte[] bbuf = new byte[4];
			int hasRead = 0;
			while((hasRead = fis.read(bbuf))>0){
				System.out.println(new String(bbuf,0,hasRead));
			}
			fis.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 包装成PrintStream后进行输出
	 * 
	 * */
	public static void test3(){
		try{
		    FileOutputStream fos = new FileOutputStream("a.txt");
		    PrintStream ps = new PrintStream(fos);
		    ps.println("普通字符串");
		    ps.println(new MainTest());
		    
		}catch(Exception e){
			e.printStackTrace();
	    }
	}
	
	
	/*
	 * 1.BufferedInputStream
	 * 2.BufferedOutputStream
	 * 3.BufferedReader
	 * 4.BufferedWriter
	 * buffered 有readLine和writeLine的方法，可以方便的处理一行的输入输出
	 * */
	public static void test4(){
		
	}
	
	/*
	 * 1.InputStreamReader
	 * 2.OutputStreamWriter
	 * 字节流转化为字符流
	 * */
	public static void test5(){
		try{
			InputStreamReader reader = new InputStreamReader(System.in);
			BufferedReader buf = new BufferedReader(reader);
			String str = null;
			while((str = buf.readLine()) !=null){
				System.out.println(str);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	/*
	 * 序列化
	 * Serializable 序列化
	 * Externalizable 反序列化
	 * */
//	public static void test6(){
//		try{
//			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./object.txt"));
//			ObjectTest objectTest = new ObjectTest("1",2);
//			oos.writeObject(objectTest);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
	/*
	 * 
	 * */
//	public static void test7(){
//		try{
//			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./object.txt"));
//			ObjectTest o = (ObjectTest)ois.readObject();
//			System.out.println(o.getA());
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
	
	/*
	 * Serializable 序列化
	 * */
//	public static void test8(){
//		try{
////			StringReader sr = new StringReader(0)
//			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./object.txt"));
////			BufferedInputStream buf = new BufferedInputStream();
////			byte[] b = new byte[1024];
////			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new ));
//			
//			ObjectTest objectTest = new ObjectTest("1",2);
//			oos.writeObject(objectTest);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}

}



