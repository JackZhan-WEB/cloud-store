package club.jackzhan.cloudstore.util;

import club.jackzhan.cloudstore.module.request.CurrentMember;

public class UserThreadLocal {

    //把构造函数私有，外面不能new，只能通过下面两个方法操作
    private UserThreadLocal(){

    }

    private static final ThreadLocal<CurrentMember> LOCAL = new ThreadLocal<CurrentMember>();

    public static void set(CurrentMember user){
        LOCAL.set(user);;
    }

    public static CurrentMember get(){
        return LOCAL.get();
    }
}
