package com.example.softwarehw.slice;

import com.example.softwarehw.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.TextField;
import ohos.agp.components.element.FrameAnimationElement;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.ToastDialog;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class LoginAbilitySlice extends AbilitySlice {
    private DirectionalLayout componentContainer;
    private Button btn;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        // 加载XML布局
        super.setUIContent(ResourceTable.Layout_ability_login);

        if (componentContainer == null) {
            FrameAnimationElement frameAnimationElement =
                    new FrameAnimationElement(getContext(), ResourceTable.Graphic_login_animation_element);

            Component component = new Component(getContext());
            component.setWidth(300);
            component.setHeight(300);
            component.setBackground(frameAnimationElement);
            componentContainer = (DirectionalLayout) findComponentById(ResourceTable.Id_frame_container);
            frameAnimationElement.start();
            componentContainer.removeAllComponents();
            componentContainer.addComponent(component);
        }
        btn = (Button) findComponentById(ResourceTable.Id_btn_login);
        btn.setClickedListener(this::onClick);
    }
    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    public void onClick(Component component) {
        // 如果component是btn
        if (component == btn){
            String uname = "";
            String upwd = "";

            btn.setEnabled(false);
            TextField txt_username = (TextField) findComponentById(ResourceTable.Id_txt_username);

            if (txt_username != null) {
                uname = txt_username.getText().trim();
            }
            TextField txt_pwd = (TextField) findComponentById(ResourceTable.Id_txt_upwd);

            if (txt_pwd != null) {
                upwd = txt_pwd.getText().trim();
            }

            if ("admin".equals(uname) && "123456".equals(upwd)) {
                ToastDialog toastDialog = new ToastDialog(this);
                toastDialog.setText("登录成功");
                toastDialog.show();
                // 点击按钮跳转至第二个页面
                gotoMain();
            }
            else {
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
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ShapeElement initElement = new ShapeElement(getContext(), ResourceTable.Graphic_background_text_field);
                        txt_username.setBackground(initElement);
                        txt_username.setHintColor(Color.BLACK);
                    }
                }, 2000);
            }
            btn.setEnabled(true);
        }
    }

    // 跳转函数
    public void gotoMain() {
        Intent mIntent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withBundleName(getBundleName())
                .withAbilityName("com.example.softwarehw.MainAbility")
                .build();
        mIntent.setOperation(operation);
        startAbility(mIntent);
        terminateAbility();
    }
}
