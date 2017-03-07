
package com.rakshith.quotopedia.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.rakshith.quotopedia.R;

/**
 * Created by rakshith on 7/12/16.
 */
public class CustomTextView extends TextView {
    Typefaces mTypefaces;

    /**
     * constructor call
     *
     * @param context - the context of the view
     */
    public CustomTextView(Context context) {
        super(context);
        mTypefaces = new Typefaces(context);
        setCustomTypeFace(context, null);
    }

    /**
     * constructor call
     *
     * @param context - the context of the view
     * @param attrs   - the attribute set that needs to be applied to the view
     */
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTypefaces = new Typefaces(context);
        setCustomTypeFace(context, attrs);
    }

    /**
     * constructor call
     *
     * @param context      - the context of the view
     * @param attrs        - the attribute set that needs to be applied to the view
     * @param defStyleAttr - style attribute that needs to be applied to apply the style
     */
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTypefaces = new Typefaces(context);
        setCustomTypeFace(context, attrs);
    }

    /**
     * sets the custom type face if provided in attrs or sets a default typeface to render the text
     *
     * @param context - the context of the view
     * @param attrs   - the attribute set that needs to be applied to the view
     */
    private void setCustomTypeFace(Context context, AttributeSet attrs) {
        Typeface typefaceToBeSet;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            String typefaceName = array.getString(R.styleable.CustomTextView_textFont);
            if (array != null && typefaceName != null) {
                typefaceToBeSet = mTypefaces.getTypeFace(typefaceName);
                setTypeface(typefaceToBeSet);
                array.recycle();
                return;
            }
        }
        typefaceToBeSet = mTypefaces.getDefaultTypeface();
        setTypeface(typefaceToBeSet);
    }

    /**
     * a method to set the html text to the text view. this takes care of adding spans
     *
     * @param htmlText         - the spanned html text that needs to be set to the textView
     * @param urlClickListener - a reference to the implementation of the UrlClickListener
     */
    public void setHtmlText(Spanned htmlText, @NonNull UrlClickListener urlClickListener) {
        setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(htmlText);
        URLSpan[] urls = strBuilder.getSpans(0, htmlText.length(), URLSpan.class);
        for (URLSpan span : urls) {
            makeLinkClickable(strBuilder, span, urlClickListener);
        }
        setText(strBuilder);
    }

    private void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span,
                                   @NonNull final UrlClickListener urlClickListener) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        TextURLSpan clickable = new TextURLSpan(span.getURL());
        clickable.setUrlClickListener(urlClickListener);

        strBuilder.setSpan(clickable, start, end, flags);
        //strBuilder.removeSpan(span);
    }

    /**
     * a listener that listens to click events on the links inside the text view
     */
    public interface UrlClickListener {
        /**
         * a method that is invoked on the click of a url inside the text view
         *
         * @param urlClicked - the reference to the uri that was clicked
         */
        void onUrlClicked(Uri urlClicked);
    }

    /**
     * Custom span to highlight clickable text.
     *
     * For underline use {@link ClickableSpan} directly
     */
    private static class TextURLSpan extends ClickableSpan {

        private final String mURL;
        private UrlClickListener mUrlClickListener;

        public TextURLSpan(String url) {
            mURL = url;
        }

        public TextURLSpan(Parcel src) {
            mURL = src.readString();
        }

        public void setUrlClickListener(UrlClickListener urlClickListener) {
            this.mUrlClickListener = urlClickListener;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(ds.isUnderlineText());
        }

        public String getURL() {
            return mURL;
        }

        @Override
        public void onClick(View widget) {
            Uri uri = Uri.parse(getURL());

            if (mUrlClickListener != null) {
                mUrlClickListener.onUrlClicked(uri);
            }
        }
    }
}

