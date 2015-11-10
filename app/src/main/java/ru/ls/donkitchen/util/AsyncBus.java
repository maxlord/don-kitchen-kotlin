package ru.ls.donkitchen.util;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 13.04.2015
 */
public class AsyncBus extends Bus {
	private final Handler mainThread = new Handler(Looper.getMainLooper());

	public AsyncBus(ThreadEnforcer enforcer) {
		super(enforcer);
	}

	@Override
	public void post(final Object event) {
		mainThread.post(() -> AsyncBus.super.post(event));
	}

	public void postDelayed(final Object event, long delayMs) {
		mainThread.postDelayed(() -> AsyncBus.super.post(event), delayMs);
	}
}
