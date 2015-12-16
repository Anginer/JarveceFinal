package com.project.jarvece.PARAM;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.KeyEvent.Callback;
import android.view.View;

public abstract class CC_Activity extends Activity
{
	/**
	 * <p>
	 * Méthode appelée quand on fait appel à la méthode onClick dans le XML
	 * 
	 * @param view
	 */
	public void onClick(View view)
	{
		CC_FragmentManager.getInstance().onClick(view);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return ((Callback) CC_FragmentManager.getInstance().getCurrentFragment()).onKeyDown(keyCode, event);
	}

	@Override
	public void onNewIntent(Intent i)
	{
		((CC_Fragment) CC_FragmentManager.getInstance().getCurrentFragment()).onNewIntent(i);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		boolean defaultReturn = super.onKeyUp(keyCode, event);

		return ((CC_Fragment) CC_FragmentManager.getInstance().getCurrentFragment()).onKeyUp(keyCode, event, defaultReturn);
	}

	@Override
	public void startActivity(Intent intent)
	{
		CC_FragmentManager.getInstance().getCurrentFragment().startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initClasses();
		initFragmentManager();
		setContentView(getLayoutId());
		onCreate();
	}

	public abstract void onCreate();

	public abstract Integer getLayoutId();

	/**
	 * <p>
	 * Initialise 
	 */
	private void initClasses()
	{

	}

	/**
	 * <p>
	 * Initialise le fragment manager et switch sur le 1er fragment
	 */
	private void initFragmentManager()
	{
		// on init le fragment manager avec les infos
		CC_FragmentManager.getInstance().init(this, getFragmentContainerId());
		// on switch sur le 1er fragment
		CC_FragmentManager.getInstance().changeFragment(getStartFragment());
	}

	/**
	 * <p>
	 * Appelle la méthode onBackPressed du fragment courrant
	 */
	@Override
	public void onBackPressed()
	{
		CC_FragmentManager.getInstance().onBackPressed();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
	}

	/**
	 * <p>
	 * Spécifie le widget qui contient les fragments
	 * 
	 * @return l'id du widget qui contiendra les fragments
	 */
	public abstract Integer getFragmentContainerId();

	/**
	 * <p>
	 * Spécifie le fragment de départ
	 * 
	 * @return fragment de départ
	 */
	public abstract CC_Fragment getStartFragment();
}
