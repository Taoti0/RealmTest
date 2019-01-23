package com.project.eniac0.realmtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private EditText username,password;
    private Button saveUser,lookUser;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        //实例化数据库
        RealmConfiguration config=new RealmConfiguration.Builder()
                .name("UserRealm.realm")//文件名
                .encryptionKey(new byte[64])//加密用字段,不是64位会报错
                .schemaVersion(0)//版本号
                .build();
        /**
         * 其他方法：
         * Builder.name : 指定数据库的名称。如不指定默认名为default。
         * Builder.schemaVersion : 指定数据库的版本号。
         * Builder.encryptionKey : 指定数据库的密钥。
         * Builder.migration : 指定迁移操作的迁移类。
         * Builder.deleteRealmIfMigrationNeeded : 声明版本冲突时自动删除原数据库。
         * Builder.inMemory : 声明数据库只在内存中持久化。
         * build : 完成配置构建。
         */
        realm=Realm.getInstance(config);

        saveUser=(Button)findViewById(R.id.btn_save);
        saveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realmInsert(realm);
            }
        });

        lookUser=(Button)findViewById(R.id.btn_look);
        lookUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,UserInfo.class);
                startActivity(intent);
            }
        });
    }

    //增
    private void realmInsert(Realm realm){
        username=(EditText)findViewById(R.id.et_username);
        password=(EditText)findViewById(R.id.et_password);

        realm.beginTransaction();//开启事务
        UserDB user=realm.createObject(UserDB.class);
        user.setUserName(username.getText().toString());
        user.setPassWord(password.getText().toString());
        realm.commitTransaction();//提交事务
        user=realm.where(UserDB.class).findFirst();
    }

    //关闭Realm
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
