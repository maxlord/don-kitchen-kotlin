package ru.ls.donkitchen.dto;

/**
 * Описывает пару: ключ - значение
 * Используется в выпадающих списках
 *
 * @author Lord (Kuleshov M.V.)
 * @since 28.08.15
 */
public class KeyValue {
	private int key;
	private String value;

	public KeyValue() {
	}

	public KeyValue(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
