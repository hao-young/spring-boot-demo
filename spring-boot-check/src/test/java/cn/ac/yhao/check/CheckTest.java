package cn.ac.yhao.check;

import org.junit.Test;

public class CheckTest {

    @Test
    public void testCheck(){
        User user = new User();
        user.setName("张三");
        user.setAge(13);
        this.testUser(user);
    }

    @Check({"name", "age>18:年龄需要满18周岁"})
    public void testUser(User user){
        System.out.println("user: [name:"+user.getName()+"age:" +user.getAge()+"]");
    }

    class User{
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
