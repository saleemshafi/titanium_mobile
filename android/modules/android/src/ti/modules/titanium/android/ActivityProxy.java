/**
 * 
 */
package ti.modules.titanium.android;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.KrollProxyListener;
import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.util.Log;
import org.appcelerator.titanium.util.TiConfig;

import android.content.Context;
import android.content.Intent;

public class ActivityProxy 
	extends KrollProxy
	implements KrollProxyListener
{
	private static final String LCAT = "TiActivity";
	private static boolean DBG = TiConfig.LOGD;
		
	//TODO This may need to be a soft reference.
	private TiBaseActivity activity;
	
	public ActivityProxy(TiContext tiContext, Object[] args) 
	{
		super(tiContext);
		modelListener = this;

		KrollDict d = null;
		
		if (args != null && args.length >= 1) {
			if (args[0] instanceof KrollDict) {
				if (DBG) {
					Log.d("LCAT", "ActivityProxy created with dictionary");
				}
				d = (KrollDict) args[0];
			} else if (args[0] instanceof TiBaseActivity) {
				if (DBG) {
					Log.d(LCAT, "ActivityProxy created with existing Activity");
				}
				activity = (TiBaseActivity) args[0];
			}
		}
		
	}
	
	public void start(Object[] args) 
	{
		if (args == null || args.length == 0) {
			Log.w(LCAT, "Ignoring start request. missing Intent");
			return;
		}
		
		if (args[0] instanceof IntentProxy) {
			IntentProxy ip = (IntentProxy) args[0];
			Intent intent = ip.getIntent();
			
			this.getTiContext().getActivity().startActivity(intent);
		} else {
			Log.e(LCAT, "Expected IntentProxy. Received " + args[0].getClass().getCanonicalName());
		}
	}
	
	protected Context getContext() {
		if (activity == null) {
			return getTiContext().getActivity().getApplication();
		}
		return activity;
	}
	
	protected TiBaseActivity getActivity() {
		return activity;
	}
	
	protected void release() {
		activity = null;
	}

	@Override
	public void listenerAdded(String type, int count, KrollProxy proxy) {
	}

	@Override
	public void listenerRemoved(String type, int count, KrollProxy proxy) {
	}

	@Override
	public void processProperties(KrollDict d) {
	}

	@Override
	public void propertyChanged(String key, Object oldValue, Object newValue, KrollProxy proxy) {
		Log.e(LCAT, "Property Change: " + key);
	}

	
}