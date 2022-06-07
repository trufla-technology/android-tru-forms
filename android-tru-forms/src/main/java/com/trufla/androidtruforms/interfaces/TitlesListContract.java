package com.trufla.androidtruforms.interfaces;

public interface TitlesListContract {
    void onRequestTitlesList(TruConsumer<String> titlesLoadedListener, String type);
}
