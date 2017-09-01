package com.system.base.pojo;

public class Person {
	private String id;
	private String name;
	private String sex;
	private int age;
	private String password;
	
	public Person() {

	}

	public Person(String i, String name, String sex, int age, String password) {
		this.id = i;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
