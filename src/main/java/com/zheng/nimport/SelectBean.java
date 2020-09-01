package com.zheng.nimport;

/**
 * 需要一个无参构造
 */
public class SelectBean {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SelectBean(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    public SelectBean() {
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", age=" + age + '}';
    }
}
