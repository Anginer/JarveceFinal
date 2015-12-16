package com.project.jarvece;

import android.view.KeyEvent;

import com.project.jarvece.GUI.Accueil.F_Accueil;
import com.project.jarvece.PARAM.CC_Activity;
import com.project.jarvece.PARAM.CC_Fragment;

public class MainActivity extends CC_Activity {


	@Override
	public void onCreate() {
	}

	@Override
	public Integer getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public Integer getFragmentContainerId() {
		return R.id.frameLayoutFragmentContainer;
	}

	@Override
	public CC_Fragment getStartFragment() {
		return new F_Accueil();
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
               
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
		return true;
           
     }
}
