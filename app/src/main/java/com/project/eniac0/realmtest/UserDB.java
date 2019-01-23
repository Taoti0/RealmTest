package com.project.eniac0.realmtest;

import android.widget.RemoteViews;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by Eniac0 on 2019/1/20.
 * 创建用户数据库，并封装
 * 支持的属性：
 * boolean, byte, short,int,long,float, double,String, Date，byte[],
 * RealmObject, RealmList<? extends RealmObject>
 * Boolean, Byte, Short, Integer, Long, Float 和 Double
 */

/**
 * 声明Realm 数据模型
 * 方法一：直接继承于RealmObject来声明
 *
 * @PrimaryKey      表示该字段是主键
 * @Required        表示该字段非空
 * @Ignore          表示忽略该字段
 * @Index           添加搜索索引
 */
public class UserDB extends RealmObject {
    private String userName;
    private String passWord;

    //对象的get,set方法
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }

    public String getPassWord(){
        return passWord;
    }
    public void setPassWord(String passWord){
        this.passWord=passWord;
    }
}


/**
 * 方法二：通过实现 RealmModel接口并添加 @RealmClass修饰符来声明
 *
    @RealmClass
    public class UserDB implements RealmModel{

    }
*/
