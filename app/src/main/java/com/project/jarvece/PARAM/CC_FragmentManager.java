package com.project.jarvece.PARAM;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;

public class CC_FragmentManager
{
	private static CC_FragmentManager	instance	= null;
	private Integer						defaultFragmentContainerId;
	private CC_Fragment				currentFragment;
	private CC_Dialog					currentDialog;
	private android.app.FragmentManager	androidFragmentManager;

	// Singleton
	public static CC_FragmentManager getInstance()
	{
		if (instance == null)
		{
			instance = new CC_FragmentManager();
		}

		return instance;
	}

	private CC_FragmentManager()
	{

	}

	/**
	 * Init le fragment manager avec les ressources nécessaires
	 * 
	 * @param activity
	 * @param defaultFragmentContainerId
	 *            l'id du frameLayout par défaut qui contient les fragments
	 */
	public void init(Activity activity, Integer defaultFragmentContainerId)
	{
		this.androidFragmentManager = activity.getFragmentManager();
		this.defaultFragmentContainerId = defaultFragmentContainerId;
	}

	public Fragment getCurrentFragment()
	{
		return currentFragment;
	}

	public void refreshCurrentFragment()
	{
		if (currentDialog != null)
		{
			currentDialog.refresh();
		}
		else
		{
			currentFragment.refresh();
		}
	}

	public void onClick(View view)
	{
		if (currentDialog != null)
		{
			currentDialog.onClick(view, view.getId());
		}
		else
		{
			currentFragment.onClick(view, view.getId());
		}
	}

	public void onBackPressed()
	{
		if (currentDialog != null)
		{
			currentDialog.onBackPressed();
		}
		else
		{
			currentFragment.onBackPressed();
		}
	}

	public void setCurrentDialog(CC_Dialog currentDialog)
	{
		this.currentDialog = currentDialog;
	}

	public void changeFragment(CC_Fragment newFragment)
	{
		if (newFragment != null)
		{
			Fragment fragmentOld = androidFragmentManager.findFragmentById(defaultFragmentContainerId);
			FragmentTransaction fragmentTransaction = androidFragmentManager.beginTransaction();

			if (fragmentOld != null)
			{
				fragmentTransaction.remove(fragmentOld);
				fragmentTransaction.replace(defaultFragmentContainerId, newFragment);
			}
			else
			{
				fragmentTransaction.add(defaultFragmentContainerId, newFragment);
			}

			fragmentTransaction.commitAllowingStateLoss();

			currentFragment = newFragment;
		}
	}
}
