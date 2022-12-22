package com.example.softwarehw;

import com.example.softwarehw.slice.MainAbilitySlice;
import com.example.softwarehw.slice.LoginAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        //super.setMainRoute(MainAbilitySlice.class.getName()); // debug用
        super.setMainRoute(LoginAbilitySlice.class.getName());
        super.addActionRoute("action.route_to_main", MainAbilitySlice.class.getName());
    }
}
