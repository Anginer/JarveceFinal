package com.project.jarvece.PARAM;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class CC_Fragment extends Fragment
{
	protected View	convertView;

	// méthodes utilitaires

	public void setVisibility(Integer viewParentId, Integer viewId, Integer visibility)
	{
		View view = convertView.findViewById(viewParentId).findViewById(viewId);

		setVisibility(view, visibility);
	}

	public void setVisibility(Integer viewId, boolean isVisible, Integer visibilityIfFalse)
	{
		if (isVisible == true)
		{
			setVisibility(viewId, View.VISIBLE);
		}
		else
		{
			setVisibility(viewId, visibilityIfFalse);
		}
	}

	public void setVisibility(Integer viewId, Integer visibility)
	{
		View view = convertView.findViewById(viewId);

		setVisibility(view, visibility);
	}

	public void setVisibility(View view, Integer visibility)
	{
		view.setVisibility(visibility);
	}

	public void setEnabled(Integer viewParentId, Integer viewId, Boolean enabled)
	{
		View view = convertView.findViewById(viewParentId).findViewById(viewId);

		setEnabled(view, enabled);
	}

	public void setEnabled(Integer viewId, Boolean enabled)
	{
		View view = convertView.findViewById(viewId);

		setEnabled(view, enabled);
	}

	public void setEnabled(View view, Boolean enabled)
	{
		view.setEnabled(enabled);
	}

	// erase text
	public void eraseText(Integer textViewId)
	{
		TextView textView = (TextView) convertView.findViewById(textViewId);

		eraseText(textView);
	}

	public void eraseText(TextView textView)
	{
		textView.setText("");
	}

	// gettext
	public String getText(Integer textViewId)
	{
		TextView textView = (TextView) convertView.findViewById(textViewId);

		return getText(textView);
	}

	public String getText(TextView textView)
	{
		return textView.getText().toString();
	}

	public void changeFragment(CC_Fragment newFragment)
	{
		CC_FragmentManager.getInstance().changeFragment(newFragment);
	}

	/**
	 * <p>
	 * Ferme l'application (appelle la méthode finish de l'activité)
	 */
	public void finish()
	{
		getActivity().finish();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		convertView = inflater.inflate(getLayoutId(), null);

		onCreateView(convertView);

		refresh();

		return convertView;
	}

	// méthodes à redéfinir
	public abstract void onCreateView(View convertView);

	public abstract void onClick(View view, Integer id);

	public abstract void refresh();

	public abstract Integer getLayoutId();

	public abstract void onBackPressed();

	public boolean onKeyUp(int keyCode, KeyEvent event, boolean defaultReturn)
	{
		return defaultReturn;
	}

	public void onNewIntent(Intent i)
	{
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return true;
	}

	// /////////////////////////////////////////////////////
	// View
	// /////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	public <T> T getView(Integer viewId)
	{
		T view = null;

		if (viewId != null)
		{
			try
			{
				view = (T) convertView.findViewById(viewId);
			}
			catch (Exception e)
			{
				view = null;
			}
		}

		return view;
	}

	@SuppressWarnings("unchecked")
	public <T> T getView(Integer parentViewId, Integer viewId)
	{
		T view = null;

		if (parentViewId != null && viewId != null)
		{
			try
			{
				View parentView = convertView.findViewById(parentViewId);

				if (parentView != null)
				{
					try
					{
						view = (T) parentView.findViewById(viewId);
					}
					catch (Exception e)
					{
						view = null;
					}
				}
			}
			catch (Exception e)
			{
				view = null;
			}
		}

		return view;
	}

	// /////////////////////////////////////////////////////
	// Context
	// /////////////////////////////////////////////////////

	// /////////////////////////////////////////////////////
	// TextView
	// /////////////////////////////////////////////////////
	public void setText(Integer textViewId, Integer textId)
	{
		String text = getString(textId);

		setText(textViewId, text);
	}

	public void setText(Integer textViewId, String text)
	{
		TextView textView = (TextView) getView(textViewId);

		setText(textView, text);
	}

	public void setText(TextView textView, Integer textId)
	{
		String text = getString(textId);

		setText(textView, text);
	}

	public void startActivity(Intent intent)
	{

	}

	public void setText(TextView textView, String text)
	{
		if ((textView != null) && (isEmpty(text) == false))
		{
			textView.setText(Html.fromHtml(text));
		}

	}
	
	public static boolean isEmpty (String s)
	{
		if (s == null || s.equals("") || s.equals("null") || s.equals("Null") || s.equals("NULL") )
		{return true;}
		
		
		return false;
	}

	public void setText(Integer parentViewId, Integer textViewId, Integer textId)
	{
		setText((TextView) getView(parentViewId, textViewId), textId);
	}

	public void setText(Integer parentViewId, Integer textViewId, String text)
	{
		setText((TextView) getView(parentViewId, textViewId), text);
	}


}
