package com.rakshith.quotopedia.widgets;

/**
 * Created by rakshith on 3/8/17.
 */

import android.content.Context;
import android.graphics.Typeface;

import com.rakshith.quotopedia.R;

import java.util.HashMap;

public class Typefaces {
    private HashMap<String, Typeface> mTypefaceMap;
    private final String mDefaultFontName;
    private Context mContext;

    /**
     * constructor call
     *
     * @param context - the application context
     */
    public Typefaces(Context context) {
        mTypefaceMap = new HashMap<>();
        mContext = context;
        mDefaultFontName = mContext.getString(R.string.regular);
    }

    /**
     * Get the typeface for the typeFaceName. If the typeFace is not present in the map then we load it to the map.
     *
     * @param typeFaceName - the path of the type face that has been lazy loaded
     * @return the typeface corresponding to the typeFaceName if it exists in the map. null otherwise
     */
    public Typeface getTypeFace(String typeFaceName) {
        if (!mTypefaceMap.containsKey(typeFaceName)) {
            mTypefaceMap.put(typeFaceName, getTypefaceFromTypeFaceName(typeFaceName));
        }
        return mTypefaceMap.get(typeFaceName);
    }

    /**
     * get the typeface given by a particular resID.
     *
     * @param typeFaceNameResID - the resolution id of th typeface.
     * @return the typeface corresponding to the resID
     */
    public Typeface getTypeFace(int typeFaceNameResID) {
        return getTypeFace(mContext.getResources().getString(typeFaceNameResID));
    }

    /**
     * Get the path for of the typeface from assets for a typeface
     *
     * @param typeface - the typeface name for which path is required
     * @return the relative path of the typeface
     */
    private String getPathForTypeFace(String typeface) {
        return "fonts/" + typeface;
    }


    /**
     * returns the typeface for a particular typeface name given in the parameter
     *
     * @param typeface - the typeface name for which typeface is required
     * @return the typeface object for the typeface name given in the parameter
     */
    private Typeface getTypefaceFromTypeFaceName(String typeface) {
        return Typeface.createFromAsset(mContext.getAssets(), getPathForTypeFace(typeface));
    }

    /**
     * @return the typeface for the default font name defined in mDefaultFontName
     */
    public Typeface getDefaultTypeface() {
        return getTypeFace(mDefaultFontName);
    }
}
