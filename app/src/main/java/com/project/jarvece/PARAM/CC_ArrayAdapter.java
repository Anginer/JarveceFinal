package com.project.jarvece.PARAM;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public abstract class CC_ArrayAdapter<T> extends ArrayAdapter<T>
{
	protected View				convertView;
	protected LayoutInflater	layoutInflater;
	protected T					itemSelection;		// item sélectionné (peut être null)
	private Integer				itemLayoutId;		// id du layout des items
	private static Integer		colorBackground1;	// couleur des lignes paires
	private static Integer		colorBackground2;	// couleur des lignes impaires
	private static Integer		colorSelection;	// couleur quand on clique sur un item

	public Integer getItemLayoutId()
	{
		return itemLayoutId;
	}

	/**
	 * <p>
	 * Constructeur du CC_ArrayAdapter.
	 * 
	 * @param context
	 * @param objects
	 *            liste d'items à contenir dans la liste
	 * @param itemLayoutId
	 *            id du layout des items
	 */
	public CC_ArrayAdapter(Context context, List<T> objects, Integer itemLayoutId)
	{
		super(context, -1, objects);

		layoutInflater = LayoutInflater.from(context);
		this.itemLayoutId = itemLayoutId;
	}

	/**
	 * <p>
	 * Retourne la position à laquelle se trouve l'objet envoyé en paramètre. Se base sur le "equals" pour faire les comparaisons.
	 * 
	 * @param item
	 *            objet que auquel on souhaite connaitre sa position
	 * @return la position de l'objet, ou -1 si non trouvé
	 */
	public int getItemPosition(T item)
	{
		for (int i = 0; i < this.getCount(); i++)
		{
			if (getItem(i).equals(item) == true)
			{
				return i;
			}
		}

		return -1;
	}

	/**
	 * <p>
	 * D�fini la couleur utilisée quand on sélectionne un élément.
	 * 
	 * @param color
	 */
	public static void setColorSelection(Integer color)
	{
		if (color != null)
		{
			CC_ArrayAdapter.colorSelection = color;
		}
	}

	/**
	 * <p>
	 * Défini la couleur utilisée pour le fond des lignes paires.
	 * 
	 * @param color
	 */
	public static void setColorbackground1(Integer color)
	{
		if (color != null)
		{
			CC_ArrayAdapter.colorBackground1 = color;
		}
	}

	/**
	 * <p>
	 * Défini la couleur utilisée pour le fond des lignes impaires.
	 * 
	 * @param color
	 */
	public static void setColorbackground2(Integer color)
	{
		if (color != null)
		{
			CC_ArrayAdapter.colorBackground2 = color;
		}
	}

	/**
	 * <p>
	 * Change la couleur du texte du TextView
	 * 
	 * @param textViewId
	 *            id du TextView
	 * @param color
	 *            couleur du texte
	 */
	public void setTextColor(Integer textViewId, int color)
	{
		TextView textView = getView(textViewId);
		if (textView != null)
		{
			textView.setTextColor(color);
		}
	}

	/**
	 * <p>
	 * Méthode utilisée dans la librairie, ne pas utiliser.
	 * 
	 * @param context
	 */
	public static void _init(Context context)
	{
		/*CC_ArrayAdapter.colorSelection = context.getResources().getColor(R.color./////);
		CC_ArrayAdapter.colorBackground1 = context.getResources().getColor(R.color.//////);
		CC_ArrayAdapter.colorBackground2 = context.getResources().getColor(R.color./////);*/
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = layoutInflater.inflate(itemLayoutId, null);
		}

		this.convertView = convertView;

		setBackgroundColor(position);

		getView(position, convertView, getItem(position));

		setColorSelection(position);

		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		return getView(position, convertView, parent);
	}

	public void setBackgroundColor(View convertView, Integer color)
	{
		if (color != null)
		{
			convertView.setBackgroundColor(color);
		}
	}

	public void resetBackgroundColor(int position)
	{
		setBackgroundColor(position);
		setColorSelection(position);
	}

	private void setColorSelection(int position)
	{
		T item = getItem(position);

		if (CC_ArrayAdapter.colorSelection != null && item.equals(itemSelection))
		{
			convertView.setBackgroundColor(CC_ArrayAdapter.colorSelection);
		}
	}

	private void setBackgroundColor(int position)
	{
		Integer backgroundColor = getCurrentColorBackground(position);

		if (backgroundColor != null)
		{
			convertView.setBackgroundColor(backgroundColor);
		}
	}

	public abstract void getView(int position, View convertView, T currentItem);

	/**
	 * <p>
	 * Permet de set la visibilité suivant un boolean.
	 * 
	 * @param viewId
	 *            id de la view
	 * @param isVisible
	 *            si true la view sera VISIBLE, sinon elle sera comme visibilityIfFalse
	 * @param visibilityIfFalse
	 *            la visibilité de la vue si isVisible == false
	 */
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
		if (view != null && visibility != null)
		{
			view.setVisibility(visibility);
		}
	}

	public void addText(TextView view, String text)
	{
		if (view != null && text != null && text.equals("") == false && text.equals("null") == false)
		{
			String oldText = view.getText().toString();
			view.setText(oldText + " " + text);
		}
	}

	private Integer getCurrentColorBackground(int position)
	{

		if (CC_ArrayAdapter.colorBackground1 != null && (position % 2) == 0)
		{
			return CC_ArrayAdapter.colorBackground1;
		}
		else if (CC_ArrayAdapter.colorBackground2 != null)
		{
			return CC_ArrayAdapter.colorBackground2;
		}

		return null;
	}

	public void setItemSelection(T itemSelection)
	{
		this.itemSelection = itemSelection;

		notifyDataSetChanged();
	}

	public T getItemSelection()
	{
		return itemSelection;
	}

	public void setItemSelection(int position)
	{
		this.setItemSelection(this.getItem(position));
	}

	public boolean isDoubleClick(int position)
	{
		return this.getItem(position).equals(itemSelection);
	}

	// /////////////////////////////////////////////////////
	// View
	// /////////////////////////////////////////////////////
	@SuppressWarnings({ "unchecked", "hiding" })
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

	@SuppressWarnings({ "unchecked", "hiding" })
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
	public String getString(Integer stringId, Object... formatArgs)
	{
		if (stringId != null && formatArgs != null)
		{
			return getContext().getString(stringId, formatArgs);
		}

		return null;
	}

	public String getString(Integer stringId)
	{
		if (stringId != null)
		{
			return getContext().getString(stringId);
		}

		return null;
	}

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

	public void setText(TextView textView, String text)
	{
		if ((textView != null) && (CC_Fragment.isEmpty(text) == false))
		{
			textView.setText(Html.fromHtml(text));
		}

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