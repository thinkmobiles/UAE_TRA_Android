package com.uae.tra_smart_services.entities;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.uae.tra_smart_services.customviews.HexagonView;

/**
 * Created by mobimaks on 09.10.2015.
 */
public class HexagonViewTarget extends ViewTarget<HexagonView, Drawable> {

    public HexagonViewTarget(final HexagonView _view) {
        super(_view);
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        super.onLoadStarted(placeholder);
        updateHexagonViewSrc(placeholder);
    }

    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        super.onLoadFailed(e, errorDrawable);
        updateHexagonViewSrc(errorDrawable);
    }

    @Override
    public void onLoadCleared(Drawable placeholder) {
        super.onLoadCleared(placeholder);
        updateHexagonViewSrc(placeholder);
    }

    @Override
    public void onResourceReady(Drawable resource, GlideAnimation<? super Drawable> glideAnimation) {
        updateHexagonViewSrc(resource);
    }

    private void updateHexagonViewSrc(final Drawable _drawable){
        final HexagonView hexagonView = getView();
        hexagonView.postScaleType(HexagonView.CENTER_CROP);
        hexagonView.setHexagonSrcDrawable(_drawable);
    }
}
