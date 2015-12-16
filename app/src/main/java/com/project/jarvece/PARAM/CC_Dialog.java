package com.project.jarvece.PARAM;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public abstract class CC_Dialog {

	protected View				convertView;
	private Context				context;
	private AlertDialog			alertDialog;
	private AlertDialog.Builder	alertDialogBuilder;
	private static Boolean		available	= true; // empecher plusieurs boites de dialogues en meme temps
	protected View				buttonCancel;		// Bouton annuler de la dialog, non obligatoire
	protected View				buttonOk;			// Bouton ok de la dialog, obligatoire, (peut-être n'importe quel bouton)

	/**
	 * <p>
	 * Permet de creer une boite de dialogue
	 * 
	 * @param context
	 * @param layoutId
	 *            id du layout utilisé pour la boite de dialogue
	 */
	public CC_Dialog(Context context, Integer layoutId)
	{
		this(context, layoutId, null);
	}

	/**
	 * <p>
	 * Permet de creer une boite de dialogue, avec la fonctionnalite onClickOk sur le bouton "ok"
	 * 
	 * @param context
	 * @param layoutId
	 *            id du layout utilisé pour la boite de dialogue
	 * @param buttonOkId
	 *            id du bouton "ok" dans le layoutId
	 */
	public CC_Dialog(Context context, Integer layoutId, Integer buttonOkId)
	{
		this(context, layoutId, buttonOkId, null);
	}

	/**
	 * <p>
	 * Permet de creer une boite de dialogue, avec la fonctionnalité onClickOk sur le bouton "ok" et onClickCancel sur le bouton "cancel"
	 * 
	 * @param context
	 * @param layoutId
	 *            id du layout utilisé pour la boite de dialogue
	 * @param buttonOkId
	 *            id du bouton "ok" dans le layoutId
	 * @param buttonCancelId
	 *            id du bouton "cancel" dans le layoutId
	 */
	public CC_Dialog(Context context, Integer layoutId, Integer buttonOkId, Integer buttonCancelId)
	{
		this.context = context;

		// on créé la vue de la boite de dialogue et l'alertDialog
		create(layoutId);

		// si on a envoyé un id du bouton ok du layoutId, on set le listener
		if (buttonOkId != null)
		{
			buttonOk = getView(buttonOkId);
			buttonOk.setOnClickListener(onClickOkListener);
		}

		// si on a envoyé un id du bouton annuler du layoutId, on set le
		// listener
		if (buttonCancelId != null)
		{
			buttonCancel = getView(buttonCancelId);
			buttonCancel.setOnClickListener(onClickCancelListener);
		}
	}

	public Context getContext()
	{
		return context;
	}

	/**
	 * <p>
	 * Méthode appelèe si l'id du bouton ok a été défini
	 */
	public abstract void onClickOk();

	/**
	 * <p>
	 * Méthode appelee si l'id du bouton cancel a été défini
	 */
	public abstract void onClickCancel();

	public abstract void onClick(View view, Integer id);

	public abstract void refresh();

	public abstract void onBackPressed();

	/**
	 * <p>
	 * Listener appelé si l'id du bouton ok a été défini
	 */
	private OnClickListener	onClickOkListener		= new OnClickListener() {

														@Override
														public void onClick(View v)
														{
															onClickOk();
														}
													};

	/**
	 * <p>
	 * Listener appelé si l'id du bouton cancel a été défini
	 */
	private OnClickListener	onClickCancelListener	= new OnClickListener() {

														@Override
														public void onClick(View v)
														{
															onClickCancel();
														}
													};

	public synchronized void open()
	{
		// si une boite de dialogue n'est pas déjà ouverte
		if (CC_Dialog.available == true)
		{
			// on empêche de creer une deuxieme boite de dialogue en même temps
			CC_Dialog.available = false;

			// pour appeler les méthodes onClick et refresh
			CC_FragmentManager.getInstance().setCurrentDialog(this);

			alertDialog.show();

			onCreateView(convertView);
		}
	}

	private void create(Integer layoutId)
	{
		// on créé la vue de la boite de dialogue
		convertView = LayoutInflater.from(context).inflate(layoutId, null);

		// on créé le fenetre de la boite de dualogue
		alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(convertView);

		// on assigne la construction à l'objet qui s'occupe d'afficher
		alertDialog = alertDialogBuilder.create();
		alertDialog.setCancelable(false);
	}

	// Accesseurs

	public void close()
	{
		alertDialog.dismiss();

		// on permet la cr�ation d'une autre boite de dialogue
		CC_Dialog.available = true;

		CC_FragmentManager.getInstance().setCurrentDialog(null);
	}

	public void setCancelable(Boolean b)
	{
		alertDialog.setCancelable(b);
	}

	// Redéfinition
	protected abstract void onCreateView(View convertView);

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
	public String getString(Integer stringId, Object... formatArgs)
	{
		if (stringId != null && formatArgs != null)
		{
			return context.getString(stringId, formatArgs);
		}

		return null;
	}

	public String getString(Integer stringId)
	{
		if (stringId != null)
		{
			return context.getString(stringId);
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
