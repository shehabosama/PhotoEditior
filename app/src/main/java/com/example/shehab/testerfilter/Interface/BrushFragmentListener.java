package com.example.shehab.testerfilter.Interface;

public interface BrushFragmentListener {
    void onBrushSizeChangeListener(float Size);
    void onBrushOpacityChangeListener(int opactiy);
    void onBrushColorChangeListener(int color);
    void onBrushStateChangeListener(boolean isEraser);
}
