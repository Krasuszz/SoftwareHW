package com.example.softwarehw.slice;

import com.example.softwarehw.MainAbility;
import com.example.softwarehw.ResourceTable;


import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.components.element.FrameAnimationElement;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.ToastDialog;
import ohos.hiviewdfx.Debug;

import java.util.Timer;
import java.util.TimerTask;

public class LoginAbilitySlice extends AbilitySlice {
    private DirectionalLayout componentContainer;
    private Button button_login, button_signup;
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
        if (component == button_login) // 登录按钮
        {
            String input_id = "";
            String input_password = "";

            button_login.setEnabled(false);
            TextField txt_username = (TextField) findComponentById(ResourceTable.Id_txt_username);

            if (txt_username != null)
            {
                input_id = txt_username.getText().trim();
            }
            TextField txt_pwd = (TextField) findComponentById(ResourceTable.Id_txt_upwd);

            if (txt_pwd != null)
            {
                input_password = txt_pwd.getText().trim();
            }

            if ("admin".equals(input_id) && "12345".equals(input_password)) // 密码正确跳转到主页面
            {
                new ToastDialog(this).setText("登录成功").show();
                gotoMain(input_id);
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
            button_login.setEnabled(true);
        }
        else if(component == button_signup) // 注册按钮
        {

        }
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
}
