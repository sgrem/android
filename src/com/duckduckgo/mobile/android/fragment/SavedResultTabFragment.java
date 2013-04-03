package com.duckduckgo.mobile.android.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.duckduckgo.mobile.android.R;
import com.duckduckgo.mobile.android.activity.DuckDuckGo;
import com.duckduckgo.mobile.android.views.SavedSearchListView;
import com.duckduckgo.mobile.android.views.SavedSearchListView.OnSavedSearchItemSelectedListener;

public class SavedResultTabFragment extends ListFragment {
	SavedSearchListView savedSearchView = null;
	
	/** (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			// no reason to try to create its view hierarchy because it won't be displayed.
            return null;
        }
		LinearLayout fragmentLayout = (LinearLayout)inflater.inflate(R.layout.fragment_tab_savedresult, container, false);
		return fragmentLayout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// setup for real work
		final Activity activity = getActivity();

		if(activity instanceof DuckDuckGo) {	
			savedSearchView = (SavedSearchListView) getListView();
			savedSearchView.setDivider(null);
			savedSearchView.setAdapter(((DuckDuckGo) activity).mDuckDuckGoContainer.savedSearchAdapter);
			savedSearchView.setOnSavedSearchItemSelectedListener(new OnSavedSearchItemSelectedListener() {
				public void onSavedSearchItemSelected(String query) {
					if(query != null){							
						((DuckDuckGo) activity).searchWebTerm(query);	
						((DuckDuckGo) activity).itemSaveSearch(query);
						((DuckDuckGo) activity).syncAdapters();
					}			
				}
			});
			
			savedSearchView.setOnSavedSearchItemLongClickListener(((DuckDuckGo) activity).mSavedSearchLongClickListener);
		}
	}
}