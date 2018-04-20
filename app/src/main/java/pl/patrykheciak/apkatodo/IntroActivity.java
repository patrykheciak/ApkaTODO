package pl.patrykheciak.apkatodo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;


public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(
                "Dziękujemy że jesteś z nami!",
                "Serdecznie dziękujemy za wybranie naszego produktu",
                R.mipmap.asd,
                Color.parseColor("#3F51B5")));

        addSlide(AppIntroFragment.newInstance(
                "Dodawaj listy zadań",
                "Klikając + utworzysz nową listę zadań",
                R.mipmap.fab,
                Color.parseColor("#e71976")));

        addSlide(AppIntroFragment.newInstance(
                "Tytul2", "opis2",
                R.drawable.ic_menu_gallery,
                Color.parseColor("#e71976")));

        addSlide(AppIntroFragment.newInstance("Twórz powiadomienia do zadań",
                "Dla każdego zadania możesz ustawić przypomnienie", R.drawable.ic_menu_gallery, Color.parseColor("#3F51B5")));

        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));
        showSkipButton(false);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
