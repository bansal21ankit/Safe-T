package com.example.safet.saviour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.safet.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public final class SaviourFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = SaviourFragment.class.getName();
    public static final int TITLE_RES = R.string.saviour_title;

    private ListView mListView;
    private ArrayList<SaviourDetail> mSaviourDetail = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saviour, container, false);

        mListView = (ListView) view.findViewById(R.id.saviour_list);
        //Need to write up code to get Object Id till then keep it constant
        String objectId = "WAD3Z4Zpbb";
        showSaviorList(objectId);

        initActionButtons(view);
        return view;
    }

    private void initActionButtons(View rootView) {
        rootView.findViewById(R.id.saviour_add).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saviour_add:
                // TODO Open screen to add a trusted contact.
                new AddSaviourDialog().show(getChildFragmentManager(), AddSaviourDialog.TAG);
                break;
        }
    }

    private void showSaviorList(String objectId) {
        final ArrayList<String> saviorNumList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.RequestTables.TABLE_USERS);
        query.whereEqualTo(Constants.RequestKeys.COL_OBJECT_ID, objectId);
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {

                if (e != null) {
                    e.printStackTrace();
                } else {
                    Log.d(TAG, "parseObject = " + parseObject);
                    String usersNumList = (String) parseObject
                            .get(Constants.RequestKeys.COL_SAVIOURS);
                    while (usersNumList.contains(",")) {
                        String str = usersNumList.substring(0, usersNumList.indexOf(","));
                        usersNumList = usersNumList.substring(usersNumList.indexOf(",") + 1);
                        saviorNumList.add(str);
                    }

                    //Add last num
                    saviorNumList.add(usersNumList);
                    Log.d(TAG, "NUM ARRAY SIZE" + saviorNumList.size());

                    // get names of saviours from the corresponding num
                    for (int i = 0; i < saviorNumList.size(); i++) {
                        final String num = saviorNumList.get(i).trim();
                        ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.RequestTables.TABLE_USERS);
                        query.whereEqualTo(Constants.RequestKeys.COL_PHONE, num);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> list, ParseException e) {
                                if (e != null) {
                                    e.printStackTrace();
                                } else {
                                    //only one user exist with that number
                                    if (list.size() == 1) {

                                        String name = (String) list.get(0).get(Constants.RequestKeys.COL_NAME);
                                        mSaviourDetail.add(new SaviourDetail.Builder()
                                                .setSaviourName(name).setSaviourPhone(num).create());

                                        ArrayList<String> nameList = new ArrayList<String>();
                                        for (int j = 0; j < mSaviourDetail.size(); j++) {
                                            nameList.add(mSaviourDetail.get(j).getSaviourName());
                                        }
                                        mListView.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, nameList));
                                    } else {
                                        Log.d(TAG, "ZERO OR MORE THAN ONE USER FOUND WITH THE SAME NUMBER" + num + list.size());
                                    }
                                }
                            }
                        });

                    }
                }
            }
        });
    }
}