package com.project.eniac0.realmtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Eniac0 on 2019/1/20.
 */

public class UserInfo extends Activity implements AdapterView.OnItemLongClickListener,View.OnCreateContextMenuListener{

    Realm realm;
    ListView lv_user;
    TextView lv_username,lv_password;
    Map<String, Object> map;
    List<Map<String,Object>> list;
    SimpleAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        lv_user=(ListView)findViewById(R.id.lv_info);
        lv_username=(TextView)findViewById(R.id.lv_username);
        lv_password=(TextView)findViewById(R.id.lv_password);
        Realm.init(this);
        //实例化数据库
        RealmConfiguration config=new RealmConfiguration.Builder()
                .name("UserRealm.realm")//文件名
                .encryptionKey(new byte[64])//加密用字段,不是64位会报错
                .schemaVersion(0)//版本号
                .build();
        realm=Realm.getInstance(config);

        //适配器
        adapter=new SimpleAdapter(this,
                getData(),
                R.layout.lv_user,
                new String[]{"username","password"},
                new int[]{R.id.lv_username,R.id.lv_password});

        lv_user.setAdapter(adapter);
        lv_user.setOnItemLongClickListener(this);
        lv_user.setOnCreateContextMenuListener(this);
    }
    //查
    private List<Map<String, Object>> getData() {
        RealmResults<UserDB> userList = realm.where(UserDB.class)
                .findAll();
        list=new ArrayList<Map<String, Object>>();
        map = new HashMap<String, Object>();
        for (int i = 0; i < userList.size(); i++) {
            //Log.d("1", "1==>" + userList.get(i).getUserName() + "----" + userList.get(i).getPassWord());
            String lv_username = userList.get(i).getUserName();
            String lv_password = userList.get(i).getPassWord();
            map=new HashMap<String,Object>();
            map.put("username",lv_username);
            map.put("password",lv_password);
            list.add(map);
        }
        return list;
    }
    //改
    private void realmUpdata(Realm realm){

    }
    //删
//    private void realmDelete(Realm realm){
//        realm.beginTransaction();
//        RealmResults results=realm.where(UserDB.class)
//    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("操作");
        menu.add(0,1,0,"详情");
        menu.add(0,2,1,"删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case 1:
                Toast.makeText(this,"第"+item.getItemId()+"个用户信息",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                final int position=(int) lv_user.getAdapter().getItemId(menuInfo.position);
                if (list.remove(position)!=null){
                    final RealmResults<UserDB> user=realm.where(UserDB.class).findAll();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            user.deleteFromRealm(position);
                        }
                    });
                }else {
                    System.out.println("failed");
                }
                getData().clear();
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        return false;
    }
}
