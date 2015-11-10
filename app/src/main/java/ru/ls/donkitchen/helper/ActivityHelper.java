package ru.ls.donkitchen.helper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;

/**
 * Хелпер для работы с Активити. Содержит набор методов для работы с актитиви.
 * 
 * @author Lord (Kuleshov M.V.)
 * @since 02.04.2013
 * 
 */
public class ActivityHelper {
	/**
	 * Открывает активити
	 * 
	 * @param parentActivity
	 *            класс родительской активити
	 * @param cls
	 *            класс открываемой активити
	 * @param closeCurrent
	 *            нужно ли закрывать текущую активити
	 * @param extras
	 *            дополнительные параметры, передаваемые в открываемую актвити
	 * @throws NullPointerException
	 * @throws ActivityNotFoundException
	 */
	public static void startActivity(Activity parentActivity, Class<?> cls, boolean closeCurrent, Bundle extras)
			throws NullPointerException, ActivityNotFoundException {

		Intent intent = new Intent(parentActivity, cls);
		if (extras != null) {
			intent.putExtras(extras);
		}
		parentActivity.startActivity(intent);
		if (closeCurrent) {
			parentActivity.finish();
		}
	}

	/**
	 * Открывает новую Activity и сохраняет класс текущей активити
	 * 
	 * @param parentActivity
	 *            - текущая активити
	 * @param cls
	 *            - класс новой активити
	 * @param closeCurrent
	 *            - если true, то закрывает текущую
	 * @throws NullPointerException
	 *             если parentActivity или cls равны null
	 * @throws ActivityNotFoundException
	 *             если активити не найдена
	 */
	public static void startActivity(Activity parentActivity, Class<?> cls, boolean closeCurrent)
			throws NullPointerException, ActivityNotFoundException {
		startActivity(parentActivity, cls, closeCurrent, null);
	}
	
	public static void hideSoftKeyboardOnOutside(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		View focusView = activity.getCurrentFocus();
		if (focusView != null) {
			inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);	
		}
	}

	public static void setupAutoHideKeyboardOnOutside(final Activity activity, View view) {

		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {

			view.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					hideSoftKeyboardOnOutside(activity);
					return false;
				}
			});
		}

		// If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {

			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

				View innerView = ((ViewGroup) view).getChildAt(i);

				setupAutoHideKeyboardOnOutside(activity, innerView);
			}
		}
	}

	/**
	 * Вызывает диалог выбора приложения для открытия документа
	 *
	 * @param context конекст
	 * @param fileName название файла
	 */
	public static void openDocument(Context context, String fileName) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		File file = new File(fileName);
		String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
		String mimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
		if (extension.equalsIgnoreCase("") || mimeType == null) {
			// if there is no extension or there is no definite mimeType, still try to open the file
			intent.setDataAndType(Uri.fromFile(file), "text/*");
		} else {
			intent.setDataAndType(Uri.fromFile(file), mimeType);
		}

		// custom message for the intent
		context.startActivity(Intent.createChooser(intent, "Выберите приложение:"));
	}

//	/**
//	 * Выбирает элемент в списке
//	 *
//	 * @param spinner выпадающий список
//	 * @param id идентификатор элемента
//	 */
//	public static void selectSpinnerItem(Spinner spinner, int id) {
//		if (spinner != null && spinner.getAdapter() != null && id != 0) {
//			for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
//				if (spinner.getAdapter().getItem(i) instanceof DictionaryResult.Data.Item) {
//					DictionaryResult.Data.Item item = (DictionaryResult.Data.Item) spinner.getAdapter().getItem(i);
//					if (item.getId() == id) {
//						spinner.setSelection(i);
//						break;
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * Выбирает элемент в списке
//	 *
//	 * @param spinner выпадающий список
//	 * @param text текст элемента списка
//	 */
//	public static void selectSpinnerItemByText(Spinner spinner, String text) {
//		if (spinner != null && spinner.getAdapter() != null && !text.isEmpty()) {
//			for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
//				if (spinner.getAdapter().getItem(i).toString().equals(text)) {
//					spinner.setSelection(i);
//					break;
//				}
//			}
//		}
//	}
//
//	/**
//	 * Инициализирует спиннер списком элементов
//	 *
//	 * @param activity
//	 * @param spinner
//	 * @param dictionaryResult
//	 */
//	public static void initSpinnerAdapter(Activity activity, Spinner spinner, DictionaryResult dictionaryResult) {
//		List<DictionaryResult.Data.Item> items = new ArrayList<>();
//
//		if (dictionaryResult != null) {
//			if (dictionaryResult.getStatus().isSuccess()) {
//				if (dictionaryResult.getData() != null && dictionaryResult.getData().getItems() != null) {
//					for (DictionaryResult.Data.Item it : dictionaryResult.getData().getItems()) {
//						items.add(new DictionaryResult.Data.Item(it.getId(), it.getTitle()));
//					}
//				}
//			}
//		}
//
//		ArrayAdapter<DictionaryResult.Data.Item> adapter = new ArrayAdapter<>(activity,
//				android.R.layout.simple_spinner_dropdown_item, items);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner.setAdapter(adapter);
//	}
//
//	/**
//	 * Инициализирует список для автозавершения
//	 * @param activity
//	 * @param actv
//	 * @param dictionaryResult
//	 * @param ids
//	 */
//	public static void initAutocompleteTextViewAdapter(Activity activity, MaterialAutoCompleteTextView actv, DictionaryResult dictionaryResult, Map<String, Integer> ids) {
//		ids.clear();
//
//		List<String> items = new ArrayList<>();
//
//		if (dictionaryResult != null) {
//			if (dictionaryResult.getStatus().isSuccess()) {
//				if (dictionaryResult.getData() != null && dictionaryResult.getData().getItems() != null) {
//					for (DictionaryResult.Data.Item it : dictionaryResult.getData().getItems()) {
//						items.add(it.getTitle());
//
//						ids.put(it.getTitle(), it.getId());
//					}
//				}
//			}
//		}
//
//		ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
//				android.R.layout.simple_dropdown_item_1line, items);
//		actv.setAdapter(adapter);
//	}
}
