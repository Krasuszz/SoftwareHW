package com.example.softwarehw.slice;

import com.example.softwarehw.MainAbility;
import com.example.softwarehw.ResourceTable;

import com.example.softwarehw.MyApplication;
import com.example.softwarehw.bean.UserDataBean;
import com.example.softwarehw.util.Tools;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.components.element.FrameAnimationElement;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.distributed.common.*;
import ohos.data.distributed.user.SingleKvStore;
import ohos.hiviewdfx.Debug;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONArray;
import ohos.utils.zson.ZSONObject;

import java.util.*;

public class LoginAbilitySlice extends AbilitySlice {
    private DirectionalLayout componentContainer;
    private Button button_login, button_signup;

    // 分布式数据库
    private SingleKvStore KvStore;
    // 存入的列表数据key
    private static final String KEY_DATA = "user_data";

    private final List<UserDataBean> listData_login = new ArrayList<>();
    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x12345, "LoginAbilitySlice");
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        // 加载XML布局
        super.setUIContent(ResourceTable.Layout_ability_login);

        if (componentContainer == null) {
            FrameAnimationElement frameAnimationElement =
                    new FrameAnimationElement(getContext(), ResourceTable.Graphic_login_animation_element);

            Component component = new Component(getContext());
            component.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
            component.setHeight(ComponentContainer.LayoutConfig.MATCH_PARENT);
            component.setBackground(frameAnimationElement);
            componentContainer = (DirectionalLayout) findComponentById(ResourceTable.Id_frame_container);
            frameAnimationElement.start();
            componentContainer.removeAllComponents();
            componentContainer.addComponent(component);
        }
        button_login = (Button) findComponentById(ResourceTable.Id_button_login);
        button_signup = (Button) findComponentById(ResourceTable.Id_button_signup);
        button_login.setClickedListener(this::onClick);
        button_signup.setClickedListener(this::onClick);
        initDatabase();

    }
    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    public void onClick(Component component)
    {
        String input_id = "", input_password = "";
        String user_id = "", user_password = "" ;

        //button_login.setEnabled(false);
        TextField txt_username = (TextField) findComponentById(ResourceTable.Id_txt_username);
        TextField txt_pwd = (TextField) findComponentById(ResourceTable.Id_txt_upwd);
        if (txt_username == null||txt_pwd == null)
        {
            new ToastDialog(this).setText("账号或密码为空").show();
            return;
        }

        input_id = txt_username.getText().trim();
        input_password = txt_pwd.getText().trim();

        if (component == button_login) // 登录按钮
        {
            UserDataBean user_data = find_user(input_id);
            if(user_data == null)
            {
                new ToastDialog(this).setText("账号或密码错误").show();
                return;
            }
            user_id = user_data.get_user_id();
            user_password = user_data.get_user_password();

            if (user_password.equals(input_password)) // 密码正确跳转到主页面
            {
                new ToastDialog(this).setText("登录成功").show();
                gotoMain(user_id);
            }
            else
            {
                // 显示TextField错误
                ShapeElement errorElement = new ShapeElement(this, ResourceTable.Graphic_background_text_field_error);
                errorElement.setAlpha(120);
                txt_username.setBackground(errorElement);
                txt_username.setText("");
                txt_username.setHint("密码错误");
                txt_username.setHintColor(Color.RED);
                txt_username.setHorizontalPadding(100,20);
                // TextField失焦
                txt_username.clearFocus();

                ToastDialog toastDialog = new ToastDialog(this);
                toastDialog.setText("登录失败");
                toastDialog.show();

                // 密码错误2s后恢复颜色
                Timer timer = new Timer();
                timer.schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        ShapeElement initElement = new ShapeElement(getContext(), ResourceTable.Graphic_background_text_field);
                        txt_username.setBackground(initElement);
                        txt_username.setHintColor(Color.BLACK);
                    }
                }, 2000);
            }
            //button_login.setEnabled(true);
        }
        else if(component == button_signup) // 注册按钮
        {
            UserDataBean user_data = find_user(input_id);
            if(user_data != null)
            {
                new ToastDialog(this).setText("账号已存在").show();
                //return;
            }
            add_user(input_id, input_password);

            UserDataBean user_data1 = find_user(input_id);
            if(user_data1 != null)
            {
                new ToastDialog(this).setText("注册成功！").show();
            }
            else
            {
                new ToastDialog(this).setText("寄").show();
            }

        }
    }
    // 数据库查询
    private UserDataBean find_user(String input_id)
    {
        String tmp_id = "", tmp_password="", tmp_name="";

        Iterator<UserDataBean> it = listData_login.iterator();
        while (it.hasNext())
        {
            UserDataBean s=it.next();
            tmp_id = s.get_user_id();
            if(input_id.equals(tmp_id))
            {
                tmp_password = s.get_user_password();
                tmp_name = s.get_user_name();
                return new UserDataBean(tmp_id, tmp_password, tmp_password);
            }
        }
        return null;
    }
    // 数据库插入
    private void add_user(String input_id, String input_password)
    {
        // 更新listData
        listData_login.add(new UserDataBean(input_id, input_password, "piggy"));
        // 存入数据库中
        KvStore.putString(KEY_DATA, ZSONObject.toZSONString(listData_login));
    }

    // 跳转函数
    public void gotoMain(String user_id)
    {
        Intent intent = new Intent();

        IntentParams intentParams = new IntentParams();
        intentParams.setParam("user_id",user_id);
        intent.setParams(intentParams);

        present(new MainAbilitySlice(),intent);
    }

    /**
     * 从MyApplication获取分布式数据库
     */
    private void initDatabase()
    {
        KvStore = MyApplication.getInstance().getKvStore();
        // 监听数据库数据改变
        KvStore.subscribe(SubscribeType.SUBSCRIBE_TYPE_ALL, new KvStoreObserver() {
            @Override
            public void onChange(ChangeNotification changeNotification) {
                KvStoreObserver.super.onChange(changeNotification);
                List<Entry> insertEntries = changeNotification.getInsertEntries();
                List<Entry> updateEntries = changeNotification.getUpdateEntries();

                // 第一次存入数据， 获取insertEntries
                if (insertEntries.size() > 0) {
                    for (Entry entry : insertEntries) {  // 遍历insertEntries
                        if (KEY_DATA.equals(entry.getKey())) {  // 找到entry中key相同的
                            // 回调为非UI线程， 需要在UI更新线程
                            getUITaskDispatcher().syncDispatch(() -> {
                                listData_login.clear();
                                listData_login.addAll(ZSONArray.stringToClassList(entry.getValue().getString(), UserDataBean.class));
                                HiLog.info(label, "listData1:"+ZSONObject.toZSONString(ZSONArray.stringToZSONArray(entry.getValue().getString()).toJavaList(UserDataBean.class)));
                                HiLog.info(label, "listData_inside:"+ZSONObject.toZSONString(listData_login));
                            });
                        }
                    }
                }
                else if (updateEntries.size() > 0){
                    for (Entry entry : updateEntries) {
                        if (KEY_DATA.equals(entry.getKey())) {
                            getUITaskDispatcher().syncDispatch(() -> {
                                listData_login.clear();
                                listData_login.addAll(ZSONArray.stringToClassList(entry.getValue().getString(), UserDataBean.class));
                                HiLog.info(label, "listData1:"+ZSONObject.toZSONString(ZSONArray.stringToZSONArray(entry.getValue().getString()).toJavaList(UserDataBean.class)));
                                HiLog.info(label, "listData_inside:"+ZSONObject.toZSONString(listData_login));
                            });
                        }
                    }
                }
                HiLog.info(label, "listData_out:"+ZSONObject.toZSONString(listData_login));
            }
        });
    }
}
